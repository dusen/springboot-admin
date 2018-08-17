/**
 * @(#)TransactionDispatchDataSubscriber.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.api.log.MdcKeyEnum;
import com.fastretailing.dcp.common.exception.BusinessException;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.ProcessingStatus;
import com.fastretailing.dcp.storecommon.transaction.TransactionMessageConverter;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.config.BusinessProcessorConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.service.BusinessProcessorService;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.service.BusinessProcessorStatusService;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;
import com.fastretailing.dcp.storecommon.transaction.log.TransactionMdcKey;
import com.fastretailing.dcp.storecommon.transaction.repository.TransactionStatusDynamoRepository;
import com.fastretailing.dcp.storecommon.transaction.util.TransactionDateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that performs application type and status management.
 */
@Slf4j
@Service
public class TransactionDispatchDataSubscriber implements ApplicationContextAware {

    /** An instance of a class that performs business processing. */
    private BusinessProcessorService businessProcessorService;

    /** Transaction status management service. */
    @Autowired
    private BusinessProcessorStatusService businessProcessorStatusService;

    /** Transaction status dynamo repository. */
    @Autowired
    private TransactionStatusDynamoRepository transactionStatusDynamoRepository;

    /** Converter for published transaction messages and transaction data. */
    @Autowired
    private TransactionMessageConverter transactionMessageConverter;

    /** Business processor configuration. */
    @Autowired
    private BusinessProcessorConfiguration businessProcessorConfiguration;

    /** DynamoDB time to live expiration days. */
    @Value("${transaction-dispatch.dynamodb.expiration-days:60}")
    private int expirationDays;


    /**
     * Get the instance of the business processor service by using the set application context.
     * 
     * @param applicationContext Application context.
     * @throws BeansException Thrown if an instance could not be retrieved.
     */
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        businessProcessorService =
                applicationContext.getBean(businessProcessorConfiguration.getBusinessProcessName(),
                        BusinessProcessorService.class);
    }

    /**
     * Implements business processes using transaction data based on messages obtained from
     * subscriptions from Redis And Transaction processing status. The content of business process
     * is defined in each class inheriting this class.
     *
     * 
     * <P>
     * [Setting value of processing completion status] Normal
     * <UL>
     * <LI>Result code: NORMAL
     * <LI>Error code: null
     * <LI>Error message: null
     * </UL>
     * Abnormal time
     * <UL>
     * <LI>When a SystemException occurs
     * <UL>
     * <LI>Result code : Any result code
     * <LI>Error code : Any error code
     * <LI>Error message : Any error message
     * </UL>
     * <LI>When Error orException occurs
     * <UL>
     * <LI>Result code: ABNORMAL
     * <LI>Error code: E001
     * <LI>Error message: Error or exception occurred in the application.
     * </UL>
     * </UL>
     *
     * @param message Message retrieved from Subscribe from Redis.
     */
    public void receiveMessage(String message) {

        // When the number of threads reaches the upper limit, DB
        // registration processing is not performed.
        log.info("Message received. (message: {})", message);

        if (StringUtils.isEmpty(message)) {
            throw new SystemException("Received an empty message.");
        }

        final ReceiveMessage receiveMessage =
                transactionMessageConverter.deserialize(message, true);

        final List<String> acceptTransactionType =
                businessProcessorConfiguration.getAcceptingTransactionType();

        final String transactionId = receiveMessage.getTransactionId();

        MDC.put(TransactionMdcKey.SALES_TRANSACTION_ID, transactionId);
        if (receiveMessage.getTransactionData().getRequestHeaderMap() != null) {

            String traceIdKey = MdcKeyEnum.AMAZON_TRACE_ID.getKey();
            MDC.put(traceIdKey, Optional
                    .ofNullable(receiveMessage.getTransactionData().getRequestHeaderMap().get(
                            traceIdKey))
                    .orElse(receiveMessage.getTransactionData().getRequestHeaderMap().get(
                            traceIdKey.toLowerCase())));
        }

        final TransactionData transactionData = receiveMessage.getTransactionData();

        // Check accepted transaction type.
        if (!businessProcessorService.checkTransactionType(transactionId, transactionData)) {
            log.info("Skip transaction type. " + "acceptTransactionType={}/receiveMessage={}",
                    acceptTransactionType, receiveMessage);
            return;
        }

        try {
            if (businessProcessorStatusService.acquireExecutionRight(
                    receiveMessage) == ExecutionRightAcquisitionState.FAILED) {
                // Skip completion status.
                log.info("Skipping processing.");
                return;
            }

            // Insert processing status to dynamoDB.
            TransactionStatus transactionStatus = new TransactionStatus();
            transactionStatus.setTransactionId(transactionId);
            transactionStatus.setBusinessStatus(createBusinessStatus(ProcessingStatus.PROCESSING));
            transactionStatus.setRegistrationDate(TransactionDateUtil.createCurrentDateMinute());
            transactionStatus.setExpirationTime(
                    Instant.now().plus(expirationDays, ChronoUnit.DAYS).getEpochSecond());
            transactionStatusDynamoRepository.save(transactionStatus);

            transactionStatusDynamoRepository.save(transactionStatus);

            // Execute business process.
            BusinessStatus businessProcessStatus =
                    businessProcessorService.execute(transactionId, transactionData);

            transactionStatus.setRegistrationDate(TransactionDateUtil.createCurrentDateMinute());
            if (businessProcessStatus == BusinessStatus.SUCCESS) {
                // Insert complete status to dynamoDB.
                transactionStatus
                        .setBusinessStatus(createBusinessStatus(ProcessingStatus.COMPLETION));
                transactionStatusDynamoRepository.save(transactionStatus);
            } else {
                // Insert error status to dynamoDB.
                transactionStatus.setBusinessStatus(createBusinessStatus(ProcessingStatus.ERROR));
                transactionStatusDynamoRepository.save(transactionStatus);
            }

        } catch (BusinessException be) {
            log.error("Execute business process error", be);
        }

    }

    /**
     * Create business status.
     * 
     * @param processingStatus Processing status.
     * @return Created business status.
     */
    private String createBusinessStatus(ProcessingStatus processingStatus) {
        String businessProcessName = businessProcessorConfiguration.getBusinessProcessName();

        return String.join(TransactionStatus.BUSINESS_STATUS_SEPARATOR,
                String.valueOf(System.currentTimeMillis()), businessProcessName,
                processingStatus.toString());
    }

}

/**
 * @(#)SqsMessageReceiverImpl.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.controller;

import java.util.Optional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Controller;
import com.fastretailing.dcp.common.api.log.MdcKeyEnum;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.ProcessingStatus;
import com.fastretailing.dcp.storecommon.transaction.TransactionMessageConverter;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.config.BusinessReceiverConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.exception.ExecutionRightAcquisitionException;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.service.BusinessReceiverStatusService;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.service.BusinessReceiverStrategy;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.service.TransactionPublishingService;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;
import com.fastretailing.dcp.storecommon.transaction.log.TransactionMdcKey;
import com.fastretailing.dcp.storecommon.transaction.repository.TransactionStatusDynamoRepository;
import com.fastretailing.dcp.storecommon.transaction.util.TransactionDateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * SQS message listener controller.
 */
@Slf4j
@Controller
public class SqsMessageReceiverImpl implements MessageReceiver {

    /** RDS Business status table service. */
    @Autowired
    private BusinessReceiverStatusService businessReceiverStatusService;

    /** DynamoDB access service. */
    @Autowired
    private TransactionStatusDynamoRepository transactionStatusDynamoRepository;

    /** Execute business processes. */
    @Autowired
    private BusinessReceiverStrategy businessReceiverStrategy;

    /** Redis access service. */
    @Autowired
    private TransactionPublishingService transactionPublishingService;

    /** Transaction converter. */
    @Autowired
    private TransactionMessageConverter transactionMessageConverter;

    /** Business receiver configuration. */
    @Autowired
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /** DynamoDB time to live expiration days. */
    @Value("${transaction-dispatch.dynamodb.expiration-days:60}")
    private int expirationDays;

    /** Error message for runtime exception. */
    private static String ERROR_MSG = "Runtime exception occured.";

    /**
     * Receives a transaction message from SQS.
     * 
     * @param message Received message.
     */
    @SqsListener(value = "${transaction-dispatch.sqs.queue-name}",
            deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @Override
    public void receive(String message) {
        try {
            processPublish(message);
        } catch (RuntimeException e) {
            log.error(ERROR_MSG, e);
            throw e;
        }
    }

    /**
     * Insert status and publish receive data.
     * 
     * @param message SQS message.
     */
    private void processPublish(String message) {

        if (StringUtils.isEmpty(message)) {
            throw new SystemException("Received an empty message.");
        }

        ReceiveMessage receiveMessage = transactionMessageConverter.deserialize(message, true);
        String transactionId = receiveMessage.getTransactionId();

        MDC.put(TransactionMdcKey.SALES_TRANSACTION_ID, transactionId);
        if (receiveMessage.getTransactionData().getRequestHeaderMap() != null) {

            String traceIdKey = MdcKeyEnum.AMAZON_TRACE_ID.getKey();
            MDC.put(traceIdKey, Optional
                    .ofNullable(receiveMessage.getTransactionData().getRequestHeaderMap().get(
                            traceIdKey))
                    .orElse(receiveMessage.getTransactionData().getRequestHeaderMap().get(
                            traceIdKey.toLowerCase())));
        }

        // Insert receive status to RDS.
        if (businessReceiverStatusService
                .acquireExecutionRight(receiveMessage) == ExecutionRightAcquisitionState.FAILED) {
            throw new ExecutionRightAcquisitionException(
                    "Cannot get right to execute transaction. transactionId = "
                            + receiveMessage.getTransactionId());
        }

        // Insert status to DynamoDB.
        TransactionStatus dynamoStatus = new TransactionStatus();
        dynamoStatus.setTransactionId(transactionId);
        dynamoStatus.setBusinessStatus(createBusinessStatus(ProcessingStatus.PROCESSING));
        dynamoStatus.setRegistrationDate(TransactionDateUtil.createCurrentDateMinute());
        dynamoStatus.setExpirationTime(
                Instant.now().plus(expirationDays, ChronoUnit.DAYS).getEpochSecond());

        transactionStatusDynamoRepository.save(dynamoStatus);

        // Execute business process
        BusinessStatus businessReceiverStatus = businessReceiverStrategy.receive(receiveMessage);

        dynamoStatus.setRegistrationDate(TransactionDateUtil.createCurrentDateMinute());
        if (businessReceiverStatus == BusinessStatus.SUCCESS) {
            // Insert completion status to DynamoDB.
            dynamoStatus.setBusinessStatus(createBusinessStatus(ProcessingStatus.COMPLETION));
            transactionStatusDynamoRepository.save(dynamoStatus);

            // Publish to redis.
            transactionPublishingService.publish(receiveMessage);
        } else {
            // Insert error status to dynamoDB.
            dynamoStatus.setBusinessStatus(createBusinessStatus(ProcessingStatus.ERROR));
            transactionStatusDynamoRepository.save(dynamoStatus);
        }

    }

    /**
     * Create business status.
     * 
     * @param processingStatus Processing status.
     * @return Created business status.
     */
    private String createBusinessStatus(ProcessingStatus processingStatus) {
        String businessReceiverName = businessReceiverConfiguration.getBusinessReceiverName();

        // At this timing, the number of retries is necessarily zero.
        return String.join(TransactionStatus.BUSINESS_STATUS_SEPARATOR,
                String.valueOf(System.currentTimeMillis()), businessReceiverName,
                processingStatus.toString());
    }
}

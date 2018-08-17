/**
 * @(#)BusinessProcessorStatusServiceImpl.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.config.BusinessProcessorConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.dto.TBusinessProcessorStatus;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.repository.BusinessProcessorStatusMapper;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * This class that manipulates pointer information.
 */
@Slf4j
@Service
public class BusinessProcessorStatusServiceImpl implements BusinessProcessorStatusService {

    @Value("${transaction-dispatch.business-processor.partitioning.partition-number}")
    private long partitionNo;

    /** Spring cloud batch parameter. */
    @Autowired
    private BusinessProcessorConfiguration businessProcessorConfiguration;

    /** Partition calculation service. */
    @Autowired
    private BusinessProcessorPartitionService businessProcessorPartitionCalculationService;

    /** mapper. */
    @Autowired
    private BusinessProcessorStatusMapper businessProcessorControllerMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ExecutionRightAcquisitionState acquireExecutionRight(ReceiveMessage receiveMessage) {

        final String businessProcessName = businessProcessorConfiguration.getBusinessProcessName();
        final List<String> acceptTransactionType =
                businessProcessorConfiguration.getAcceptingTransactionType();

        // Calculate partition no.
        long transactionDataPartitionNo = businessProcessorPartitionCalculationService
                .calculationPartitionNo(receiveMessage.getTransactionId());

        if (transactionDataPartitionNo != partitionNo) {
            log.debug("This partitioning No ({}) is not be processed.(process-number: {})",
                    transactionDataPartitionNo, partitionNo);
            return ExecutionRightAcquisitionState.FAILED;
        }

        TBusinessProcessorStatus entity = new TBusinessProcessorStatus();
        entity.setTransactionId(receiveMessage.getTransactionId());
        entity.setBusinessProcessName(businessProcessName);
        entity.setCreateUser(String.valueOf(Thread.currentThread().getId()));
        entity.setRetryCount(receiveMessage.getTransactionData()
                .getTransactionDispatchControlInformation()
                .getRetryCount());
        entity.setPartitioningNo(transactionDataPartitionNo);
        long count = businessProcessorControllerMapper.insert(entity);

        if (count == 0L) {
            log.debug("Duplicate execute. transactionId={}/transactionType={}",
                    receiveMessage.getTransactionId(), acceptTransactionType);
            return ExecutionRightAcquisitionState.FAILED;
        } else {
            return ExecutionRightAcquisitionState.ACQUIRED;
        }

    }

}

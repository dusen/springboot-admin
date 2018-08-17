/**
 * @(#)BusinessReceiverStatusServiceImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fastretailing.dcp.storecommon.transaction.ExecutionRightAcquisitionState;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.config.BusinessReceiverConfiguration;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.dto.TBusinessReceiverStatus;
import com.fastretailing.dcp.storecommon.transaction.businessreceiver.repository.BusinessReceiverStatusMapper;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * Business receiver status table service implementation.
 */
@Slf4j
@Service
public class BusinessReceiverStatusServiceImpl implements BusinessReceiverStatusService {

    /** Business receiver configuration. */
    @Autowired
    private BusinessReceiverConfiguration businessReceiverConfiguration;

    /** mapper. */
    @Autowired
    private BusinessReceiverStatusMapper businessReceiverControllerMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ExecutionRightAcquisitionState acquireExecutionRight(ReceiveMessage receiveMessage) {

        TBusinessReceiverStatus entity = new TBusinessReceiverStatus();
        entity.setTransactionId(receiveMessage.getTransactionId());
        entity.setBusinessReceiverName(businessReceiverConfiguration.getBusinessReceiverName());
        entity.setCreateUser(String.valueOf(Thread.currentThread().getId()));
        entity.setRetryCount(receiveMessage.getTransactionData()
                .getTransactionDispatchControlInformation()
                .getRetryCount());
        long count = businessReceiverControllerMapper.insert(entity);

        if (count == 0L) {
            log.debug("Duplicate execute. transactionId={}", receiveMessage.getTransactionId());
            return ExecutionRightAcquisitionState.FAILED;
        } else {
            return ExecutionRightAcquisitionState.ACQUIRED;
        }

    }
}

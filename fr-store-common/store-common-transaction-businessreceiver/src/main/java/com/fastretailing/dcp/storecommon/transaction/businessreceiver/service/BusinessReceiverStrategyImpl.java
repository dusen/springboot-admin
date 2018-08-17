/**
 * @(#)BusinessReceiverStrategyImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fastretailing.dcp.common.exception.SystemException;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

/**
 * This strategy class will be handled in the business receiver class determined according to the
 * business type of the transaction data.
 */
@Service
public class BusinessReceiverStrategyImpl implements BusinessReceiverStrategy {

    /** List of business receiver services. */
    @Autowired
    private List<BusinessReceiverService> businessReceiverServiceList;

    /**
     * {@inheritDoc}
     */
    @Override
    public BusinessStatus receive(final ReceiveMessage receiveMessage) {

        // Use Transaction type as a key to decide business type.
        final String transactionId = receiveMessage.getTransactionId();
        final TransactionData transactionData = receiveMessage.getTransactionData();

        final BusinessStatus retVal = businessReceiverServiceList.stream()
                .filter(service -> service.checkTransactionType(transactionId, transactionData))
                .findFirst()
                .orElseThrow(() -> new SystemException())
                .receive(transactionId, transactionData);

        return retVal;
    }
}


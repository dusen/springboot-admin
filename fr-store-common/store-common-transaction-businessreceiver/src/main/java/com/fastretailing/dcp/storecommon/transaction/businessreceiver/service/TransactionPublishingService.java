/**
 * @(#)TransactionPublishingService.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import com.fastretailing.dcp.storecommon.transaction.businessreceiver.type.TransactionPublishState;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;

/**
 * This class publishes a transaction message to Redis.
 */
public interface TransactionPublishingService {

    /**
     * Publish transaction data.
     * 
     * @param receiveMessage Transaction list.
     * @return The published state of transaction to Redis.
     */
    TransactionPublishState publish(ReceiveMessage receiveMessage);
}

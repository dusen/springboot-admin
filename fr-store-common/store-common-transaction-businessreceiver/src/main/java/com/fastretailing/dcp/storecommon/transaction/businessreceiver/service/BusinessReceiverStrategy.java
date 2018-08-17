/**
 * @(#)BusinessReceiverStrategy.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceiveMessage;

/**
 * This strategy class will be handled in the business receiver class determined according to the
 * business type of the transaction data.
 */
public interface BusinessReceiverStrategy {

    /**
     * It is a strategy class that performs transit reception processing at an appropriate business
     * receiver according to the business type given to the transit data.
     * 
     * @param receiveMessage Transaction information.
     * @return Returns the result status of reception processing.
     */
    BusinessStatus receive(ReceiveMessage receiveMessage);

}

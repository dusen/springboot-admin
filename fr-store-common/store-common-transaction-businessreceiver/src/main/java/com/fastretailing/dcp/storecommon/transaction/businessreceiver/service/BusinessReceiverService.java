/**
 * @(#)BusinessReceiverService.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessreceiver.service;

import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

/**
 * The BusinessReceiverService interface must be implemented by all classes that do business
 * reception processing. A class must define a method called checkBusinessType that determines the
 * business type and a method called receive that receives business data.
 */
public interface BusinessReceiverService {

    /**
     * Receives business transaction data.
     * 
     * @param transactionId Transaction Id.
     * @param transactionData Transaction data.
     * @return Returns the result status of reception processing.
     */
    BusinessStatus receive(String transactionId, TransactionData transactionData);

    /**
     * Checks the business type to be processed with this service.
     * 
     * @param transactionId Transaction Id.
     * @param transactionData Transaction data.
     * @return true if the business type to process, true, otherwise false.
     */
    boolean checkTransactionType(String transactionId, TransactionData transactionData);

}

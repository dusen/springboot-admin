/**
 * @(#)BusinessProcessorService.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

/**
 * Interface for implementing business processes.
 */
public interface BusinessProcessorService {

    /**
     * Executes business processing with transaction data as an argument.
     * 
     * @param transactionId Transaction Id.
     * @param transactionData SQS receive data.
     * @return Business process result.
     */
    BusinessStatus execute(String transactionId, TransactionData transactionData);

    /**
     * Checks the business type to be processed with this service.
     * 
     * @param transactionId Transaction Id.
     * @param transactionData Transaction data.
     * @return true if the business type to process, true, otherwise false.
     */
    boolean checkTransactionType(String transactionId, TransactionData transactionData);
}

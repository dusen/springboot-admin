/**
 * @(#)TargetStubService.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import org.springframework.stereotype.Service;
import com.fastretailing.dcp.storecommon.transaction.BusinessStatus;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionData;

/**
 * Business processor stub service for unit test.
 */
@Service
public class TargetStubService implements BusinessProcessorService {

    /**
     * Executes business processing with transaction data as an argument.
     * 
     * @param transactionId Transaction ID.
     * @param transactionData Transaction data.
     * @return Fixed return value for success status.
     */
    @Override
    public BusinessStatus execute(String transactionId, TransactionData transactionData) {
        return BusinessStatus.SUCCESS;
    }

    /**
     * Checks the business type to be processed with this service.
     * 
     * @param transactionId Transaction ID.
     * @param transactionData Transaction data.
     * @return Fixed return value for processing with this service.
     */
    @Override
    public boolean checkTransactionType(String transactionId, TransactionData transactionData) {
        return true;
    }
    
}

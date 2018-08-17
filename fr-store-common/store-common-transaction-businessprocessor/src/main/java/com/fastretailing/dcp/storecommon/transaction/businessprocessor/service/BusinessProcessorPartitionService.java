/**
 * @(#)BusinessProcessorPartitionService.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

/**
 * Calculation partition no class.
 */
public interface BusinessProcessorPartitionService {

    /**
     * Calculate the partition number from the transaction ID.
     * 
     * @param transactionId Transaction Id.
     * @return Partition no.
     */
    long calculationPartitionNo(String transactionId);
}

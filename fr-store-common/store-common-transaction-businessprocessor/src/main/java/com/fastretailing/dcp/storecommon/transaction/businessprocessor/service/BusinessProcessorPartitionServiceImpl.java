/**
 * @(#)BusinessProcessorPartitionServiceImpl.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Partition number calculation service class.
 */
@Service
public class BusinessProcessorPartitionServiceImpl implements BusinessProcessorPartitionService {

    /**
     * Transaction id UUID part start index. String.valueOf(System.currentTimeMillis()).length() =
     * 13
     */
    private static final int UUID_START_INDEX = 14;

    /** Partition count. */
    @Value("${transaction-dispatch.business-processor.partitioning.total-count}")
    private int partitioningTotalCount;


    /**
     * {@inheritDoc}
     */
    @Override
    public long calculationPartitionNo(String transactionId) {

        long ret = Math.abs((UUID.fromString(transactionId.substring(UUID_START_INDEX))
                .getLeastSignificantBits() % partitioningTotalCount));
        
        return ret;
    }
}

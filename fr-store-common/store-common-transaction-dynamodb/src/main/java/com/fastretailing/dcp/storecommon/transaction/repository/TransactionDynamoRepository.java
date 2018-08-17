/**
 * @(#)TransactionDynamoRepository.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.fastretailing.dcp.storecommon.transaction.dto.ReceivedTransaction;

/**
 * This class is a repository that gets transaction information in the tranInfo table.
 */
@EnableScan
public interface TransactionDynamoRepository
        extends PagingAndSortingRepository<ReceivedTransaction, Long> {

    /**
     * Searches the received transaction with the transaction ID as the key.
     * 
     * @param transactionId Transaction ID to search.
     * @return Received transaction.
     */
    ReceivedTransaction findByTransactionId(String transactionId);

}

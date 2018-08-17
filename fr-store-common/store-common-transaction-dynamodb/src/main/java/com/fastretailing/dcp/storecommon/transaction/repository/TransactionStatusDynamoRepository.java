/**
 * @(#)TransactionStatusDynamoRepository.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.repository;

import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatus;

/**
 * This class is a repository that gets transaction information in the tranInfo table.
 */
@EnableScan
public interface TransactionStatusDynamoRepository
        extends PagingAndSortingRepository<TransactionStatus, String> {

    /**
     * Searches the received transaction with the transaction ID as the key.
     * 
     * @param transactionId Transaction ID to search.
     * @return Transaction status list.
     */
    List<TransactionStatus> findByTransactionId(String transactionId);
}

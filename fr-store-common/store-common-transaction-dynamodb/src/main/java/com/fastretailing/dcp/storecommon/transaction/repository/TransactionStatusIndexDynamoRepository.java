/**
 * @(#)TransactionStatusIndexDynamoRepository.java
 *
 *                                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.repository;

import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.fastretailing.dcp.storecommon.transaction.dto.TransactionStatusIndex;

/**
 * This class is a repository that gets transaction status by the global secondary index of
 * transaction status.
 */
@EnableScan
public interface TransactionStatusIndexDynamoRepository
        extends PagingAndSortingRepository<TransactionStatusIndex, String> {

    /**
     * Using secondary index, get data by registration date.
     * 
     * @param registrationDate Registration date.(yyyyMMddHHmm)
     * @return List for transaction status data.
     */
    List<TransactionStatusIndex> findByRegistrationDate(String registrationDate);

    /**
     * Using secondary index, get data that is between the specified registration dates.
     * 
     * @param registrationDateFrom Registration datetime from.(yyyyMMddHHmm)
     * @param registrationDateTo Registration datetime to.(yyyyMMddHHmm)
     * @return A list of status data.
     */
    List<TransactionStatusIndex> findByRegistrationDateBetween(String registrationDateFrom,
            String registrationDateTo);
}

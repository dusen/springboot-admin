/**
 * @(#)SalesTransactionHistoryDetailOptionalMapper.java
 *
 *                                                      Copyright (c) 2018 Fast Retailing
 *                                                      Corporation.
 */

package com.fastretailing.dcp.sales.common.repository.optional;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryDetail;
import com.fastretailing.dcp.sales.common.entity.optional.SalesTransactionHistoryDetailCondition;

/**
 * Sales transaction history detail optional mapper.
 */
@Mapper
public interface SalesTransactionHistoryDetailOptionalMapper {

    /**
     * Find all sales transaction history detail data by condition.
     *
     * @param salesTransactionHistoryDetailCondition Sales transaction history detail condition.
     * @return List of sales transaction history detail data.
     */
    List<SalesTransactionHistoryDetail> selectSalesTransactionHistoryDetailByCondition(
            SalesTransactionHistoryDetailCondition salesTransactionHistoryDetailCondition);

    /**
     * Select error contents name.
     *
     * @param salesTransactionHistoryDetail Sales transaction history detail.
     * @return Error contents name.
     */
    String selectErrorContentsName(SalesTransactionHistoryDetail salesTransactionHistoryDetail);

}

/**
 * @(#)AlterationHistorySalesTransactionOptionalMapper.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.sales.common.repository.optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;


/**
 * Alteration history sales transaction mapper.
 *
 */
@Mapper
public interface AlterationHistorySalesTransactionOptionalMapper {
    /**
     * Select sales error sales transaction by sales transaction id.
     * 
     * @param salesTransactionId Sales transaction id.
     * @param historyType History type.
     * @return Transaction import data.
     */
    TransactionImportData selectByErrorTransactionId(
            @Param("salesTransactionId") String salesTransactionId,
            @Param("historyType") Integer historyType);
}

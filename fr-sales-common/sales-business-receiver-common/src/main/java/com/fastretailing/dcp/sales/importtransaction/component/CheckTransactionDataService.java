/**
 * @(#)CheckTransactionDataService.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import com.fastretailing.dcp.sales.common.type.TransactionCheckResultType;
import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;

/**
 * The class is used to check transaction data.
 *
 */
public interface CheckTransactionDataService {

    /**
     * Check transaction data.
     * 
     * @param transactionImportData Transaction import data.
     * @param userId User id.
     * @param salesTransactionErrorId Transaction data error id.
     * @return TransactionCheckResultType
     */
    TransactionCheckResultType checkTransactionData(TransactionImportData transactionImportData,
            String userId, String salesTransactionErrorId);
}

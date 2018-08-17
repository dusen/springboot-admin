/**
 * @(#)InsertErrorService.java
 *
 *                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;


/**
 * The class is used to insert data to error table.
 *
 */
public interface InsertErrorService {
    /**
     * Insert transaction data to error table when error occurred.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Error sub number.
     * @param userId User id.
     */
    void insertErrorTable(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId);
}

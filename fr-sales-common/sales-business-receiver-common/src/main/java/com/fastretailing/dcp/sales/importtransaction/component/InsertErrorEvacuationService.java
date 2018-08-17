/**
 * @(#)InsertErrorEvacuationService.java
 *
 *                                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;

/**
 * The class is used to insert data to error evacuation table.
 *
 */
public interface InsertErrorEvacuationService {
    /**
     * Insert transaction data to error evacuation table.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Error sub number.
     * @param userId User id.
     */
    void insertErrorEvacuationTable(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId);
}

/**
 * @(#)InsertSalesCoreService.java
 *
 *                                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;

/**
 * The class is used to insert transaction data.
 *
 */
public interface InsertSalesCoreService {
    /**
     * Insert transaction data to sales core table.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Error sub number.
     * @param userId User id.
     * @throws Error evacuation exception.
     */
    void insertSalesCoreTable(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) throws ErrorEvacuationException;
}

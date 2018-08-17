/**
s * @(#)ErrorDataDeletionService.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;

/**
 * The class is used to delete transaction error data.
 *
 */
public interface ErrorDataDeletionService {

    /**
     * Delete transaction error data.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Error sub number.
     * @param userId User id.
     */
    void deleteErrorData(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId);
}

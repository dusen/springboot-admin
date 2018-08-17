/**
 * @(#)ErrorDataCorrectionService.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

import com.fastretailing.dcp.sales.importtransaction.dto.TransactionImportData;
import com.fastretailing.dcp.sales.importtransaction.exception.ErrorEvacuationException;

/**
 * The class is used to correct transaction data.
 *
 */
public interface ErrorDataCorrectionService {
    /**
     * Correct transaction data.
     * 
     * @param transactionImportData Transaction import data.
     * @param salesTransactionErrorId Error sub number.
     * @param userId User id.
     * @throws Error evacuation exception.
     */
    void correctErrorData(TransactionImportData transactionImportData,
            String salesTransactionErrorId, String userId) throws ErrorEvacuationException;
}

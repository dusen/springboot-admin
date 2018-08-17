/**
 * @(#)InsertSalesTransactionHistory.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.importtransaction.component;

/**
 * The class is used to insert data to transaction history table.
 *
 */
public interface InsertSalesTransactionHistory {
    /**
     * Insert data to sales transaction history table.
     * 
     * @param transactionImportData Transaction import data.
     * @param transactionId Transaction id.
     * @param userId User id.
     */
    public void insertIntoTSalesTransactionHistoryTable(String transactionImportData,
            String transactionId, String userId);
}

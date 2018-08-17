/**
 * @(#)SalesTransactionHistoryListService.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionhistorylist.service;

import com.fastretailing.dcp.sales.salestransactionhistorylist.form.SalesTransactionHistoryListForm;

/**
 * Sales transaction history list service.
 */
public interface SalesTransactionHistoryListService {

    /**
     * Get sales transaction history list form.
     *
     * @return Sales transaction history list form data.
     */
    SalesTransactionHistoryListForm getInitializeInformation(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm);

    /**
     * Get sales transaction history list .
     *
     * @return Sales transaction history list form data.
     */
    SalesTransactionHistoryListForm getSalesTransactionHistoryList(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm);

    /**
     * Get sorted sales transaction history list .
     *
     * @return Sales transaction history list form data.
     */
    SalesTransactionHistoryListForm getSortedSalesTransactionHistoryList(
            SalesTransactionHistoryListForm salesTransactionHistoryListForm);

}

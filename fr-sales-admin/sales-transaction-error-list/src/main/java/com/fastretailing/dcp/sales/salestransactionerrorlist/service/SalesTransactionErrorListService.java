/**
 * @(#)SalesTransactionErrorListService.java
 *
 *                                           Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.service;

import java.io.File;
import com.fastretailing.dcp.sales.salestransactionerrorlist.form.SalesTransactionErrorListForm;

/**
 * Sales transaction error list service.
 */
public interface SalesTransactionErrorListService {

    /**
     * Get sales transaction error list form.
     *
     * @param salesTransactionErrorListForm Sales transaction error list form.
     * @return Sales transaction error list form data.
     */
    SalesTransactionErrorListForm getInitializeInformation(
            SalesTransactionErrorListForm salesTransactionErrorListForm);

    /**
     * Get sales transaction error list .
     *
     * @param salesTransactionErrorListForm Sales transaction error list form.
     * @return Sales transaction error list form data.
     */
    SalesTransactionErrorListForm getSalesTransactionErrorList(
            SalesTransactionErrorListForm salesTransactionErrorListForm);

    /**
     * No Link Access.
     * 
     * @param salesTransactionErrorListForm Sales transaction error list form.
     */
    void numberLinkAccess(SalesTransactionErrorListForm salesTransactionErrorListForm);

    /**
     * Delete.
     * 
     * @param salesTransactionErrorListForm Sales transaction error list form.
     */
    void delete(SalesTransactionErrorListForm salesTransactionErrorListForm);

    /**
     * Update.
     * 
     * @param salesTransactionErrorListForm Sales transaction error list form.
     */
    void upload(SalesTransactionErrorListForm salesTransactionErrorListForm);

    /**
     * Download.
     * 
     * @param salesTransactionErrorListForm Sales transaction error list form.
     * @Return Zip file.
     */
    File download(SalesTransactionErrorListForm salesTransactionErrorListForm);

}

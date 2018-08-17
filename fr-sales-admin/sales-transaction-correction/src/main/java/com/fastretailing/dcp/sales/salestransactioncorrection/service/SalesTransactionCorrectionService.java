/**
 * @(#)SalesTransactionCorrectionService.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrection.service;

import com.fastretailing.dcp.sales.salestransactioncorrection.form.SalesTransactionCorrectionForm;

/**
 * Sales transaction correction service.
 */
public interface SalesTransactionCorrectionService {

    /**
     * Get sales transaction correction form.
     *
     * @return Sales transaction correction form data.
     */
    SalesTransactionCorrectionForm getSalesTransactionHeaderInformation(
            SalesTransactionCorrectionForm salesTransactionCorrectionForm);

    /**
     * Upload.
     * 
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     */
    void upload(SalesTransactionCorrectionForm salesTransactionCorrectionForm);

    /**
     * Audit.
     * 
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     */
    void audit(SalesTransactionCorrectionForm salesTransactionCorrectionForm);

    /**
     * Delete.
     *
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     */
    void delete(SalesTransactionCorrectionForm salesTransactionCorrectionForm);

    /**
     * Delete By Option.
     *
     * @param salesTransactionCorrectionForm Sales transaction correction form.
     */
    void deleteByOption(SalesTransactionCorrectionForm salesTransactionCorrectionForm);

    /**
     * Back to previous page.
     * 
     * @param salesTransactionCorrectionForm The form for this page.
     */
    void back(SalesTransactionCorrectionForm salesTransactionCorrectionForm);

}

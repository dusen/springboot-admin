/**
 * @(#)SalesTransactionCorrectionHistoryDetailService.java
 *
 *                                                         Copyright (c) 2018 Fast Retailing
 *                                                         Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.service;

import com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.form.SalesTransactionCorrectionHistoryDetailForm;

/**
 * Sales transaction correction history detail service.
 */
public interface SalesTransactionCorrectionHistoryDetailService {

    /**
     * Get sales transaction correction history detail form.
     *
     * @return Sales transaction correction history detail form data.
     */
    SalesTransactionCorrectionHistoryDetailForm getSalesTransactionHeaderInformation(
            SalesTransactionCorrectionHistoryDetailForm salesTransactionCorrectionHistoryDetailForm);

    /**
     * Get after correction sales transaction correction history detail form.
     *
     * @return Sales transaction correction history detail form data.
     */
    SalesTransactionCorrectionHistoryDetailForm getAfterCorrectionSalesTransactionHeaderInformation(
            SalesTransactionCorrectionHistoryDetailForm salesTransactionCorrectionHistoryDetailForm);

}

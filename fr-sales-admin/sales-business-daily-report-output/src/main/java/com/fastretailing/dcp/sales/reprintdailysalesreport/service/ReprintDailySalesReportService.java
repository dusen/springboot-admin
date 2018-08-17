/**
 * @(#)ReprintDailySalesReportService.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.service;

import com.fastretailing.dcp.sales.reprintdailysalesreport.form.ReprintDailySalesReportForm;

/**
 * Sales reprint daily sales report service.
 */
public interface ReprintDailySalesReportService {

    /**
     * Get reprint daily sales report form.
     *
     * @return Sales reprint daily sales report form data.
     */
    ReprintDailySalesReportForm getInitializeInformation(
            ReprintDailySalesReportForm reprintDailySalesReportForm);

    /**
     * Get reprint daily sales report list .
     *
     * @return Sales reprint daily sales report form data.
     */
    ReprintDailySalesReportForm getReprintDailySalesReportList(
            ReprintDailySalesReportForm reprintDailySalesReportForm);

    /**
     * Handle reprint daily sales report list .
     *
     * @return Sales reprint daily sales report form data.
     */
    ReprintDailySalesReportForm printReprintDailySalesReportList(
            ReprintDailySalesReportForm reprintDailySalesReportForm);
}

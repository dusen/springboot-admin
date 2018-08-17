/**
 * @(#)ReportUrlPath.java
 * 
 *                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.constant;

/**
 * Define the Class for report URL path.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface ReportUrlPath {

    /** Request URL base part. */
    public static final String REQUEST_BASE_PATH = "/{brand}/{region}/stores/{store_code}/reports";

    /** Request URL PATH for PDF out. */
    public static final String REQUEST_CREATE_PDF_URL_PATH = REQUEST_BASE_PATH + "/pdf";

    /** Request URL PATH for Excel out. */
    public static final String REQUEST_CREATE_EXCEL_URL_PATH = REQUEST_BASE_PATH + "/xlsx";

    /** Request URL PATH for Update report create status information. */
    public static final String REQUEST_UPDATE_CREATE_STATUS_URL_PATH =
            REQUEST_BASE_PATH + "/status";

    /** Request URL PATH for Get report data. */
    public static final String REQUEST_GET_REPORT_DATA_URL_PATH =
            REQUEST_BASE_PATH + "/{receipt_number}";

    /** Request URL PATH for Get report list. */
    public static final String REQUEST_GET_REPORT_LIST_URL_PATH = REQUEST_BASE_PATH;
}

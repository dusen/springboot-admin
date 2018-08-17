/**
 * @(#)ReportMessagesConstants.java
 * 
 *                                  Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.report.constant;

/**
 * Define the Class for report messages.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface ReportMessagesConstants {

    /** Validation error occurred on item in the request parameters. */
    public static final String ERROR_VALIDATION = "E_SIV_6900001";

    /** Failed to request REST service. */
    public static final String ERROR_FAILED_TO_REQUEST = "E_SIV_6900002";

    /** Failed to receipt the report order. */
    public static final String ERROR_FAILED_TO_RECEIPT = "E_SIV_6900003";

    /** SvfrClient error occurred. error code was {0}. */
    public static final String ERROR_SVF = "E_SIV_6900004";

    /** SvfrClient error occurred. error code was {0}. item name = {1}. */
    public static final String ERROR_SVF_ITEM = "E_SIV_6900005";

    /** Failed to save files on the report API server. */
    public static final String ERROR_SAVE_FILES = "E_SIV_6900006";

    /** Failed to access AwsS3 server. */
    public static final String ERROR_ACCESS_AWS3 = "E_SIV_6900007";

    /** Failed to access PF({0}) server. HttpStatus was {1}. */
    public static final String ERROR_ACCESS_PF = "E_SIV_6900008";

    /** Failed to update Status on the PF({0}) server. */
    public static final String ERROR_UPDATE_PF = "E_SIV_6900009";

    /** Failed to update Status on the PF({0}) server. cause was {1}. */
    public static final String ERROR_UPDATE_PF_CAUSE = "E_SIV_6900010";

    /** Http Status Code = {0}. */
    public static final String ERROR_HTTP_STATUS_CODE = "E_SIV_6900011";

    /** Form file is not exist on the AwsS3.[S3 bucket name = {0}. S3 key name = {1}]. */
    public static final String ERROR_FORM_FILE_IS_NOT_EXIST_ON_S3 = "E_SIV_6900012";

    /** There is no {0}. */
    public static final String ERROR_TARGET_SHEET_NOT_EXIST = "E_SIV_6900013";

    /** There is no report data. receipt number : {0}. */
    public static final String ERROR_NO_RECEIPT_DATA = "E_SIV_6900014";

    /** Data not found from report create status table. ReceptionNumber={0}. */
    public static final String ERROR_NO_DATA_CREATE_REPORT_STATUS = "E_SIV_6900015";

    /** Download report file from S3 failed. ErrorMessage={0}. */
    public static final String ERROR_FAILED_DOWNLOAD_REPORT_FILE = "E_SIV_6900016";

    /** Failed to update Status on the server. */
    public static final String ERROR_UPDATE_MANAGEMENT = "E_SIV_6900018";

    /** Validation error {0} is not null parameter. */
    public static final String ERROR_VALIDATION_API = "E_SIV_6910001";

    /** Validation error createReportStatus is not expected value. createReportStatus={0}. */
    public static final String ERROR_CREATE_REPORT_STATUS_INVALID = "E_SIV_6910002";

    /** Validation error autoPrintStatus is not expected value. autoPrintStatus={0}. */
    public static final String ERROR_AUTO_PRINT_STATUS_INVALID = "E_SIV_6910003";

    /** Validation error createReportStatus or autoPrintStatus is required. */
    public static final String ERROR_BOTH_STATUS_NULL = "E_SIV_6910004";

    /** Validation error receptionNumberList or reportIdList is required. */
    public static final String ERROR_BOTH_LIST_NULL = "E_SIV_6910005";

    /** Data not found from report create status table. ReceptionNumber={0}. */
    public static final String ERROR_NO_CREATE_STATUS_DATA = "E_SIV_6910006";

    /** There is no report master data. receipt id : {0}. */
    public static final String ERROR_NO_REPORT_MASTER_DATA = "E_SIV_6910007";

    /** Failed in generating download URL for report. ReceiptNumber={0} */
    public static final String ERROR_FAILED_GENERATING_DOWNLOAD_URL = "E_SIV_6910008";

    /** Platform not found. systemid={0}. */
    public static final String ERROR_SYSTEMID_INVALID = "E_EDG_6920001";

    /** Failed to get report list. ErrorMessage={0}. */
    public static final String ERROR_FAILED_GET_REPORT_LIST = "E_EDG_6920002";

    /** Status Code of get report list is not 200. Status={0}. */
    public static final String ERROR_INVALID_STATUS_GET_REPORT_LIST = "E_EDG_6920003";

    /** Response of get report list is null. */
    public static final String ERROR_GET_REPORT_LIST_NULL = "E_EDG_6920004";

    /** Failed to get report data. ErrorMessage={0}. */
    public static final String ERROR_FAILED_GET_REPORT_DATA = "E_EDG_6920005";

    /** Status Code of get report data is not 200. Status={0}. */
    public static final String ERROR_INVALID_STATUS_GET_REPORT_DATA = "E_EDG_6920006";

    /** Response of get report data is null. */
    public static final String ERROR_GET_REPORT_DATA_NULL = "E_EDG_6920007";

    /** Failed to execute outer command. ErrorMessage={0}. */
    public static final String ERROR_FAILED_EXECUTE_OUTER_COMMAND = "E_EDG_6920008";

    /** Failed to execute print document. ErrorMessage={0}. */
    public static final String ERROR_FAILED_EXECUTE_PRINT_DOCUMENT = "E_EDG_6920009";

    /** CreateReportStatus required 1 or 2. */
    public static final String ERROR_CREATE_REPORT_STATUS_NOT_EXPECT = "E_EDG_6920010";

    /** InterruptedException occurred. ErrorMessage={0}. */
    public static final String ERROR_INTERRUPTEDEXCEPTION_OCCURED = "E_EDG_6920011";

    /** Status Code of update auto print status is not 200. Status={0}. */
    public static final String ERROR_INVALID_STATUS_UPDATE_AUTO_PRINT_STATUS = "E_EDG_6920012";

    /** Failed to update auto print status. ErrorMessage={0}. */
    public static final String ERROR_FAILED_UPDATE_AUTO_PRINT_STATUS = "E_EDG_6920013";

    /** CreateReportStatus is not null parameter. ReceptioNumber={0} StoreCode={1}. */
    public static final String ERROR_CREATE_REPORT_STATUS_NULL = "E_EDG_6920014";

    /** AutoPrintStatus is not null parameter. ReceptioNumber={0} StoreCode={1}. */
    public static final String ERROR_AUTO_PRINT_STATUS_NULL = "E_EDG_6920015";

    /** Printer not found. PrinterName={0}. */
    public static final String ERROR_PRINTER_NOT_FOUND = "E_EDG_6920016";

    /** Failed to preserve report file. FilePath={0}. */
    public static final String ERROR_FAILED_PRESERVE_FILE = "E_EDG_6920017";

    /** Failed to delete report file for execute outer command. ErrorMessage={0} FilePath={1}. */
    public static final String ERROR_FAILED_DELETE_FILE = "E_EDG_6920018";

    /** Failed to delete report file.[S3 bucket name={0}. S3 key name={1}]. */
    public static final String ERROR_DELETE_FILE_FAIL = "E_EDG_62900003";
}

/**
 * @(#)Constants.java
 *
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.templates.constants;

/**
 * Constant class for testing. Define constants for use in Selenium.
 */
public final class Constants {

    /** Internet Explorer WebDriver. */
    public static final String IE_DRIVER = "/IEDriverServer.exe";

    /** Google Chrome WebDriver. */
    public static final String CHROME_DRIVER = "/chromedriver.exe";

    /** Processing wait time(second). */
    public static final int PROCESSING_WAIT_TIME = 10;

    /** Thread sleep time(milliseconds). */
    public static final int THREAD_SLEEP_TIME = 5000;
    
    /** Sales transaction correction history detail screen URL. */
    public static final String SALES_TRANSACTION_CORRECTION_URL =
            "http://localhost:8080/sales/v1/uq/ca/screen/sales-transaction-correction";

    /** Sales transaction error list screen URL. */
    public static final String EXPECTED_SALES_TRANSACTION_ERROR_LIST_URL =
            "http://localhost:8080/sales/v1/uq/ca/screen/sales-transaction-error-list";

}

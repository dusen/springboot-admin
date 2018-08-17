/**
 * @(#)Constants.java
 *
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.templates.constants;

/**
 * Constant class for testing. Define constants for use in selenium.
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

    /** Sales reprint daily sales report screen URL. */
    public static final String EXPECTED_SALES_REPRINT_DAILY_SALES_REPORT_URL =
            "http://localhost:8080/sales/v1/uq/ca/screen/1234567890/sales-reprint-daily-sales-report";

}

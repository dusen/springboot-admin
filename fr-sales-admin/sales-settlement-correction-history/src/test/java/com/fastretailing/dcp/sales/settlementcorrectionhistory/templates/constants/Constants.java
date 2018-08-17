/**
 * @(#)Constants.java
 *
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.templates.constants;

/**
 * Constant class for testing. Define constants for use in Selenium.
 */
public final class Constants {

    /** Internet explorer web driver. */
    public static final String IE_DRIVER = "/IEDriverServer.exe";

    /** Google chrome web driver. */
    public static final String CHROME_DRIVER = "/chromedriver.exe";

    /** Processing wait time(second). */
    public static final int PROCESSING_WAIT_TIME = 10;

    /** Thread sleep time(milliseconds). */
    public static final int THREAD_SLEEP_TIME = 2000;

    /** Settlement correction history list screen URL. */
    public static final String EXPECTED_SETTLEMENT_CORRECTION_HISTORY_LIST_URL =
            "http://localhost:8080/sales/v1/uq/jp/screen/sales-settlement-correction-history";

}

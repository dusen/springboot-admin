/**
 * @(#)Constants.java
 *
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.templates.constants;

/**
 * Constant class for testing. Define constants for use in selenium.
 */
public final class Constants {

    /** Internet explorer WebDriver. */
    public static final String IE_DRIVER = "/IEDriverServer.exe";

    /** Google chrome WebDriver. */
    public static final String CHROME_DRIVER = "/chromedriver.exe";

    /** Processing wait time(second). */
    public static final int PROCESSING_WAIT_TIME = 10;

    /** Thread sleep time(milliseconds). */
    public static final int THREAD_SLEEP_TIME = 5000;

    /** Declare irregular settlements screen URL. */
    public static final String DECLARE_IRREGULAR_SETTLEMENTS_URL =
            "http://localhost:8080/sales/v1/uq/ca/screen/sc001/declare-irregular-settlements";

}

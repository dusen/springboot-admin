/**
 * @(#)Constants.java
 *
 *                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.templates.constants;

/**
 * Constant class for testing. Define constants for use in Selenium.
 */
public class Constants {

    /** Internet Explorer WebDriver. */
    public static final String IE_DRIVER = "/IEDriverServer.exe";

    /** Google Chrome WebDriver. */
    public static final String CHROME_DRIVER = "/chromedriver.exe";

    /** Processing wait time(second). */
    public static final int PROCESSING_WAIT_TIME = 10;

    /** Thread sleep time(milliseconds). */
    public static final int THREAD_SLEEP_TIME = 5000;

    /** Sales payoff unmatch list screen URL. */
    public static final String SALES_PAYOFF_UNMATCH_LIST_URL =
            // "file:///D:/ICF_AutoCapsule_disabled/project/FR/%E5%8F%82%E8%80%83/SalesPayoffUnmatchList.htm";
            "http://localhost:8080/sales/v1/uq/ca/screen/unmatched-sales-list";
}

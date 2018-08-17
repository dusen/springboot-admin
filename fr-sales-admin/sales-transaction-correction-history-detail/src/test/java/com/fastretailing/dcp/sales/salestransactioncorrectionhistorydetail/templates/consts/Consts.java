/**
 * @(#)Consts.java
 *
 *                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactioncorrectionhistorydetail.templates.consts;

/**
 * Constant class for testing. Define constants for use in Selenium.
 */
public final class Consts {

    /** Internet Explorer WebDriver. */
    public static final String IE_DRIVER = "/IEDriverServer.exe";

    /** Google Chrome WebDriver. */
    public static final String CHROME_DRIVER = "/chromedriver.exe";

    /** Processing wait time(second). */
    public static final int PROCESSING_WAIT_TIME = 10;

    /** Thread sleep time(milliseconds). */
    public static final int THREAD_SLEEP_TIME = 5000;

    /** Sales transaction correction history detail screen URL. */
    public static final String SALES_TRANSACTION_CORRECTION_HISTORY_DETAIL_URL =
            "http://localhost:8080/sales/v1/uq/jp/screen/sales-transaction-correction-history-detail";

    /** Store setting screen URL. */
    public static final String STORE_SETTING_LIST_URL =
            "http://localhost:8080/sales/v1/uq/jp/screen/brand-country-setting-edit/list";

    /** Store setting screen URL. */
    public static final String STORE_SETTING_DETAIL_URL =
            "http://localhost:8080/sales/v1/uq/jp/screen/brand-country-setting-edit/detail";
}

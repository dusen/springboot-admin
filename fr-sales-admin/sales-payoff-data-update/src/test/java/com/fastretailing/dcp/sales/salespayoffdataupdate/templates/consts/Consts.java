/**
 * @(#)Consts.java
 *
 *                 Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.templates.consts;

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
    public static final int THREAD_SLEEP_TIME = 2000;

    /** Expected delivery list by item screen URL. */
    public static final String ALTERATION_DATA_UPLOAD_URL =
            "http://localhost:8080/sales/v1/uq/ca/screen/sales-settlement-unmatch-fix/index?"
                    + "systemBrandCode=01&systemCountryCode=17&storeCode=222&payoffDate=2018/05/12&cashRegisterNumber=134&integrityCheckType=1"
                    + "&brandName=UQ&countryName=CA&storeName=Yorkdale%20Shopping%20Centre&errorContent=Balance%20Mismatch";

}

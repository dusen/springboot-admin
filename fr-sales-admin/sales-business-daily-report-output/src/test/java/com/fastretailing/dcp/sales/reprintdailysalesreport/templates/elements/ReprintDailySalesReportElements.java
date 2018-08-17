/**
 * @(#)ReprintDailySalesReportElements.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales reprint daily sales report page.
 */
public class ReprintDailySalesReportElements extends BaseElements {

    /** System brand code. */
    @FindBy(xpath = "//input[@id='systemBrandCode']")
    public WebElement systemBrandCode;

    /** System country code. */
    @FindBy(xpath = "//input[@id='systemCountryCode']")
    public WebElement systemCountryCode;

    /** Display store code. */
    @FindBy(xpath = "//input[@id='displayStoreCode']")
    public WebElement displayStoreCode;

    /** Business date input. */
    @FindBy(xpath = "//input[@id='businessDate']")
    public WebElement businessDate;

    /** Clear button. */
    @FindBy(xpath = "//a[@id='clearButton']")
    public WebElement clearButton;

    /** Search button. */
    @FindBy(xpath = "//a[@id='searchButton']")
    public WebElement searchButton;
}

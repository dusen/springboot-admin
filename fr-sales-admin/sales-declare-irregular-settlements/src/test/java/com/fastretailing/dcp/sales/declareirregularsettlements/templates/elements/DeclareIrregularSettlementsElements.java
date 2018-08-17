/**
 * @(#)DeclareIrregularSettlementsElements.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of declare irregular settlements elements page.
 */
public class DeclareIrregularSettlementsElements extends BaseElements {

    /** Settlement date. */
    @FindBy(xpath = "//input[@id='settlementDate']")
    public WebElement settlementDate;

    /** Brand code pull down map. */
    @FindBy(xpath = "//div[@id='systemBrandCodeSelect']")
    public WebElement brandCodeMap;

    /** System brand code. */
    @FindBy(xpath = "//input[@id='systemBrandCode']")
    public WebElement systemBrandCode;

    /** System brand name. */
    @FindBy(xpath = "//input[@id='systemBrandName']")
    public WebElement systemBrandName;

    /** Country code pull down map. */
    @FindBy(xpath = "//div[@id='systemCountryCodeSelect']")
    public WebElement countryCodeMap;

    /** System country code. */
    @FindBy(xpath = "//input[@id='systemCountryCode']")
    public WebElement systemCountryCode;

    /** System country name. */
    @FindBy(xpath = "//input[@id='systemCountryName']")
    public WebElement systemCountryName;

    /** Store code. */
    @FindBy(xpath = "//input[@id='storeCode']")
    public WebElement storeCode;

    /** Display store code. */
    @FindBy(xpath = "//input[@id='displayStoreCode']")
    public WebElement displayStoreCode;

    /** Cash register number. */
    @FindBy(xpath = "//input[@id='cashRegisterNo']")
    public WebElement cashRegisterNo;

    /** Change fund integer. */
    @FindBy(xpath = "//input[@id='changeFundInteger']")
    public WebElement changeFundInteger;

    /** Change fund decimal. */
    @FindBy(xpath = "//div[@id='changeFundDecimal']")
    public WebElement changeFundDecimal;

    /** Closing balance integer. */
    @FindBy(xpath = "//input[@id='closingBalanceInteger']")
    public WebElement closingBalanceInteger;

    /** Closing balance decimal. */
    @FindBy(xpath = "//input[@id='closingBalanceDecimal']")
    public WebElement closingBalanceDecimal;

    /** Required balance. */
    @FindBy(xpath = "//input[@id='requiredBalance']")
    public WebElement requiredBalance;

    /** Value diff. */
    @FindBy(xpath = "//input[@id='valueDiff']")
    public WebElement valueDiff;

    /** End receipt number. */
    @FindBy(xpath = "//input[@id='endReceiptNo']")
    public WebElement endReceiptNo;

    /** Decimal size. */
    @FindBy(xpath = "//input[@id='decimalSize']")
    public WebElement decimalSize;

    /** Cash payment total. */
    @FindBy(xpath = "//input[@id='cashPaymentTotal']")
    public WebElement cashPaymentTotal;

    /** Clear button. */
    @FindBy(xpath = "//a[@id='clearButton']")
    public WebElement clearButton;

    /** Search button. */
    @FindBy(xpath = "//a[@id='searchButton']")
    public WebElement searchButton;

    /** Confirm button 1. */
    @FindBy(xpath = "//a[@id='confirmButton']")
    public WebElement confirmButton;

    /** Tender payment information list. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody")
    public WebElement tenderPaymentInformationList;

    /** Click date. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']"
            + "//a[@class='ui-datepicker-next ui-corner-all']")
    public WebElement dateNext;

    /** Select date. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']"
            + "//table[@class='ui-datepicker-calendar'] /tbody //tr[1]/td[7] /a[@class='ui-state-default']")
    public WebElement selectDate;
}

/**
 * @(#)SalesPayoffDataUpdateElements.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales payoff data update page.
 */
public class SalesPayoffDataUpdateElements extends BaseElements {

    @FindBy(xpath = "//a[@id='updateButton']")
    public WebElement updateButtonElement;

    @FindBy(xpath = "//input[@id='payoffDate']")
    public WebElement payoffDate;

    @FindBy(xpath = "//input[@id='brandName']")
    public WebElement brandName;

    @FindBy(xpath = "//input[@id='countryName']")
    public WebElement countryName;

    @FindBy(xpath = "//input[@id='storeCode']")
    public WebElement storeCode;

    @FindBy(xpath = "//input[@id='storeName']")
    public WebElement storeName;

    @FindBy(xpath = "//input[@id='cashRegisterNumber']")
    public WebElement cashRegisterNo;

    @FindBy(xpath = "//input[@id='errorContent']")
    public WebElement errorContent;

    @FindBy(xpath = "//input[@id='salesTransactionErrorStatus']")
    public WebElement salesTransactionErrorStatus;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.payoffTypeCode']")
    public WebElement salesPayoffDataList0PayoffTypeCode;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.integrityCheckType']")
    public WebElement salesPayoffDataList0IntegrityCheckType;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.pileUpPayoffDataSameDecideFlag']")
    public WebElement salesPayoffDataList0PileUpPayoffDataSameDecideFlag;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.payoffTypeName']")
    public WebElement salesPayoffDataList0PayoffTypeName;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.payoffTypeSubNumberCode']")
    public WebElement salesPayoffDataList0PayoffTypeSubNumberCode;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.payoffTypeSubNumberName']")
    public WebElement salesPayoffDataList0PayoffTypeSubNumberName;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.pileUpPayoffAmount']")
    public WebElement salesPayoffDataList0PileUpPayoffAmount;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.pileUpPayoffQuantity']")
    public WebElement salesPayoffDataList0PileUpPayoffQuantity;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.payoffAmount']")
    public WebElement salesPayoffDataList0PayoffAmount;

    @FindBy(xpath = "//input[@id='salesPayoffDataList0.payoffQuantity']")
    public WebElement salesPayoffDataList0payoffQuantity;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.payoffTypeCode']")
    public WebElement salesPayoffDataList1PayoffTypeCode;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.integrityCheckType']")
    public WebElement salesPayoffDataList1IntegrityCheckType;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.pileUpPayoffDataSameDecideFlag']")
    public WebElement salesPayoffDataList1PileUpPayoffDataSameDecideFlag;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.payoffTypeName']")
    public WebElement salesPayoffDataList1PayoffTypeName;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.payoffTypeSubNumberCode']")
    public WebElement salesPayoffDataList1PayoffTypeSubNumberCode;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.payoffTypeSubNumberName']")
    public WebElement salesPayoffDataList1PayoffTypeSubNumberName;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.pileUpPayoffAmount']")
    public WebElement salesPayoffDataList1PileUpPayoffAmount;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.pileUpPayoffQuantity']")
    public WebElement salesPayoffDataList1PileUpPayoffQuantity;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.payoffAmount']")
    public WebElement salesPayoffDataList1PayoffAmount;

    @FindBy(xpath = "//input[@id='salesPayoffDataList1.payoffQuantity']")
    public WebElement salesPayoffDataList1payoffQuantity;

    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[1]/td[1]")
    public WebElement td1;

    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[2]/td[1]")
    public WebElement td2;

    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[1]/td[7]")
    public WebElement td17;

    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[1]/td[8]")
    public WebElement td18;

    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[2]/td[7]")
    public WebElement td27;

    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[2]/td[8]")
    public WebElement td28;

    @FindBy(xpath = "//div[@id='information-confirm-box']" + "/div[@class='message-btns']/"
            + "a[@class='button skeleton primary small confirm-btn font-weight-bold']")
    public WebElement okElement;

    @FindBy(xpath = "//div[@id='error-box']/div[@id='box-body']")
    public WebElement boxBodyElement;
}

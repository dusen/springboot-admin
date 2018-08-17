/**
 * @(#)SalesTransactionErrorListElements.java
 *
 *                                            Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales transaction error list page.
 */
public class SalesTransactionErrorListElements extends BaseElements {
	
	@FindBy(xpath="//a[@class='noLink']")
	public WebElement noLink;
	
    /** Brand code pull down div. */
    @FindBy(xpath = "//div[@id='brandSelect']")
    public WebElement brandPullDownDiv;

    /** System brand code. */
    @FindBy(xpath = "//input[@id='systemBrandCode']")
    public WebElement systemBrandCode;

    /** Country code pull down div. */
    @FindBy(xpath = "//div[@id='countrySelect']")
    public WebElement countryPullDownDiv;

    /** System country code. */
    @FindBy(xpath = "//input[@id='systemCountryCode']")
    public WebElement systemCountryCode;

    /** Store code. */
    @FindBy(xpath = "//input[@id='storeCode']")
    public WebElement storeCodeInput;

    /** Cash register no input. */
    @FindBy(xpath = "//input[@id='cashRegisterNo']")
    public WebElement cashRegisterNoInput;

    /** Receipt no input. */
    @FindBy(xpath = "//input[@id='receiptNo']")
    public WebElement receiptNoInput;

    /** Sales transaction type pull down div. */
    @FindBy(xpath = "//div[@id='salesTransactionTypeSelect']")
    public WebElement salesTransactionTypePullDownDiv;

    /** Sales transaction type input. */
    @FindBy(xpath = "//input[@id='salesTransactionType']")
    public WebElement salesTransactionTypeInput;

    /** Data creation date from input. */
    @FindBy(xpath = "//input[@id='dataCreationDateFrom']")
    public WebElement dataCreationDateFromInput;

    /** Data creation date to input. */
    @FindBy(xpath = "//input[@id='dataCreationDateTo']")
    public WebElement dataCreationDateToInput;

    /** Business date from input. */
    @FindBy(xpath = "//input[@id='businessDateFrom']")
    public WebElement businessDateFromInput;

    /** Business date to input. */
    @FindBy(xpath = "//input[@id='businessDateTo']")
    public WebElement businessDateToInput;

    /** Error contents pull down div. */
    @FindBy(xpath = "//div[@id='errorContentsSelect']")
    public WebElement errorContentsPullDownDiv;

    /** Error contents input. */
    @FindBy(xpath = "//input[@id='errorContents']")
    public WebElement errorContentsInput;

    /** Category option 1. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[1]")
    public WebElement categoryOption1;

    /** Category option 2. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[2]")
    public WebElement categoryOption2;

    /** Category option 3. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[3]")
    public WebElement categoryOption3;

    /** Category option 4. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[4]")
    public WebElement categoryOption4;

    /** Category option 5. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[5]")
    public WebElement categoryOption5;

    /** Category option 6. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[6]")
    public WebElement categoryOption6;

    /** Category option 7. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[7]")
    public WebElement categoryOption7;

    /** Category option 8. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[8]")
    public WebElement categoryOption8;

    /** Category option 9. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[9]")
    public WebElement categoryOption9;

    /** Category option 10. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[10]")
    public WebElement categoryOption10;

    /** Category option 11. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[11]")
    public WebElement categoryOption11;

    /** Category option 12. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[12]")
    public WebElement categoryOption12;

    /** Category option 13. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[13]")
    public WebElement categoryOption13;

    /** Category option 14. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[14]")
    public WebElement categoryOption14;

    /** Category option 15. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[15]")
    public WebElement categoryOption15;

    /** Category option 16. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[16]")
    public WebElement categoryOption16;

    /** Category option 17. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[17]")
    public WebElement categoryOption17;

    /** Category option 18. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[18]")
    public WebElement categoryOption18;

    /** Category option 19. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[19]")
    public WebElement categoryOption19;

    /** Category option 20. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[20]")
    public WebElement categoryOption20;

    /** Category option 21. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[21]")
    public WebElement categoryOption21;

    /** Clear button. */
    @FindBy(xpath = "//a[@id='clearButton']")
    public WebElement clearButton;

    /** Search button. */
    @FindBy(xpath = "//a[@id='searchButton']")
    public WebElement searchButton;

    /** Sales transaction error list. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody")
    public WebElement salesTransactionErrorList;
    
    /** Expected Delivery List. */
    @FindBy(xpath = "//table[@class='common-table width100per cell-color-change orderInformationTabTable']/tbody")
    public WebElement salesTransactionCorrectionList;

    /** Sales transaction header. */
    @FindBy(xpath = "//a[@class='tablinks padding5 salesTransactionHeaderTab']")
    public WebElement salesTransactionHeader;

    /** Expected Delivery List. */
    @FindBy(xpath = "//tbody[@id='salesDetail0111142661801221200-1990020-01']")
    public WebElement itemDetailList;

    @FindBy(xpath = "//div[@id='salesTransactionHeaderTab_1']/div[2]/div")
    public WebElement salesTransactionHeaderTabListOne;

    /** Sales transaction header tax. */
    @FindBy(xpath = "//a[@class='tablinks padding5 tax']")
    public WebElement salesTransactionHeaderTax;

    /** Sales transaction header. */
    @FindBy(xpath = "//a[@class='tablinks padding5 payment']")
    public WebElement salesTransactionHeaderPayment;

    /** Sales transaction header. */
    @FindBy(xpath = "//a[@class='tablinks padding5 total']")
    public WebElement salesTransactionHeaderTotal;

    /** Expected Delivery List. */
    @FindBy(xpath = "//tbody[@id='taxTbody']")
    public WebElement taxList;

    /** Expected Delivery List. */
    @FindBy(xpath = "//tbody[@id='paymentTbody']")
    public WebElement paymentList;

    /** Expected Delivery List. */
    @FindBy(xpath = "//tbody[@id='totalTbody']")
    public WebElement totalList;
}

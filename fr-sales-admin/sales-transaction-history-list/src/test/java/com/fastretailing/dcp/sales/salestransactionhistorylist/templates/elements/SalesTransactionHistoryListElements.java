/**
 * @(#)SalesTransactionHistoryListElements.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionhistorylist.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales transaction history list page.
 */
public class SalesTransactionHistoryListElements extends BaseElements {

    /** Brand code pull down div. */
    @FindBy(xpath = "//div[@id='brandSelect']")
    public WebElement brandPullDownDiv;

    /** System brand code. */
    @FindBy(xpath = "//input[@id='systemBrandCode']")
    public WebElement systemBrandCodeInput;

    /** Country code pull down div. */
    @FindBy(xpath = "//div[@id='countrySelect']")
    public WebElement countryPullDownDiv;

    /** System country code. */
    @FindBy(xpath = "//input[@id='systemCountryCode']")
    public WebElement systemCountryCodeInput;

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

    /** Corrector code input. */
    @FindBy(xpath = "//input[@id='correctorCode']")
    public WebElement correctorCodeInput;

    /** Correction date from input. */
    @FindBy(xpath = "//input[@id='correctionDateFrom']")
    public WebElement correctionDateFromInput;

    /** Correction date to input. */
    @FindBy(xpath = "//input[@id='correctionDateTo']")
    public WebElement correctionDateToInput;

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

    /** Category option 22. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[22]")
    public WebElement categoryOption22;

    /** Category option 23. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[23]")
    public WebElement categoryOption23;

    /** Category option 24. */
    @FindBy(xpath = "//div[@class='select-elem active']/ul/li[24]")
    public WebElement categoryOption24;

    /** Clear button. */
    @FindBy(xpath = "//a[@id='clearButton']")
    public WebElement clearButton;

    /** Search button. */
    @FindBy(xpath = "//a[@id='searchButton']")
    public WebElement searchButton;

    /** Search button. */
    @FindBy(xpath = "//tr[@class='v-align-middle']/th[6]/a")
    public WebElement sortKey;

    /** Sales transaction history list. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody")
    public WebElement salesTransactionHistoryList;
    
    /** For detail link. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[@class='v-align-middle'][2]/td[1]/a[@class='noLink']")
    public WebElement forDetailLink;
    
    /** Sales transaction correction list. */
    @FindBy(xpath = "//table[@class='common-table width100per cell-color-change orderInformationTabTable']/tbody")
    public WebElement salesTransactionCorrectionList;
    
    /** item detail list. */
    @FindBy(xpath = "//tbody[@id='salesDetail1']")
    public WebElement itemDetailList;
    
    /** Sales transaction header. */
    @FindBy(xpath = "//a[@class='tablinks padding5 salesTransactionHeaderTab']")
    public WebElement salesTransactionHeader;

    /** Sales transaction header tab list one. */
    @FindBy(xpath = "//div[@id='salesTransactionHeaderTab_1']/div[1]/div")
    public WebElement salesTransactionHeaderTabListOne;

    /** Sales transaction header tax. */
    @FindBy(xpath = "//a[@class='tablinks padding5 tax']")
    public WebElement salesTransactionHeaderTax;

    /** Sales transaction header payment. */
    @FindBy(xpath = "//a[@class='tablinks padding5 payment']")
    public WebElement salesTransactionHeaderPayment;

    /** Sales transaction header total. */
    @FindBy(xpath = "//a[@class='tablinks padding5 total']")
    public WebElement salesTransactionHeaderTotal;

    /** Tax list. */
    @FindBy(xpath = "//tbody[@class='taxTbody']")
    public WebElement taxList;

    /** Payment list. */
    @FindBy(xpath = "//tbody[@class='paymentTbody']")
    public WebElement paymentList;

    /** Total list. */
    @FindBy(xpath = "//tbody[@id='totalTbody']")
    public WebElement totalList;
}

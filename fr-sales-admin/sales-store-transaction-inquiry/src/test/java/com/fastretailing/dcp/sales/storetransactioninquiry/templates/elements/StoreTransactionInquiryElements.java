/**
 * @(#)StoreTransactionInquiryElements.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.templates.elements;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales transaction history list page.
 */
public class StoreTransactionInquiryElements extends BaseElements {

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

    /** Business date. */
    @FindBy(xpath = "//input[@id='businessDate']")
    public WebElement businessDateInput;

    /** Cash register no input. */
    @FindBy(xpath = "//input[@id='cashRegisterNo']")
    public WebElement cashRegisterNoInput;

    /** Data creation date from input. */
    @FindBy(xpath = "//input[@id='dataCreationDateFrom']")
    public WebElement dataCreationDateFromInput;

    /** Data creation time from input. */
    @FindBy(xpath = "//input[@id='dataCreationTimeFrom']")
    public WebElement dataCreationTimeFromInput;

    /** Data creation date to input. */
    @FindBy(xpath = "//input[@id='dataCreationTimeTo']")
    public WebElement dataCreationTimeToInput;

    /** Data creation date to input. */
    @FindBy(xpath = "//input[@id='dataCreationDateTo']")
    public WebElement dataCreationDateToInput;

    /** Sales transaction type pull down div. */
    @FindBy(xpath = "//div[@id='salesTransactionTypeSelect']")
    public WebElement salesTransactionTypePullDownDiv;

    /** Sales transaction type input. */
    @FindBy(xpath = "//input[@id='salesTransactionType']")
    public WebElement salesTransactionTypeInput;

    /** Cahser code. */
    @FindBy(xpath = "//input[@id='casherCode']")
    public WebElement casherCodeInput;

    /** Membership id. */
    @FindBy(xpath = "//input[@id='membershipId']")
    public WebElement membershipIdInput;

    /** Receipt no from. */
    @FindBy(xpath = "//input[@id='receiptNoFrom']")
    public WebElement receiptNoFromInput;

    /** Receipt no to. */
    @FindBy(xpath = "//input[@id='receiptNoTo']")
    public WebElement receiptNoToInput;

    /** Payment tender group pull down div. */
    @FindBy(xpath = "//div[@id='paymentTenderGroupSelect']")
    public WebElement paymentTenderGroupPullDownDiv;

    /** Payment tender group input. */
    @FindBy(xpath = "//input[@id='paymentTenderGroup']")
    public WebElement paymentTenderGroupInput;

    /** Payment tender id pull down div. */
    @FindBy(xpath = "//div[@id='paymentTenderGroupSelect']")
    public WebElement paymentTenderIdPullDownDiv;

    /** Payment tender id input. */
    @FindBy(xpath = "//input[@id='paymentTenderId']")
    public WebElement paymentTenderIdInput;

    /** Payment amount from Input. */
    @FindBy(xpath = "//input[@id='paymentAmountFrom']")
    public WebElement paymentAmountFromInput;

    /** Payment amount to Input. */
    @FindBy(xpath = "//input[@id='paymentAmountTo']")
    public WebElement paymentAmountToInput;

    /** Deposit amount from. */
    @FindBy(xpath = "//input[@id='depositAmountFrom']")
    public WebElement depositAmountFromInput;

    /** Deposit amount to. */
    @FindBy(xpath = "//input[@id='depositAmountTo']")
    public WebElement depositAmountToInput;

    /** Change amount from. */
    @FindBy(xpath = "//input[@id='changeAmountFrom']")
    public WebElement changeAmountFromInput;

    /** Change amount to. */
    @FindBy(xpath = "//input[@id='changeAmountTo']")
    public WebElement changeAmountToInput;

    /** Taxation type pull down div. */
    @FindBy(xpath = "//div[@id='taxationTypeSelect']")
    public WebElement taxationTypePullDownDiv;

    /** Taxation type input. */
    @FindBy(xpath = "//input[@id='taxationType']")
    public WebElement taxationTypeInput;

    /** Non merchandise item pull down div. */
    @FindBy(xpath = "//div[@id='nonMerchandiseItemSelect']")
    public WebElement nonMerchandiseItemPullDownDiv;

    /** Non merchandise item input. */
    @FindBy(xpath = "//input[@id='nonMerchandiseItemCode']")
    public WebElement nonMerchandiseItemInput;

    /** Discount type pull down div. */
    @FindBy(xpath = "//div[@id='discountTypeSelect']")
    public WebElement discountTypePullDownDiv;

    /** Discount type input. */
    @FindBy(xpath = "//input[@id='discountType']")
    public WebElement discountTypeInput;

    /** Item code. */
    @FindBy(xpath = "//input[@id='itemCode']")
    public WebElement itemCodeInput;

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

    /** Number of transaction. */
    @FindBy(xpath = "//input[@id='numberOfTransaction']")
    public WebElement numberOfTransaction;

    /** Header. */
    @FindBy(xpath = "//div[@id='detailDiv']/span[@class='headerSub']")
    public List<WebElement> headerList;

    /** Item list. */
    @FindBy(xpath = "//div[@id='detailDiv']/span[@class='itemSub']")
    public List<WebElement> itemList;

    /** Discount list. */
    @FindBy(xpath = "//div[@id='detailDiv']/span[@class='discountSub']")
    public List<WebElement> discountList;

    /** Tax list. */
    @FindBy(xpath = "//div[@id='detailDiv']/span[@class='taxSub']")
    public List<WebElement> taxList;

    /** Tender list. */
    @FindBy(xpath = "//div[@id='detailDiv']/span[@class='tenderSub']")
    public List<WebElement> tenderList;

}

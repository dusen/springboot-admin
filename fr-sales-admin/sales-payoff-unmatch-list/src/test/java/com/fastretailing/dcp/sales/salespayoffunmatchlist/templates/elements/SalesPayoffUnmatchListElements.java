/**
 * @(#)SalesPayoffUnmatchListElements.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffunmatchlist.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales payoff unmatch list page.
 */
public class SalesPayoffUnmatchListElements extends BaseElements {

    /** Brand name select box. */
    @FindBy(id = "brandSelect")
    public WebElement brandNameSelectBox;
    /** Brand name input. */
    @FindBy(id = "systemBrandName")
    public WebElement brandNameInput;

    /** Country select box. */
    @FindBy(id = "countrySelect")
    public WebElement countrySelectBox;
    /** Country input. */
    @FindBy(id = "systemCountryName")
    public WebElement countryInput;

    /** Store code input. */
    @FindBy(id = "viewStoreCode")
    public WebElement storeCodeInput;

    /** Register No input. */
    @FindBy(id = "cashRegisterNo")
    public WebElement registerNoInput;

    /** Payoff date from input. */
    @FindBy(id = "payoffDateFrom")
    public WebElement payoffDateFromInput;

    /** Payoff date to input. */
    @FindBy(id = "payoffDateTo")
    public WebElement payoffDateToInput;

    /** Error contents select box. */
    @FindBy(id = "errorContentsSelect")
    public WebElement errorContentsSelectBox;
    /** Error contents input. */
    @FindBy(id = "errorContentsName")
    public WebElement errorContentsInput;

    /** Clear button. */
    @FindBy(id = "clearButton")
    public WebElement clearButton;

    /** Searc button. */
    @FindBy(id = "searchButton")
    public WebElement searchButton;

    /** Audit button. */
    @FindBy(id = "auditButton")
    public WebElement auditButton;

    /** Sales payoff unmatch list. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody")
    public WebElement salesPayoffUnmatchList;
}

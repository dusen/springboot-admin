/**
 * @(#)SalesPayoffUnmatchListElements.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.stockstatusrefer.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of sales payoff unmatch list page.
 */
public class StockStatusReferElements extends BaseElements {

    /** Item code radio button. */
    @FindBy(id = "ItemCodeLabelRadiobutton")
    public WebElement itemCodeRadioBUtton;
    /** G department code radio button. */
    @FindBy(id = "GDeptClassLabelRadiobutton")
    public WebElement gDepartmentCodeRadioButton;
    /** PLU code radio button. */
    @FindBy(id = "PluCodeLabelRadiobutton")
    public WebElement pluCodeRaioButton;

    /** Stock status select box. */
    @FindBy(xpath = "//*[@id='ListStockStatus']/../..")
    public WebElement stockStatusSelectBox;
    /** Stock status input. */
    @FindBy(id = "ListStockStatus")
    public WebElement stockStatusInput;

    /** Item code input. */
    @FindBy(id = "InputTextItemCode")
    public WebElement itemCodeInput;

    /** Item color select box. */
    @FindBy(xpath = "//*[@id='ListItemColor']/../..")
    public WebElement itemColerSelectBox;
    /** Item color input. */
    @FindBy(id = "ListItemColor")
    public WebElement itemColerInput;

    /** Item size select box. */
    @FindBy(xpath = "//*[@id='ListItemSize']/../..")
    public WebElement itemSizeSelectBox;
    /** Item size input. */
    @FindBy(id = "ListItemSize")
    public WebElement itemSizeInput;

    /** Pattern length select box. */
    @FindBy(xpath = "//*[@id='ListPatternLength']/../..")
    public WebElement patternLengthSelectBox;
    /** Pattern length input. */
    @FindBy(id = "ListPatternLength")
    public WebElement patternLengthInput;

    /** Department name select box. */
    @FindBy(xpath = "//*[@id='ListDeptName']/../..")
    public WebElement departmentNameSelectBox;
    /** Department name input. */
    @FindBy(id = "ListDeptName")
    public WebElement departmentNameInput;

    /** PLU code input. */
    @FindBy(id = "InputTextPluCode")
    public WebElement pluCodeInput;

    /** Clear button. */
    @FindBy(id = "ButtonClear")
    public WebElement clearButton;
    /** Display button. */
    @FindBy(id = "ButtonDisplay")
    public WebElement displayButton;

    /** All stock quantity total. */
    @FindBy(id = "LabelAllStockQuantityTotal")
    public WebElement allStockQuantityTotal;
    /** Stock quantity total. */
    @FindBy(id = "LabelStockQuantityTotal")
    public WebElement stockQuantityTotal;
    /** Write off expected total. */
    @FindBy(id = "LabelWriteOffExpectedTotal")
    public WebElement writeOffExpectedTotal;
    /** Customer order total. */
    @FindBy(id = "LabelCustomerOrderTotal")
    public WebElement customerOrderTotal;
    /** Investigating stock total. */
    @FindBy(id = "LabelInvestigatingStockTotal")
    public WebElement investigatingStockTotal;
    /** Store stock total. */
    @FindBy(id = "LabelStoreStockTotal")
    public WebElement storeStockTotal;

    /** All stock quantity list total. */
    @FindBy(id = "LabelAllStockQuantityListTotal")
    public WebElement allStockQuantityListTotal;
    /** Stock quantity list total. */
    @FindBy(id = "LabelStockQuantityListTotal")
    public WebElement stockQuantityListTotal;
    /** Write off expected list total. */
    @FindBy(id = "LabelWriteOffExpectedListTotal")
    public WebElement writeOffExpectedListTotal;
    /** Customer order list total. */
    @FindBy(id = "LabelCustomerOrderListTotal")
    public WebElement customerOrderListTotal;
    /** Investigating stock list total. */
    @FindBy(id = "LabelInvestigatingStockListTotal")
    public WebElement investigatingStockListTotal;
    /** Store stock list total. */
    @FindBy(id = "LabelStoreStockListTotal")
    public WebElement storeStockListTotal;

    /** Stock status list. */
    @FindBy(xpath = "//table[@id='DisplayDataTable']/tbody")
    public WebElement stockStatusList;
}

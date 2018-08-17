/**
 * @(#)ExpectedDeliveryListByItemElements.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of brand country setting edit page.
 */
public class SalesBrandCountrySettingEditElements extends BaseElements {

    @FindBy(xpath = "//a[@id='searchButton']")
    public WebElement searchButton;

    /** Brand country setting edit List. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody")
    public WebElement brandCountrySettingEditList;

    /** Number link box. */
    @FindBy(xpath = "//a[@class='for-store-list']")
    public WebElement numberLink;

    /** Number link box. */
    @FindBy(xpath = "//table[@id='dataTable']/tbody/tr[2]/td[1]")
    public WebElement detailNumberLink;

    /** Brand Pull Down Clear. */
    @FindBy(xpath = "//div[@class='select-elem'][1]/ul/li[2]/span")
    public WebElement selectElemListUq;
    
    /** Brand Pull Down Clear. */
    @FindBy(xpath = "//input[@id='viewStoreCode']")
    public WebElement viewStoreCodeInput;
}

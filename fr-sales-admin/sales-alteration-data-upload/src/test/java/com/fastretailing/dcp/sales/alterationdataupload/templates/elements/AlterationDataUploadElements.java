/**
 * @(#)TransferOutInstructionReferenceDetailElements.java
 *
 *                                                        Copyright (c) 2018 Fast Retailing
 *                                                        Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The elements of transfer out instruction reference detail item page.
 */
public class AlterationDataUploadElements extends BaseElements {
    /** Transfer out instruction detail item list. */
    @FindBy(xpath = "//input[@id='file']")
    public WebElement fileElement;

    @FindBy(xpath = "//a[@id='uploadButton']")
    public WebElement uploadButtonElement;

    @FindBy(xpath = "//div[@id='type1']/label[@class='transactionData']")
    public WebElement alterationDataType1Element;

    @FindBy(xpath = "//div[@id='type2']/label[@class='payoffData']")
    public WebElement alterationDataType2Element;

    @FindBy(xpath = "//div[@id='information-confirm-box']"
            + "/div[@class='message-btns']/a[@class='button skeleton primary small confirm-btn font-weight-bold']")
    public WebElement okElement;

    @FindBy(xpath = "//div[@id='information-box']/div[@id='box-body']")
    public WebElement boxBodyElementInfo;

    @FindBy(xpath = "//div[@id='error-box']/div[@id='box-body']")
    public WebElement boxBodyElementError;
}

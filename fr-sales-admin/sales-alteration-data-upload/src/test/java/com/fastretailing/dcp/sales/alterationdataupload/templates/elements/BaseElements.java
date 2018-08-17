/**
 * @(#)BaseElements.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The common elements of page.
 */
public class BaseElements {

    /** Store code. */
    @FindBy(xpath = "//input[@name='storeCode']")
    public WebElement storeCodeElement;

    /** Country code. */
    @FindBy(xpath = "//input[@name='countryCode']")
    public WebElement countryCodeElement;

    /** Default locale. */
    @FindBy(xpath = "//input[@name='defaultLocale']")
    public WebElement defaultLocaleElement;

    /** Specify locale. */
    @FindBy(xpath = "//input[@name='specifyLocale']")
    public WebElement specifyLocaleElement;

    /** Brand. */
    @FindBy(xpath = "//input[@name='brandCode']")
    public WebElement brandCodeElement;

    /** Header. */
    @FindBy(xpath = "//div[@class='header']")
    public WebElement headerElement;

    /** Main body. */
    @FindBy(xpath = "//div[@class='contents']")
    public WebElement mainBodyElement;

    /** Footer. */
    @FindBy(xpath = "//div[@class='footer']")
    public WebElement footerElement;

    /** Progress dialog. */
    @FindBy(xpath = "//div[@id='progress-box']")
    public WebElement progressDialogElement;

    /** Error dialog. */
    @FindBy(xpath = "//div[@id='error-box']")
    public WebElement errorDialogElement;

    /** Warning dialog. */
    @FindBy(xpath = "//div[@id='warning-box']")
    public WebElement warningDialogElement;

    /** Warning confirm dialog. */
    @FindBy(xpath = "//div[@id='warning-confirm-box']")
    public WebElement warningConfirmDialogElement;

    /** Information dialog. */
    @FindBy(xpath = "//div[@id='information-box']")
    public WebElement informationDialogElement;

    /** Information confirm dialog. */
    @FindBy(xpath = "//div[@id='information-confirm-box']")
    public WebElement informationConfirmDialogElement;

    /** Generally message dialog. */
    @FindBy(xpath = "//div[@id='message-box']")
    public WebElement generallyMessageDialogElement;

    /** Generally dialog. */
    @FindBy(xpath = "//div[@id='dialog-box']")
    public WebElement generallyDialogElement;
}

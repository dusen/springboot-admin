/**
 * @(#)BaseElements.java
 *
 *                       Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.stockstatusrefer.templates.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The common elements of page.
 */
public class BaseElements {

    /** Title. */
    @FindBy(xpath = "//div[@class='header']/h1[@class='title']")
    public WebElement title;

    /** Progress box. */
    @FindBy(id = "progress-box")
    public WebElement progressBox;

    /** Date picker. */
    @FindBy(id = "ui-datepicker-div")
    public WebElement datepicker;

    /** Year of date picker. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']//*[@class='ui-datepicker-year']")
    public WebElement datepickerYear;

    /** Month of date picker. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']//*[@class='ui-datepicker-month']")
    public WebElement datepickerMonth;

    /** Prev of date picker. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']//*[@title='Prev']")
    public WebElement datepickerPrev;

    /** Next of date picker. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']//*[@title='Next']")
    public WebElement datepickerNext;

    /** Calendar of date picker. */
    @FindBy(xpath = "//div[@id='ui-datepicker-div']//table[@class='ui-datepicker-calendar']")
    public WebElement datepickerCalendar;
}

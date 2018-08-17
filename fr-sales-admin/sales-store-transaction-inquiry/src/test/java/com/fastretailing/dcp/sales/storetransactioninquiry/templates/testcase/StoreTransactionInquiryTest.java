/**
 * @(#)StoreTransactionInquiryTest.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.templates.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.storetransactioninquiry.templates.constants.Constants;
import com.fastretailing.dcp.sales.storetransactioninquiry.templates.elements.StoreTransactionInquiryElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of store transaction inquiry screen.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)

@Sql(scripts = "/junit_create_table_sales_test.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_test.sql",
        executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class StoreTransactionInquiryTest {
    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected StoreTransactionInquiryElements element;

    /** The environment in current application. */
    @Autowired
    protected Environment environment;

    /**
     * Initialization processing. Configure the browser to be tested. Set Element information of the
     * screen to be tested to web driver.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Before
    public abstract void setUp() throws Exception;

    /**
     * Test end processing. Close the displayed browser.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @After
    public void tearDown() throws Exception {
        webDriver.quit();
    }

    /**
     * Show sales transaction history list page. Condition： Search button display. Result
     * confirmation： The expected sales transaction history list page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("StoreTransactionInquiryTest.xml")
    public void testStoreTransactionInquiry01() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_STORE_TRANSACTION_INQUIRY_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

    }

    /**
     * Show sales transaction history list page. Condition： Sort link display. Result confirmation：
     * The expected sales transaction history sorted list page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("StoreTransactionInquiryTest.xml")
    public void testStoreTransactionInquiry02() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_STORE_TRANSACTION_INQUIRY_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Brand pull down click.
        element.brandPullDownDiv.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        // Country pull down click.
        element.countryPullDownDiv.click();

        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        element = PageFactory.initElements(webDriver, StoreTransactionInquiryElements.class);

        assertDetailDisplay(element);


    }

    /**
     * Check the table body display screen. Check the display of the store transaction inquiry
     * screen.
     */
    private void assertDetailDisplay(final StoreTransactionInquiryElements pageElements)
            throws Exception {
        List<WebElement> headerDetail = element.headerList;
        assertEquals("20180615", headerDetail.get(0).getText());
        assertEquals("2018-05-30T11:01:02+09:00", headerDetail.get(1).getText());
        assertEquals("112326", headerDetail.get(2).getText());
        assertEquals("4", headerDetail.get(3).getText());
        assertEquals("0774", headerDetail.get(4).getText());
        assertEquals("test", headerDetail.get(5).getText());
        assertEquals("RETURN", headerDetail.get(6).getText());
        assertEquals("JPY", headerDetail.get(7).getText());
        assertEquals("-1000.00", headerDetail.get(8).getText());
        assertEquals("Cha", headerDetail.get(9).getText());
        assertEquals("-1000.00", headerDetail.get(10).getText());

        List<WebElement> itemDetail = element.itemList;
        assertEquals("2", itemDetail.get(0).getText());
        assertEquals("Noncode2", itemDetail.get(1).getText());
        assertEquals("name2", itemDetail.get(2).getText());
        assertEquals("5000.0000", itemDetail.get(3).getText());
        assertEquals("JPY", itemDetail.get(4).getText());
        assertEquals("10000000.00", itemDetail.get(5).getText());
        List<WebElement> discountDetail = element.discountList;
        assertEquals("", discountDetail.get(0).getText());
        assertEquals("2", discountDetail.get(1).getText());
        assertEquals("JPY", discountDetail.get(2).getText());
        assertEquals("3000.00", discountDetail.get(3).getText());
        List<WebElement> taxDetail = element.taxList;
        assertEquals("PST", taxDetail.get(0).getText());
        assertEquals("CAD", taxDetail.get(1).getText());
        assertEquals("2000.00", taxDetail.get(2).getText());
        List<WebElement> paymentDetail = element.tenderList;
        assertEquals("CCARD", paymentDetail.get(0).getText());
        assertEquals("CAD", paymentDetail.get(1).getText());
        assertEquals("3000.00", paymentDetail.get(2).getText());
    }

    /**
     * Check the initial display screen. Check the initial display of the sales transaction history
     * list screen.
     */
    private void assertInitialDisplay(final StoreTransactionInquiryElements pageElements)
            throws Exception {

        assertTrue(element.systemBrandCodeInput.isEnabled());
        assertTrue(element.systemCountryCodeInput.isEnabled());
        assertTrue(element.storeCodeInput.isEnabled());
        assertTrue(element.businessDateInput.isEnabled());
        assertTrue(element.cashRegisterNoInput.isEnabled());
        assertTrue(element.dataCreationDateFromInput.isEnabled());
        assertTrue(element.dataCreationDateToInput.isEnabled());
        assertTrue(element.dataCreationTimeFromInput.isEnabled());
        assertTrue(element.dataCreationTimeToInput.isEnabled());
        assertTrue(element.salesTransactionTypeInput.isEnabled());
        assertTrue(element.casherCodeInput.isEnabled());
        assertTrue(element.membershipIdInput.isEnabled());
        assertTrue(element.receiptNoFromInput.isEnabled());
        assertTrue(element.receiptNoToInput.isEnabled());
        assertTrue(element.paymentTenderGroupInput.isEnabled());
        assertTrue(element.paymentTenderIdInput.isEnabled());
        assertTrue(element.paymentAmountFromInput.isEnabled());
        assertTrue(element.paymentAmountToInput.isEnabled());
        assertTrue(element.depositAmountFromInput.isEnabled());
        assertTrue(element.depositAmountToInput.isEnabled());
        assertTrue(element.changeAmountFromInput.isEnabled());
        assertTrue(element.changeAmountToInput.isEnabled());
        assertTrue(element.taxationTypeInput.isEnabled());
        assertTrue(element.nonMerchandiseItemInput.isEnabled());
        assertTrue(element.itemCodeInput.isEnabled());
        assertTrue(element.discountTypeInput.isEnabled());
        // Brand pull down click.
        element.brandPullDownDiv.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        // Confirm result.
        assertEquals("UQ", element.categoryOption1.getText());
        // Country pull down click.
        element.countryPullDownDiv.click();
        // Confirm result.
        assertEquals("Japan", element.categoryOption1.getText());

        // Sales transaction type pull down click.
        element.salesTransactionTypePullDownDiv.click();
        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("RETURN", element.categoryOption2.getText());
        assertEquals("SALE", element.categoryOption3.getText());
        assertEquals("PVOID", element.categoryOption4.getText());

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        // Taxation type pull down click.
        element.taxationTypePullDownDiv.click();
        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("Y", element.categoryOption2.getText());
        assertEquals("N", element.categoryOption3.getText());
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Non merchandise item pull down click.
        element.paymentTenderGroupPullDownDiv.click();
        // Confirm result.
        assertEquals("IMSCOU", element.categoryOption1.getText());

        // Discount type pull down click.
        element.discountTypePullDownDiv.click();

        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("Multi Unit", element.categoryOption2.getText());
        assertEquals("Single Promotion", element.categoryOption3.getText());

    }

    /**
     * URL verification. Confirm whether you can transition to target URL.
     *
     * @param url URL.
     */
    private void assertUrl(final String url) {
        assertEquals(url, webDriver.getCurrentUrl());
    }
}

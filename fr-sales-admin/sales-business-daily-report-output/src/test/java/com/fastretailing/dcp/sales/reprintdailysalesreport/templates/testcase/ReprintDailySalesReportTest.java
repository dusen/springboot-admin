/**
 * @(#)ReprintDailySalesReportTest.java
 *
 *                                      Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.templates.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
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
import com.fastretailing.dcp.sales.reprintdailysalesreport.templates.constants.Constants;
import com.fastretailing.dcp.sales.reprintdailysalesreport.templates.elements.ReprintDailySalesReportElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales reprint daily sales report screen.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)

@Sql(scripts = "/junit_create_table_sales_6.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class ReprintDailySalesReportTest {

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected ReprintDailySalesReportElements element;

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
     * Show sales reprint daily sales report page. Condition： Search button display. Result
     * confirmation： The expected sales reprint daily sales report page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReprintDailySalesReportTest.xml")
    public void testSalesTransactionHistoryList01() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // Confirm whether the reprint daily sales report is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_REPRINT_DAILY_SALES_REPORT_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Handle of the searching function.
        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

    }

    /**
     * Show reprint daily sales report page. Condition： Print link display. Result confirmation： The
     * expected sales reprint daily sales report printed page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReprintDailySalesReportTest.xml")
    public void testSalesTransactionHistoryList02() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // Confirm whether the reprint daily sales report is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_REPRINT_DAILY_SALES_REPORT_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Handle of the searching function.
        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

    }

    /**
     * Show sales reprint daily sales report page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("ReprintDailySalesReportTest.xml")
    public void testSalesTransactionHistoryList03() throws Exception {

        assertInitialDisplay();

        // Confirm whether the reprint daily sales report is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_REPRINT_DAILY_SALES_REPORT_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Handle of the clearing function.
        element.clearButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertEquals("", element.businessDate.getText());
    }

    /**
     * Check the initial display screen. Check the initial display of the sales reprint daily sales
     * report screen.
     */
    private void assertInitialDisplay() throws Exception {

        assertTrue(element.displayStoreCode.isEnabled());
        assertTrue(element.clearButton.isEnabled());
        assertTrue(element.searchButton.isEnabled());

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
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

/**
 * @(#)DeclareIrregularSettlementsTest.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.templates.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
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
import com.fastretailing.dcp.sales.declareirregularsettlements.templates.elements.DeclareIrregularSettlementsElements;
import com.fastretailing.dcp.sales.declareirregularsettlements.templates.constants.Constants;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of declare irregular settlements list screen.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class DeclareIrregularSettlementsTest {

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of web driver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected DeclareIrregularSettlementsElements element;

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
     * Show declare irregular settlements page. Condition： Search button display. Result
     * confirmation： The expected declare irregular settlements page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("DeclareIrregularSettlementsDataTest.xml")
    public void testSalesTransactionHistoryList01() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the declare irregular settlements is the item list screen.
        assertUrl(Constants.DECLARE_IRREGULAR_SETTLEMENTS_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Add cash register number.
        element.cashRegisterNo.sendKeys("1");

        // Add settlement date calendar.
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.settlementDate.click();
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.selectDate.click();
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertTabelBodyDisplay(element);
    }

    /**
     * Show declare irregular settlements page. Condition： Confirm link display. Result
     * confirmation： The expected declare irregular settlements confirmed list page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("DeclareIrregularSettlementsDataTest.xml")
    public void testSalesTransactionHistoryList02() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the declare irregular settlements is the item list screen.
        assertUrl(Constants.DECLARE_IRREGULAR_SETTLEMENTS_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Add settlement date calendar.
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.settlementDate.click();
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.selectDate.click();
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.searchButton.click();

        // Add cash register number.
        element.cashRegisterNo.sendKeys("1");

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Search button click.
        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Search button click.
        element.confirmButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
    }

    /**
     * Show declare irregular settlements page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("DeclareIrregularSettlementsDataTest.xml")
    public void testSalesTransactionHistoryList03() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.DECLARE_IRREGULAR_SETTLEMENTS_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Add settlement date calendar.
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.settlementDate.click();
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);
        element.selectDate.click();
        Thread.sleep(Constants.THREAD_SLEEP_TIME / 4);

        // Add cash register number.
        element.cashRegisterNo.sendKeys("1");
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        // Search button click.
        element.searchButton.click();
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        // Search button click.
        element.clearButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertEquals("", element.settlementDate.getText());
        assertEquals("", element.cashRegisterNo.getText());
    }

    /**
     * Check the table body display screen. Check the display of the declare irregular settlements
     * screen.
     */
    private void assertTabelBodyDisplay(final DeclareIrregularSettlementsElements pageElements)
            throws Exception {

        assertEquals(9, element.tenderPaymentInformationList.findElements(By.tagName("tr")).size());
        assertEquals("CASH",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[1]/td[1]"))
                        .getText());
        assertEquals("",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[1]/td[2]/label"))
                        .getText());
        assertEquals("100",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[1]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[1]/td[4]/label"))
                        .getText());

        assertEquals("CCARD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[2]/td[1]"))
                        .getText());
        assertEquals("Amex",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[2]/td[2]/label"))
                        .getText());
        assertEquals("800",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[2]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[2]/td[4]/label"))
                        .getText());

        assertEquals("CCARD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[3]/td[1]"))
                        .getText());
        assertEquals("CUP",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[3]/td[2]/label"))
                        .getText());
        assertEquals("910",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[3]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[3]/td[4]/label"))
                        .getText());

        assertEquals("CCARD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[4]/td[1]"))
                        .getText());
        assertEquals("CC Unknown",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[4]/td[2]/label"))
                        .getText());
        assertEquals("900",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[4]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[4]/td[4]/label"))
                        .getText());

        assertEquals("COUPN",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[5]/td[1]"))
                        .getText());
        assertEquals("$10_Coupon",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[5]/td[2]/label"))
                        .getText());
        assertEquals("200",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[5]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[5]/td[4]/label"))
                        .getText());

        assertEquals("COUPN",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[6]/td[1]"))
                        .getText());
        assertEquals("$20_Coupon",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[6]/td[2]/label"))
                        .getText());
        assertEquals("300",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[6]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[6]/td[4]/label"))
                        .getText());

        assertEquals("COUPN",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[7]/td[1]"))
                        .getText());
        assertEquals("$30_Coupon",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[7]/td[2]/label"))
                        .getText());
        assertEquals("400",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[7]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[7]/td[4]/label"))
                        .getText());

        assertEquals("EMDSC",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[8]/td[1]"))
                        .getText());
        assertEquals("Employee discount",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[8]/td[2]/label"))
                        .getText());
        assertEquals("993",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[8]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[8]/td[4]/label"))
                        .getText());

        assertEquals("GCARD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[9]/td[1]"))
                        .getText());
        assertEquals("Gift Card",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[9]/td[2]/label"))
                        .getText());
        assertEquals("",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[9]/td[3]/label"))
                        .getText());
        assertEquals("CAD",
                element.tenderPaymentInformationList.findElement(By.xpath("//tr[9]/td[4]/label"))
                        .getText());
    }

    /**
     * Check the initial display screen. Check the initial display of the declare irregular
     * settlements screen.
     */
    private void assertInitialDisplay(final DeclareIrregularSettlementsElements pageElements)
            throws Exception {

        assertTrue(element.systemBrandCode.isEnabled());
        assertTrue(element.systemCountryCode.isEnabled());
        assertTrue(element.cashRegisterNo.isDisplayed());
        assertTrue(element.displayStoreCode.isEnabled());
        assertTrue(element.changeFundInteger.isEnabled());
        assertTrue(element.closingBalanceInteger.isEnabled());
        assertTrue(element.requiredBalance.isEnabled());
        assertTrue(element.endReceiptNo.isEnabled());
        assertTrue(element.clearButton.isDisplayed());
        assertTrue(element.searchButton.isEnabled());
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

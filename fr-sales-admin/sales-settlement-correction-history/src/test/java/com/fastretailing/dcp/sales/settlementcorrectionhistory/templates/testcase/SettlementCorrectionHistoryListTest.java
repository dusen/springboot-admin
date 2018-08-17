/**
 * @(#)SettlementCorrectionHistoryListTest.java
 *
 *                                              Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.settlementcorrectionhistory.templates.testcase;

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
import com.fastretailing.dcp.sales.settlementcorrectionhistory.templates.constants.Constants;
import com.fastretailing.dcp.sales.settlementcorrectionhistory.templates.elements.SettlementCorrectionHistoryListElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of settlement correction history list screen.
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
public abstract class SettlementCorrectionHistoryListTest {

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected SettlementCorrectionHistoryListElements element;

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
     * Show settlement correction history list page. Condition： Search button display. Result
     * confirmation： The expected settlement correction history list page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SettlementCorrectionHistoryListTest.xml")
    public void testSettlementCorrectionHistorySearch() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SETTLEMENT_CORRECTION_HISTORY_LIST_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Brand pull down click.
        element.brandPullDownDiv.click();

        element.categoryOption2.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Country pull down click.
        element.countryPullDownDiv.click();

        // Confirm result.
        element.categoryOption18.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.viewStoreCodeInput.sendKeys("9720");

        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertTabelBodyDisplay(element);

    }

    /**
     * Show settlement correction history list page. Condition： Sort link display. Result
     * confirmation： The expected settlement correction history sorted list page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SettlementCorrectionHistoryListTest.xml")
    public void testSettlementCorrectionHistorySort() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SETTLEMENT_CORRECTION_HISTORY_LIST_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Brand pull down click.
        element.brandPullDownDiv.click();

        element.categoryOption2.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Country pull down click.
        element.countryPullDownDiv.click();

        // Confirm result.
        element.categoryOption18.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.viewStoreCodeInput.sendKeys("9720");

        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertTabelBodyDisplay(element);

        element.sortKey.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertSortedTabelBodyDisplay(element);

    }

    /**
     * Show settlement correction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SettlementCorrectionHistoryListTest.xml")
    public void testSettlementCorrectionHistoryClear() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SETTLEMENT_CORRECTION_HISTORY_LIST_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Brand pull down click.
        element.brandPullDownDiv.click();

        element.categoryOption2.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Country pull downclick.
        element.countryPullDownDiv.click();

        // Confirm result.
        element.categoryOption18.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.viewStoreCodeInput.sendKeys("9720");
        element.cashRegisterNoInput.sendKeys("111");
        element.payoffDateFromInput.sendKeys("2018/01/08 00:00:00");
        element.payoffDateToInput.sendKeys("2018/02/28 00:00:00");
        element.correctorCodeInput.sendKeys("BATCH");
        element.correctionDateFromInput.sendKeys("2018/05/08 00:00:00");
        element.correctionDateToInput.sendKeys("2018/10/08 00:00:00");

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.clearButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertEquals("", element.viewStoreCodeInput.getText());
        assertEquals("", element.cashRegisterNoInput.getText());
        assertEquals("", element.payoffDateFromInput.getText());
        assertEquals("", element.payoffDateToInput.getText());
        assertEquals("", element.correctorCodeInput.getText());
        assertEquals("", element.correctionDateFromInput.getText());
        assertEquals("", element.correctionDateToInput.getText());

    }

    /*
     * Check the initial display screen. Check the initial display of the settlement correction
     * history list screen.
     */
    private void assertInitialDisplay(final SettlementCorrectionHistoryListElements pageElements)
            throws Exception {

        assertTrue(element.systemBrandCodeInput.isEnabled());
        assertTrue(element.systemCountryCodeInput.isEnabled());
        assertTrue(element.viewStoreCodeInput.isEnabled());
        assertTrue(element.cashRegisterNoInput.isEnabled());
        assertTrue(element.payoffDateFromInput.isEnabled());
        assertTrue(element.payoffDateToInput.isEnabled());
        assertTrue(element.correctorCodeInput.isEnabled());
        assertTrue(element.correctionDateFromInput.isEnabled());
        assertTrue(element.correctionDateToInput.isEnabled());
        assertTrue(element.clearButton.isEnabled());
        assertTrue(element.searchButton.isEnabled());

        // Brand pull down click.
        element.brandPullDownDiv.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("UQ", element.categoryOption2.getText());
        assertEquals("GU", element.categoryOption3.getText());

        // Country pull down click.
        element.countryPullDownDiv.click();

        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("Japan", element.categoryOption2.getText());
        assertEquals("UK", element.categoryOption3.getText());
        assertEquals("China", element.categoryOption4.getText());
        assertEquals("U.S.A.", element.categoryOption5.getText());
        assertEquals("Korea", element.categoryOption6.getText());
        assertEquals("Hong Kong", element.categoryOption7.getText());
        assertEquals("France", element.categoryOption8.getText());
        assertEquals("Singapore", element.categoryOption9.getText());
        assertEquals("Russia", element.categoryOption10.getText());
        assertEquals("Taiwan", element.categoryOption11.getText());
        assertEquals("Thailand", element.categoryOption12.getText());
        assertEquals("Philippines", element.categoryOption13.getText());
        assertEquals("Indonesia", element.categoryOption14.getText());
        assertEquals("Australia", element.categoryOption15.getText());
        assertEquals("Germany", element.categoryOption16.getText());
        assertEquals("Spain", element.categoryOption17.getText());
        assertEquals("Canada", element.categoryOption18.getText());
        assertEquals("Macau", element.categoryOption19.getText());
        assertEquals("Malaysia", element.categoryOption20.getText());
        assertEquals("Belgium", element.categoryOption21.getText());
    }

    /**
     * Check the table body display screen. Check the display of the settlement correction history
     * list screen.
     */
    private void assertTabelBodyDisplay(final SettlementCorrectionHistoryListElements pageElements)
            throws Exception {

        assertEquals(2,
                element.settlementCorrectionHistoryList.findElements(By.tagName("tr")).size());

        assertEquals("UQ",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[1]/label"))
                        .getText());
        assertEquals("Canada",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[2]/label"))
                        .getText());
        assertEquals("9720",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[3]/label"))
                        .getText());
        assertEquals("SSC",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[4]/label"))
                        .getText());
        assertEquals("2018/05/23",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[5]/label"))
                        .getText());
        assertEquals("111",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[6]/label"))
                        .getText());
        assertEquals("0005",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[7]/label"))
                        .getText());
        assertEquals("0001_name",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[8]/label"))
                        .getText());
        assertEquals("0001AAA",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[9]/label"))
                        .getText());
        assertEquals("0001AAA_name",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[10]/label"))
                        .getText());
        assertEquals("100010001000.3500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[11]/label"))
                        .getText());
        assertEquals("100010001000.9500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[12]/label"))
                        .getText());
        assertEquals("200020002000.5900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[13]/label"))
                        .getText());
        assertEquals("200020002000.5900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[14]/label"))
                        .getText());
        assertEquals("BATCH",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[15]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[16]/label"))
                        .getText());

        assertEquals("UQ",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[1]/label"))
                        .getText());
        assertEquals("Canada",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[2]/label"))
                        .getText());
        assertEquals("9720",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[3]/label"))
                        .getText());
        assertEquals("SSC",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[4]/label"))
                        .getText());
        assertEquals("2018/05/23",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[5]/label"))
                        .getText());
        assertEquals("222",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[6]/label"))
                        .getText());
        assertEquals("0006",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[7]/label"))
                        .getText());
        assertEquals("0002_name",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[8]/label"))
                        .getText());
        assertEquals("0002AAA",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[9]/label"))
                        .getText());
        assertEquals("0002AAA_name",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[10]/label"))
                        .getText());
        assertEquals("100010001000.3500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[11]/label"))
                        .getText());
        assertEquals("100010001000.3500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[12]/label"))
                        .getText());
        assertEquals("200020002000.5900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[13]/label"))
                        .getText());
        assertEquals("200020002000.3900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[14]/label"))
                        .getText());
        assertEquals("BATCH",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[15]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[16]/label"))
                        .getText());
    }

    /**
     * Check the table body display screen. Check the display of the sorted settlement correction
     * history list screen.
     */
    private void assertSortedTabelBodyDisplay(
            final SettlementCorrectionHistoryListElements pageElements) throws Exception {

        assertEquals(2,
                element.settlementCorrectionHistoryList.findElements(By.tagName("tr")).size());

        assertEquals("UQ",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[1]/label"))
                        .getText());
        assertEquals("Canada",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[2]/label"))
                        .getText());
        assertEquals("9720",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[3]/label"))
                        .getText());
        assertEquals("SSC",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[4]/label"))
                        .getText());
        assertEquals("2018/05/23",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[5]/label"))
                        .getText());
        assertEquals("222",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[6]/label"))
                        .getText());
        assertEquals("0006",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[7]/label"))
                        .getText());
        assertEquals("0002_name",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[8]/label"))
                        .getText());
        assertEquals("0002AAA",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[1]/td[9]/label"))
                        .getText());
        assertEquals("0002AAA_name",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[10]/label"))
                        .getText());
        assertEquals("100010001000.3500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[11]/label"))
                        .getText());
        assertEquals("100010001000.3500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[12]/label"))
                        .getText());
        assertEquals("200020002000.5900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[13]/label"))
                        .getText());
        assertEquals("200020002000.3900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[14]/label"))
                        .getText());
        assertEquals("BATCH",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[15]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[1]/td[16]/label"))
                        .getText());

        assertEquals("UQ",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[1]/label"))
                        .getText());
        assertEquals("Canada",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[2]/label"))
                        .getText());
        assertEquals("9720",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[3]/label"))
                        .getText());
        assertEquals("SSC",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[4]/label"))
                        .getText());
        assertEquals("2018/05/23",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[5]/label"))
                        .getText());
        assertEquals("111",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[6]/label"))
                        .getText());
        assertEquals("0005",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[7]/label"))
                        .getText());
        assertEquals("0001_name",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[8]/label"))
                        .getText());
        assertEquals("0001AAA",
                element.settlementCorrectionHistoryList.findElement(By.xpath("//tr[2]/td[9]/label"))
                        .getText());
        assertEquals("0001AAA_name",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[10]/label"))
                        .getText());
        assertEquals("100010001000.3500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[11]/label"))
                        .getText());
        assertEquals("100010001000.9500",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[12]/label"))
                        .getText());
        assertEquals("200020002000.5900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[13]/label"))
                        .getText());
        assertEquals("200020002000.5900",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[14]/label"))
                        .getText());
        assertEquals("BATCH",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[15]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.settlementCorrectionHistoryList
                        .findElement(By.xpath("//tr[2]/td[16]/label"))
                        .getText());
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

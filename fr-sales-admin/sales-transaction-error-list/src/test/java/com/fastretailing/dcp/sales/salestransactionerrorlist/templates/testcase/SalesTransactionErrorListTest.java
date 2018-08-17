/**
 * @(#)SalesTransactionErrorListTest.java
 *
 *                                        Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.templates.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

import com.fastretailing.dcp.sales.salestransactionerrorlist.templates.constants.Constants;
import com.fastretailing.dcp.sales.salestransactionerrorlist.templates.elements.SalesTransactionErrorListElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales transaction error list screen.
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
public abstract class SalesTransactionErrorListTest {

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of web driver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected SalesTransactionErrorListElements element;

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
     * Show sales transaction error list page. Condition： Search button display. Result
     * confirmation： The expected sales transaction error list page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionErrorListTest.xml")
    public void testSalesTransactionErrorList01() throws Exception {

        // Confirm initial display.
        assertInitialDisplay(element);
    }

    /**
     * Check the initial display screen. Check the initial display of the sales transaction error
     * list screen.
     */
    private void assertInitialDisplay(final SalesTransactionErrorListElements pageElements)
            throws Exception {
        assertTrue(element.systemBrandCode.isEnabled());
        assertTrue(element.systemCountryCode.isEnabled());
        assertTrue(element.storeCodeInput.isEnabled());
        assertTrue(element.cashRegisterNoInput.isEnabled());
        assertTrue(element.receiptNoInput.isEnabled());
        assertTrue(element.salesTransactionTypeInput.isEnabled());
        assertTrue(element.dataCreationDateFromInput.isEnabled());
        assertTrue(element.dataCreationDateToInput.isEnabled());
        assertTrue(element.businessDateFromInput.isEnabled());
        assertTrue(element.businessDateToInput.isEnabled());
        assertTrue(element.errorContentsInput.isEnabled());
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
        element.categoryOption2.click();
        // Country pull down click.
        element.countryPullDownDiv.click();
        
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        element.storeCodeInput.sendKeys("4");

        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("Japan", element.categoryOption2.getText());
        assertEquals("United Kingdom", element.categoryOption3.getText());
        assertEquals("China", element.categoryOption4.getText());
        assertEquals("United States", element.categoryOption5.getText());
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
        element.categoryOption18.click();
        
        // Sales transaction type pull down click.
        element.salesTransactionTypePullDownDiv.click();

        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("SALE", element.categoryOption2.getText());
        assertEquals("RETURN", element.categoryOption3.getText());
        assertEquals("PVOID", element.categoryOption4.getText());

        // Sales transaction type pull down click.
        element.errorContentsPullDownDiv.click();

        // Confirm result.
        assertEquals("Clear", element.categoryOption1.getText());
        assertEquals("Abnormal value in required field", element.categoryOption2.getText());
        assertEquals("Sales transaction without Item", element.categoryOption3.getText());
        assertEquals("Business error", element.categoryOption4.getText());
        assertEquals("Abnormal business date", element.categoryOption5.getText());
        assertEquals("Sales transaction before store opening", element.categoryOption6.getText());
        assertEquals("Sales transaction after store closing", element.categoryOption7.getText());
        assertEquals("Exceed business date closing time", element.categoryOption8.getText());
        assertEquals("Closed business date", element.categoryOption9.getText());
        assertEquals("Invalid tender", element.categoryOption10.getText());
        assertEquals("Balance inconsistency", element.categoryOption11.getText());
        assertEquals("Unique constraint error", element.categoryOption12.getText());
        assertEquals("Sales payoff inconsistency", element.categoryOption13.getText());
        
        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        
        element.noLink.click();
        
        Thread.sleep(Constants.THREAD_SLEEP_TIME);
        testSalesTransactionCorrectionHistoryDetai01();
        testSalesTransactionCorrectionHistoryDetai02();
        testSalesTransactionCorrectionHistoryDetai03();
        testSalesTransactionCorrectionHistoryDetai04();
        testSalesTransactionCorrectionHistoryDetai05();
    }
    
    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    public void testSalesTransactionCorrectionHistoryDetai01() throws Exception {
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.SALES_TRANSACTION_CORRECTION_URL);
        assertEquals(1,
                element.salesTransactionCorrectionList.findElements(By.tagName("tr")).size());
        assertEquals("✓",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[1]"))
                        .getText());
        assertEquals("114266",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[2]"))
                        .getText());
        assertEquals("199",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[3]"))
                        .getText());
        assertEquals("0002",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[4]"))
                        .getText());
        assertEquals("2018/07/02",
                element.salesTransactionCorrectionList
                        .findElement(By.xpath("//tr[1]/td[5]/div/span/input"))
                        .getAttribute("value"));
        assertEquals("2018/05/31 12:15:41",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[6]/input"))
                        .getAttribute("value"));
    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    public void testSalesTransactionCorrectionHistoryDetai02() throws Exception {
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.SALES_TRANSACTION_CORRECTION_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeader.click();
        assertEquals("1",
                element.salesTransactionHeaderTabListOne
                        .findElement(By.xpath("//div[@class='width120 one']/label"))
                        .getText());
        assertEquals("SALE",
                element.salesTransactionHeaderTabListOne
                        .findElement(By.xpath("//div[@class='width120 two']/label"))
                        .getText());
        assertEquals("0",
                element.salesTransactionHeaderTabListOne
                        .findElement(By.xpath("//div[@class='width120 three']/label"))
                        .getText());
        assertEquals("✓", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[2]"))
                .getText());
        assertEquals("2000091597562", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[4]"))
                .getText());
        assertEquals("Packaged dry color c", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[5]/input"))
                .getAttribute("value"));
        assertEquals("", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[7]"))
                .getText());
        assertEquals("15.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[9]/input"))
                .getAttribute("value"));
        assertEquals("15.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[10]/input"))
                .getAttribute("value"));
        assertEquals("1", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[12]/input"))
                .getAttribute("value"));
        assertEquals("15.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[13]/input"))
                .getAttribute("value"));
        assertEquals("15.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[14]/input"))
                .getAttribute("value"));
        assertEquals("2018/07/02", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[17]/div/span/input"))
                .getAttribute("value"));
        assertEquals("2018/05/31 12:15:41", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[18]/input"))
                .getAttribute("value"));
        assertEquals("Discount", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[19]"))
                .getText());
        assertEquals("Tax", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target_0 detail_index_0']/td[20]"))
                .getText());
    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    public void testSalesTransactionCorrectionHistoryDetai03() throws Exception {
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.SALES_TRANSACTION_CORRECTION_URL);


        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeaderTax.click();

        assertEquals(1, element.taxList.findElements(By.tagName("tr")).size());

        assertEquals("✓",
                element.itemDetailList.findElement(By.xpath("//tr[@id='taxListTr']/td[2]"))
                        .getText());
        assertEquals("1.6000",
                element.itemDetailList.findElement(By.xpath("//tr[@id='taxListTr']/td[7]/input"))
                        .getAttribute("value"));
        assertEquals("1.0800",
                element.itemDetailList.findElement(By.xpath("//tr[@id='taxListTr']/td[8]"))
                        .getText());
    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    public void testSalesTransactionCorrectionHistoryDetai04() throws Exception {
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.SALES_TRANSACTION_CORRECTION_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeaderPayment.click();

        assertEquals(1, element.paymentList.findElements(By.tagName("tr")).size());

        assertEquals("✓",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[2]"))
                        .getText());
        assertEquals("21.6000",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[8]/input"))
                        .getAttribute("value"));
    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    public void testSalesTransactionCorrectionHistoryDetai05() throws Exception {
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.SALES_TRANSACTION_CORRECTION_URL);

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeaderTotal.click();

        assertEquals(1, element.totalList.findElements(By.tagName("tr")).size());

        assertEquals("20.0000",
                element.totalList.findElement(By.xpath("//tr[@id='totalListTr']/td[4]/input"))
                        .getAttribute("value"));
        assertEquals("20.0000",
                element.totalList.findElement(By.xpath("//tr[@id='totalListTr']/td[5]/input"))
                        .getAttribute("value"));
    }
    
    /**
     * URL verification. Confirm whether you can transition to target URL.
     * 
     * @param url URL.
     */
    private void assertUrl(final String url) {
        webDriverWait.until(ExpectedConditions.urlMatches(url));
        assertEquals(url, webDriver.getCurrentUrl());
    }

}

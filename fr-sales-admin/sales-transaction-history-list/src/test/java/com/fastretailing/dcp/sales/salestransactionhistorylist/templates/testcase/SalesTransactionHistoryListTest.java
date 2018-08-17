/**
 * @(#)SalesTransactionHistoryListTest.java
 *
 *                                          Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionhistorylist.templates.testcase;

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
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.salestransactionhistorylist.templates.constants.Constants;
import com.fastretailing.dcp.sales.salestransactionhistorylist.templates.elements.SalesTransactionHistoryListElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales transaction history list screen.
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
public abstract class SalesTransactionHistoryListTest {

    /** Driver for performing browser operation. */
    protected WebDriver webDriver;

    /** Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /** Screen element (tag) information. */
    protected SalesTransactionHistoryListElements element;

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
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList01() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertTabelBodyDisplay();

    }

    /**
     * Show sales transaction history list page. Condition： Sort link display. Result confirmation：
     * The expected sales transaction history sorted list page is displayed.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList02() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertTabelBodyDisplay();

    }

    /**
     * Show sales transaction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList03() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.cashRegisterNoInput.sendKeys("003");

        element.salesTransactionTypePullDownDiv.click();

        // Confirm result.
        element.categoryOption3.click();

        element.dataCreationDateFromInput.sendKeys("004");
        element.dataCreationDateToInput.sendKeys("005");
        element.businessDateFromInput.sendKeys("006");
        element.businessDateToInput.sendKeys("007");
        element.correctorCodeInput.sendKeys("008");
        element.correctionDateFromInput.sendKeys("009");
        element.correctionDateToInput.sendKeys("010");
        element.errorContentsPullDownDiv.click();
        element.categoryOption3.click();
        element.correctorCodeInput.sendKeys("011");

        element.clearButton.click();

        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        assertEquals("", element.dataCreationDateFromInput.getText());
        assertEquals("", element.dataCreationDateToInput.getText());
        assertEquals("", element.businessDateFromInput.getText());
        assertEquals("", element.businessDateToInput.getText());
        assertEquals("", element.correctorCodeInput.getText());
        assertEquals("", element.correctionDateFromInput.getText());
        assertEquals("", element.correctionDateToInput.getText());
        assertEquals("", element.correctorCodeInput.getText());

    }

    /**
     * Check the table body display screen. Check the display of the sales transaction history list
     * screen.
     */
    private void assertTabelBodyDisplay() throws Exception {

        assertEquals(2, element.salesTransactionHistoryList.findElements(By.tagName("tr")).size());
        assertEquals("1", element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[1]"))
                .getText());
        assertEquals("UQ",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[2]/label"))
                        .getText());
        assertEquals("Canada",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[3]/label"))
                        .getText());
        assertEquals("002",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[4]/label"))
                        .getText());
        assertEquals("東京二号店",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[5]/label"))
                        .getText());
        assertEquals("1",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[6]/label"))
                        .getText());
        assertEquals("1234",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[7]/label"))
                        .getText());
        assertEquals("1",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[8]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[9]/label"))
                        .getText());
        assertEquals("20181010",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[10]/label"))
                        .getText());
        assertEquals("Business error",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[11]/label"))
                        .getText());
        assertEquals("20000001",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[12]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[13]/label"))
                        .getText());
        assertEquals("ALL",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[14]/label"))
                        .getText());
        assertEquals("Insert",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[1]/td[15]/label"))
                        .getText());

        assertEquals("2", element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[1]"))
                .getText());
        assertEquals("UQ",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[2]/label"))
                        .getText());
        assertEquals("Canada",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[3]/label"))
                        .getText());
        assertEquals("002",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[4]/label"))
                        .getText());
        assertEquals("東京二号店",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[5]/label"))
                        .getText());
        assertEquals("2",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[6]/label"))
                        .getText());
        assertEquals("1235",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[7]/label"))
                        .getText());
        assertEquals("2",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[8]/label"))
                        .getText());
        assertEquals("2018/05/23 05:05:12",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[9]/label"))
                        .getText());
        assertEquals("20181011",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[10]/label"))
                        .getText());
        assertEquals("Business error",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[11]/label"))
                        .getText());
        assertEquals("20000002",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[12]/label"))
                        .getText());
        assertEquals("2018/06/23 05:05:12",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[13]/label"))
                        .getText());
        assertEquals("only on IMS Linkage",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[14]/label"))
                        .getText());
        assertEquals("Correction",
                element.salesTransactionHistoryList.findElement(By.xpath("//tr[2]/td[15]/label"))
                        .getText());

    }

    //
    /**
     * Check the initial display screen. Check the initial display of the sales transaction history
     * list screen.
     */
    private void assertInitialDisplay() throws Exception {

        assertTrue(element.systemBrandCodeInput.isEnabled());
        assertTrue(element.systemCountryCodeInput.isEnabled());
        assertTrue(element.storeCodeInput.isEnabled());
        assertTrue(element.cashRegisterNoInput.isEnabled());
        assertTrue(element.receiptNoInput.isEnabled());
        assertTrue(element.salesTransactionTypeInput.isEnabled());
        assertTrue(element.dataCreationDateFromInput.isEnabled());
        assertTrue(element.dataCreationDateToInput.isEnabled());
        assertTrue(element.businessDateFromInput.isEnabled());
        assertTrue(element.businessDateToInput.isEnabled());
        assertTrue(element.correctorCodeInput.isEnabled());
        assertTrue(element.correctionDateFromInput.isEnabled());
        assertTrue(element.correctionDateToInput.isEnabled());
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

    }

    /**
     * Show sales transaction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList04() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.forDetailLink.click();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_CORRECTION_HISTORY_DETAIL_URL);

        assertEquals(1,
                element.salesTransactionCorrectionList.findElements(By.tagName("tr")).size());
        assertEquals("Before",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[1]"))
                        .getText());
        assertEquals("1",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[2]"))
                        .getText());
        assertEquals("199",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[3]"))
                        .getText());
        assertEquals("0002",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[4]"))
                        .getText());
        assertEquals("2013/07/08",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[5]"))
                        .getText());
        assertEquals("2018/10/10 10:10:00",
                element.salesTransactionCorrectionList.findElement(By.xpath("//tr[1]/td[6]"))
                        .getText());
    }

    //
    /**
     * Show sales transaction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList05() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.forDetailLink.click();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_CORRECTION_HISTORY_DETAIL_URL);

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeader.click();
        assertEquals("1",
                element.salesTransactionHeaderTabListOne
                        .findElement(By.xpath("//div[@class='width120 one']/label"))
                        .getText());
        assertEquals("2",
                element.salesTransactionHeaderTabListOne
                        .findElement(By.xpath("//div[@class='width120 two']/label"))
                        .getText());
        assertEquals("0",
                element.salesTransactionHeaderTabListOne
                        .findElement(By.xpath("//div[@class='width120 three']/label"))
                        .getText());
        assertEquals("Before", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[1]"))
                .getText());
        assertEquals("2000085147315", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[3]"))
                .getText());
        assertEquals("Sweat Hoodie", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[4]"))
                .getText());
        assertEquals("", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[5]"))
                .getText());
        assertEquals("34", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[6]"))
                .getText());
        assertEquals("5.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[8]"))
                .getText());
        assertEquals("5.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[9]"))
                .getText());
        assertEquals("1", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[11]"))
                .getText());
        assertEquals("5.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[12]"))
                .getText());
        assertEquals("5.0000", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[13]"))
                .getText());
        assertEquals("2018/07/02", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[16]"))
                .getText());
        assertEquals("2018/05/31 12:15:41", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[17]"))
                .getText());
        assertEquals("Discount", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[18]"))
                .getText());
        assertEquals("Tax", element.itemDetailList.findElement(By.xpath(
                "//tr[@class='v-align-middle item_detail_del_target']/td[19]"))
                .getText());
    }

    //
    /**
     * Show sales transaction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList06() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.forDetailLink.click();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_CORRECTION_HISTORY_DETAIL_URL);

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeader.click();

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeaderTax.click();

        assertEquals(1, element.taxList.findElements(By.tagName("tr")).size());

        assertEquals("Before",
                element.itemDetailList.findElement(By.xpath("//tr[@id='taxListTr']/td[1]"))
                        .getText());
        assertEquals("1.6000",
                element.itemDetailList.findElement(By.xpath("//tr[@id='taxListTr']/td[6]"))
                        .getText());
        assertEquals("1.0800",
                element.itemDetailList.findElement(By.xpath("//tr[@id='taxListTr']/td[7]"))
                        .getText());
    }

    /**
     * Show sales transaction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList07() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.forDetailLink.click();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_CORRECTION_HISTORY_DETAIL_URL);

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeader.click();

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeaderPayment.click();

        assertEquals(1, element.paymentList.findElements(By.tagName("tr")).size());

        assertEquals("Before",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[1]"))
                        .getText());
        assertEquals("",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[2]"))
                        .getText());
        assertEquals("",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[3]"))
                        .getText());
        assertEquals("",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[4]"))
                        .getText());
        assertEquals("21.6000",
                element.paymentList.findElement(By.xpath("//tr[@id='paymentListTr']/td[7]"))
                        .getText());
    }

    /**
     * Show sales transaction history list page. Condition： Clear button display. Result
     * confirmation： The expected input text is cleared.
     *
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesTransactionHistoryListTest.xml")
    public void testSalesTransactionHistoryList08() throws Exception {

        // Confirm initial display.
        assertInitialDisplay();
        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_HISTORY_LIST_URL);

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
        element.storeCodeInput.sendKeys("002");

        element.searchButton.click();
        // Wait page reload.
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.forDetailLink.click();

        // Confirm whether the transition destination is the item list screen.
        assertUrl(Constants.EXPECTED_SALES_TRANSACTION_CORRECTION_HISTORY_DETAIL_URL);

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeader.click();

        // Wait page reload
        Thread.sleep(Constants.THREAD_SLEEP_TIME);

        element.salesTransactionHeaderTotal.click();

        assertEquals(1, element.totalList.findElements(By.tagName("tr")).size());

        assertEquals("Before",
                element.totalList.findElement(By.xpath("//tr[@id='totalListTr']/td[1]")).getText());
        assertEquals("20.0000",
                element.totalList.findElement(By.xpath("//tr[@id='totalListTr']/td[4]")).getText());
        assertEquals("20.0000",
                element.totalList.findElement(By.xpath("//tr[@id='totalListTr']/td[5]")).getText());
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

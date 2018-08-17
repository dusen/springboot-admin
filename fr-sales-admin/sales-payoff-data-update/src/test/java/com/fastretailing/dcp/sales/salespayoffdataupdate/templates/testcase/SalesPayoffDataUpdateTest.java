/**
 * @(#)SalesPayoffDataUpdateTest.java
 *
 *                                    Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.templates.testcase;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.salespayoffdataupdate.templates.consts.Consts;
import com.fastretailing.dcp.sales.salespayoffdataupdate.templates.elements.SalesPayoffDataUpdateElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales payoff data update.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class SalesPayoffDataUpdateTest {

    /* Driver for performing browser operation. */
    protected WebDriver webDriver;

    /* Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /* Screen element (tag) information. */
    protected SalesPayoffDataUpdateElements element;

    /* The environment in current application. */
    @Autowired
    protected Environment environment;

    /**
     * Initialization processing. Configure the browser to be tested. Set Element information of the
     * screen to be tested to WebDriver.
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
     * Sales payoff data update normal case.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("Normal.xml")
    public void testInitializeNormal() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload.
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("2018/05/12", element.payoffDate.getAttribute("value"));
        assertEquals("UQ", element.brandName.getAttribute("value"));
        assertEquals("CA", element.countryName.getAttribute("value"));
        assertEquals("222", element.storeCode.getAttribute("value"));
        assertEquals("Yorkdale Shopping Centre", element.storeName.getAttribute("value"));
        assertEquals("134", element.cashRegisterNo.getAttribute("value"));
        assertEquals("Balance Mismatch", element.errorContent.getAttribute("value"));
        assertEquals("Normal", element.salesTransactionErrorStatus.getAttribute("value"));

        assertEquals("123456", element.salesPayoffDataList0PayoffTypeCode.getAttribute("value"));
        assertEquals("1", element.salesPayoffDataList0IntegrityCheckType.getAttribute("value"));
        assertEquals("1",
                element.salesPayoffDataList0PileUpPayoffDataSameDecideFlag.getAttribute("value"));
        assertEquals("", element.salesPayoffDataList0PayoffTypeName.getAttribute("value"));
        assertEquals("numbercode",
                element.salesPayoffDataList0PayoffTypeSubNumberCode.getAttribute("value"));
        assertEquals("", element.salesPayoffDataList0PayoffTypeSubNumberName.getAttribute("value"));
        assertEquals("30.0000",
                element.salesPayoffDataList0PileUpPayoffAmount.getAttribute("value"));
        assertEquals("30.0000",
                element.salesPayoffDataList0PileUpPayoffQuantity.getAttribute("value"));
        assertEquals("20.0000", element.salesPayoffDataList0PayoffAmount.getAttribute("value"));
        assertEquals("20.0000", element.salesPayoffDataList0payoffQuantity.getAttribute("value"));


        assertEquals("123457", element.salesPayoffDataList1PayoffTypeCode.getAttribute("value"));
        assertEquals("1", element.salesPayoffDataList1IntegrityCheckType.getAttribute("value"));
        assertEquals("1",
                element.salesPayoffDataList1PileUpPayoffDataSameDecideFlag.getAttribute("value"));
        assertEquals("", element.salesPayoffDataList1PayoffTypeName.getAttribute("value"));
        assertEquals("numbercode",
                element.salesPayoffDataList1PayoffTypeSubNumberCode.getAttribute("value"));
        assertEquals("", element.salesPayoffDataList1PayoffTypeSubNumberName.getAttribute("value"));
        assertEquals("60.0000",
                element.salesPayoffDataList1PileUpPayoffAmount.getAttribute("value"));
        assertEquals("60.0000",
                element.salesPayoffDataList1PileUpPayoffQuantity.getAttribute("value"));
        assertEquals("40.0000", element.salesPayoffDataList1PayoffAmount.getAttribute("value"));
        assertEquals("40.0000", element.salesPayoffDataList1payoffQuantity.getAttribute("value"));

    }

    /**
     * Sales payoff data update normal case 0 record.
     * 
     * @throws Exception (It does not occur as expected value).
     */

    @Test
    @DatabaseSetup("Normal0record.xml")
    public void testInitializeNormal0Record() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload.
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("2018/05/12", element.payoffDate.getAttribute("value"));
        assertEquals("UQ", element.brandName.getAttribute("value"));
        assertEquals("CA", element.countryName.getAttribute("value"));
        assertEquals("222", element.storeCode.getAttribute("value"));
        assertEquals("Yorkdale Shopping Centre", element.storeName.getAttribute("value"));
        assertEquals("134", element.cashRegisterNo.getAttribute("value"));
        assertEquals("Balance Mismatch", element.errorContent.getAttribute("value"));
        assertEquals("Normal", element.salesTransactionErrorStatus.getAttribute("value"));

        assertEquals("true", element.updateButtonElement.getAttribute("disabled"));
    }

    /**
     * Sales payoff data update list error.
     * 
     * @throws Exception (It does not occur as expected value).
     */

    @Test
    @DatabaseSetup("ListError.xml")
    public void testInitializeListErrorExists() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload.
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("2018/05/12", element.payoffDate.getAttribute("value"));
        assertEquals("UQ", element.brandName.getAttribute("value"));
        assertEquals("CA", element.countryName.getAttribute("value"));
        assertEquals("222", element.storeCode.getAttribute("value"));
        assertEquals("Yorkdale Shopping Centre", element.storeName.getAttribute("value"));
        assertEquals("134", element.cashRegisterNo.getAttribute("value"));
        assertEquals("Balance Mismatch", element.errorContent.getAttribute("value"));

        assertEquals("Error Exists", element.salesTransactionErrorStatus.getAttribute("value"));

        assertEquals("true", element.updateButtonElement.getAttribute("disabled"));

        assertEquals("true", element.salesPayoffDataList0PayoffAmount.getAttribute("readonly"));
        assertEquals("true", element.salesPayoffDataList0payoffQuantity.getAttribute("readonly"));
        assertEquals("true", element.salesPayoffDataList1PayoffAmount.getAttribute("readonly"));
        assertEquals("true", element.salesPayoffDataList1payoffQuantity.getAttribute("readonly"));

    }

    /**
     * Sales payoff data update normal case,integrity check type check.
     * 
     * @throws Exception (It does not occur as expected value).
     */

    @Test
    @DatabaseSetup("NormalIntegrityCheckTypeCheck.xml")
    public void testInitializeNormalIntegrityCheckTypeCheck() throws Exception {
        // Confirm whether sales payoff data update is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload.
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("2018/05/12", element.payoffDate.getAttribute("value"));
        assertEquals("UQ", element.brandName.getAttribute("value"));
        assertEquals("CA", element.countryName.getAttribute("value"));
        assertEquals("222", element.storeCode.getAttribute("value"));
        assertEquals("Yorkdale Shopping Centre", element.storeName.getAttribute("value"));
        assertEquals("134", element.cashRegisterNo.getAttribute("value"));
        assertEquals("Balance Mismatch", element.errorContent.getAttribute("value"));
        assertEquals("Normal", element.salesTransactionErrorStatus.getAttribute("value"));

        assertEquals("123456", element.salesPayoffDataList0PayoffTypeCode.getAttribute("value"));
        assertEquals("2", element.salesPayoffDataList0IntegrityCheckType.getAttribute("value"));
        assertEquals("",
                element.salesPayoffDataList0PileUpPayoffDataSameDecideFlag.getAttribute("value"));
        assertEquals("background-color: rgb(255, 242, 242);", element.td1.getAttribute("style"));


        assertEquals("123457", element.salesPayoffDataList1PayoffTypeCode.getAttribute("value"));
        assertEquals("3", element.salesPayoffDataList1IntegrityCheckType.getAttribute("value"));
        assertEquals("",
                element.salesPayoffDataList1PileUpPayoffDataSameDecideFlag.getAttribute("value"));
        assertEquals("background-color: rgb(255, 242, 242);", element.td2.getAttribute("style"));
    }

    /**
     * Sales payoff data update normal case,compare check.
     * 
     * @throws Exception (It does not occur as expected value).
     */

    @Test
    @DatabaseSetup("Normal.xml")
    public void testUpdateCompareCheck() throws Exception {
        // Confirm whether the sales payoff data update is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload.
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        //
        // element.fileElement.sendKeys(path);
        element.updateButtonElement.click();
        // Wait page reload.
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
    }

    /**
     * URL verification. Confirm whether you can transition to target URL.
     * 
     * @param url URL.
     */

    private void assertUrl(final String url) {
        // webDriverWait.until(ExpectedConditions.urlMatches(url));
        assertEquals(url, webDriver.getCurrentUrl());
    }
}

/**
 * @(#)ExpectedDeliveryListByItemTest.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.testcase;

import static org.junit.Assert.assertEquals;
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
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.consts.Consts;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.elements.SalesBrandCountrySettingEditElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of expected delivery list by item screen.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = { DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales_6.sql",
        executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales_6.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class SalesBrandCountrySettingEditTest {

    /* Driver for performing browser operation. */
    protected WebDriver webDriver;

    /* Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /* Screen element (tag) information. */
    protected SalesBrandCountrySettingEditElements element;

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
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesBrandCountrySettingEditTest.xml")
    public void testBrandCountrySettingEdit01() throws Exception {

        // Confirm initial display
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen
        assertUrl(Consts.BRAND_COUNTRY_SETTING_EDIT_URL);

        element.searchButton.click();

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        // Confirm search result
        assertEquals(2, element.brandCountrySettingEditList.findElements(By.tagName("tr")).size());


    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesBrandCountrySettingEditTest.xml")
    public void testBrandCountrySettingEdit02() throws Exception {
        // Confirm initial display
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen
        assertUrl(Consts.BRAND_COUNTRY_SETTING_EDIT_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        element.searchButton.click();

        element.numberLink.click();

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        // Confirm whether the transition destination is the item list screen
        assertUrl(Consts.STORE_SETTING_LIST_URL);
    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesBrandCountrySettingEditTest.xml")
    public void testBrandCountrySettingEdit03() throws Exception {
        // Confirm initial display
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen
        assertUrl(Consts.BRAND_COUNTRY_SETTING_EDIT_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        element.searchButton.click();

        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.numberLink.click();

        element.detailNumberLink.click();

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        // Confirm whether the transition destination is the item list screen
        assertUrl(Consts.STORE_SETTING_DETAIL_URL);
    }

    /**
     * Show expected delivery list by item page. Condition：Initial display. Press display button to
     * get expected delivery list. Verification result confirmation： The expected delivery list by
     * item page is displayed.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("SalesBrandCountrySettingEditTest.xml")
    public void testBrandCountrySettingEdit04() throws Exception {

        // Confirm initial display
        assertInitialDisplay(element);

        // Confirm whether the transition destination is the item list screen
        assertUrl(Consts.BRAND_COUNTRY_SETTING_EDIT_URL);

        element.viewStoreCodeInput.sendKeys("5000");

        element.searchButton.click();

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        // Confirm search result
        assertEquals(2, element.brandCountrySettingEditList.findElements(By.tagName("tr")).size());

    }

    /**
     * Check the initial display screen. Check the initial display of the inspection edit detail
     * screen.
     * 
     * @param pageElements Element information
     */
    private void assertInitialDisplay(final SalesBrandCountrySettingEditElements pageElements) {}

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

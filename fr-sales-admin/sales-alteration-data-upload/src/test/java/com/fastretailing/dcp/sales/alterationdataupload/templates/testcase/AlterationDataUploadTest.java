/**
 * @(#)AlterationDataUploadTest.java
 *
 *                                   Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.templates.testcase;

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
import com.fastretailing.dcp.sales.alterationdataupload.templates.consts.Consts;
import com.fastretailing.dcp.sales.alterationdataupload.templates.elements.AlterationDataUploadElements;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of alteration data upload.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners(mergeMode = MergeMode.MERGE_WITH_DEFAULTS,
        value = {DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionDbUnitTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@Sql(scripts = "/junit_create_table_sales.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/junit_drop_table_sales.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AlterationDataUploadTest {

    /* Driver for performing browser operation. */
    protected WebDriver webDriver;

    /* Wait processing of webdriver. */
    protected WebDriverWait webDriverWait;

    /* Screen element (tag) information. */
    protected AlterationDataUploadElements element;

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
     * Alteration data upload transaction data is zip file,include 6 files,check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadTransactionDataZip6Files() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path =
                classLoader.getResource("transactionData6File.zip").getPath().substring(1).replace(
                        "/", "\\");
        //
        element.alterationDataType1Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("Transaction data file is not correct.",
                element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload transaction data is zip file,include 8 files, but include .txt
     * file,check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadTransactionDataZip8FilesTxt() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("transactionData8FilesTxt.zip")
                .getPath()
                .substring(1)
                .replace("/", "\\");
        //
        element.alterationDataType1Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("The file format is not correct.", element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload transaction data is json file,but format is not correct,check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadTransactionDataJsonBadFormat() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("badTransactionDataFormat.json")
                .getPath()
                .substring(1)
                .replace("/", "\\");
        //
        element.alterationDataType1Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("It is not JSON format.", element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload transaction data is not json or zip file,check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadTransactionDataNotZipJson() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("abc.txt").getPath().substring(1).replace("/", "\\");
        //
        element.alterationDataType1Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("The file format is not correct.", element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload payoff data is not json file, check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadPayoffNotJson() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("abc.txt").getPath().substring(1).replace("/", "\\");
        //
        element.alterationDataType2Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("The file format is not correct.", element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload payoff data is json file,but format is not correct,check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadPayoffJsonBadFormat() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path =
                classLoader.getResource("badFormat.json").getPath().substring(1).replace("/", "\\");
        //
        element.alterationDataType2Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("It is not JSON format.", element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload payoff data is json file,integrity check error.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    public void testAlterationDataUploadPayoffJsonIntegrityCheckError() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("payOffDataIntegrityCheckError.json")
                .getPath()
                .substring(1)
                .replace("/", "\\");
        //
        element.alterationDataType2Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        assertEquals("CORRECTION payoff data import error occurs, so it cannot upload.",
                element.boxBodyElementError.getText());
    }

    /**
     * Alteration data upload payoff data is json file,revised by another user information.
     * 
     * @throws Exception (It does not occur as expected value).
     */
    @Test
    @DatabaseSetup("HandlePayoffJsonNotSameUser.xml")
    public void testAlterationDataUploadPayoffJsonRevisedByAnotherUser() throws Exception {
        // Confirm whether the alteration data upload is the correct screen.
        assertUrl(Consts.ALTERATION_DATA_UPLOAD_URL);

        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("payOffData.json").getPath().substring(1).replace("/",
                "\\");
        element.alterationDataType2Element.click();
        element.fileElement.sendKeys(path);
        element.uploadButtonElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        element.okElement.click();
        // Wait page reload
        Thread.sleep(Consts.THREAD_SLEEP_TIME);
        assertEquals("CORRECTION payoff data import error occurs, so it cannot upload.",
                element.boxBodyElementError.getText());
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

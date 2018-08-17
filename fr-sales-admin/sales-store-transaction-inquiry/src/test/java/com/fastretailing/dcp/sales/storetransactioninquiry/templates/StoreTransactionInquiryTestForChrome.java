/**
 * @(#)StoreTransactionInquiryTestForChrome.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.storetransactioninquiry.templates;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.storetransactioninquiry.templates.constants.Constants;
import com.fastretailing.dcp.sales.storetransactioninquiry.templates.elements.StoreTransactionInquiryElements;
import com.fastretailing.dcp.sales.storetransactioninquiry.templates.testcase.StoreTransactionInquiryTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

/**
 * Test class of store transaction inquiry screen for chrome.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class StoreTransactionInquiryTestForChrome extends StoreTransactionInquiryTest {

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {
        // Google Chrome settings.
        File file =
                new File(environment.getProperty("app.webdriver.path") + Constants.CHROME_DRIVER);
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=ja");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);
        webDriver = new ChromeDriver(options);
        webDriver.manage().window().setSize(new Dimension(1920, 1080));

        // Retrieve element information of screen.
        element = PageFactory.initElements(webDriver, StoreTransactionInquiryElements.class);
        webDriverWait = new WebDriverWait(webDriver, Constants.PROCESSING_WAIT_TIME);

        // Open sales transaction history list page.
        webDriver.get(Constants.EXPECTED_STORE_TRANSACTION_INQUIRY_URL);
    }

}

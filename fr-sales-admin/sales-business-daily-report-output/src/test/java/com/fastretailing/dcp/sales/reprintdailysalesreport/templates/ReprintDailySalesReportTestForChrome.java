/**
 * @(#)ReprintDailySalesReportTestForChrome.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.reprintdailysalesreport.templates;

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
import com.fastretailing.dcp.sales.reprintdailysalesreport.templates.constants.Constants;
import com.fastretailing.dcp.sales.reprintdailysalesreport.templates.elements.ReprintDailySalesReportElements;
import com.fastretailing.dcp.sales.reprintdailysalesreport.templates.testcase.ReprintDailySalesReportTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales reprint daily sales report screen for chrome.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
public class ReprintDailySalesReportTestForChrome extends ReprintDailySalesReportTest {

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {

        // Google chrome settings.
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
        element = PageFactory.initElements(webDriver, ReprintDailySalesReportElements.class);
        webDriverWait = new WebDriverWait(webDriver, Constants.PROCESSING_WAIT_TIME);

        // Open sales reprint daily sales report page.
        webDriver.get(Constants.EXPECTED_SALES_REPRINT_DAILY_SALES_REPORT_URL);
    }

}

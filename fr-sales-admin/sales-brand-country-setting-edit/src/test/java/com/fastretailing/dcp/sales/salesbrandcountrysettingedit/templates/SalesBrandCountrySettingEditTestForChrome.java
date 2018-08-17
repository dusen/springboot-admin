/**
 * @(#)ExpectedDeliveryListByItemTest.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.consts.Consts;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.elements.SalesBrandCountrySettingEditElements;
import com.fastretailing.dcp.sales.salesbrandcountrysettingedit.templates.testcase.SalesBrandCountrySettingEditTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

/**
 * Test class of expected delivery list by item screen for chrome.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class SalesBrandCountrySettingEditTestForChrome extends SalesBrandCountrySettingEditTest {

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {
        // Google Chrome settings
        File file = new File(environment.getProperty("app.webdriver.path") + Consts.CHROME_DRIVER);
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=ja");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        options.setExperimentalOption("prefs", prefs);
        webDriver = new ChromeDriver(options);

        // Retrieve element information of screen
        element = PageFactory.initElements(webDriver, SalesBrandCountrySettingEditElements.class);
        webDriverWait = new WebDriverWait(webDriver, Consts.PROCESSING_WAIT_TIME);

        // Open expected delivery list by item page
        webDriver.get(Consts.BRAND_COUNTRY_SETTING_EDIT_URL);
    }

}

/**
 * @(#)SalesTransactionErrorListTestForIe.java
 *
 *                                             Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salestransactionerrorlist.templates;

import java.io.File;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.salestransactionerrorlist.templates.constants.Constants;
import com.fastretailing.dcp.sales.salestransactionerrorlist.templates.elements.SalesTransactionErrorListElements;
import com.fastretailing.dcp.sales.salestransactionerrorlist.templates.testcase.SalesTransactionErrorListTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales transaction error list screen for ie.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
public class SalesTransactionErrorListTestForIe extends SalesTransactionErrorListTest {

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {

        // Internet Explorer settings.
        File file = new File(environment.getProperty("app.webdriver.path") + Constants.IE_DRIVER);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        webDriver = new InternetExplorerDriver();
        webDriver.manage().window().setSize(new Dimension(1920, 1080));

        // Retrieve element information of screen.
        element = PageFactory.initElements(webDriver, SalesTransactionErrorListElements.class);
        webDriverWait = new WebDriverWait(webDriver, Constants.PROCESSING_WAIT_TIME);

        // Open sales transaction error list page.
        webDriver.get(Constants.EXPECTED_SALES_TRANSACTION_ERROR_LIST_URL);
    }
}

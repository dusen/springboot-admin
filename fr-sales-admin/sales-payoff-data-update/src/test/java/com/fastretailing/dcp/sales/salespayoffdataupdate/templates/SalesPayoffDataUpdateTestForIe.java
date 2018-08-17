/**
 * @(#)SalesPayoffDataUpdateTestForIe.java
 *
 *                                         Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.salespayoffdataupdate.templates;

import java.io.File;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import com.fastretailing.dcp.sales.salespayoffdataupdate.templates.consts.Consts;
import com.fastretailing.dcp.sales.salespayoffdataupdate.templates.elements.SalesPayoffDataUpdateElements;
import com.fastretailing.dcp.sales.salespayoffdataupdate.templates.testcase.SalesPayoffDataUpdateTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of sales payoff data update screen for ie.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
public class SalesPayoffDataUpdateTestForIe extends SalesPayoffDataUpdateTest {

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {

        // Internet Explorer settings.
        File file = new File(environment.getProperty("app.webdriver.path") + Consts.IE_DRIVER);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        webDriver = new InternetExplorerDriver();

        // Retrieve element information of screen.
        element = PageFactory.initElements(webDriver, SalesPayoffDataUpdateElements.class);
        webDriverWait = new WebDriverWait(webDriver, Consts.PROCESSING_WAIT_TIME);

        // Open sales payoff data update page.
        webDriver.get(Consts.ALTERATION_DATA_UPLOAD_URL);
    }
}

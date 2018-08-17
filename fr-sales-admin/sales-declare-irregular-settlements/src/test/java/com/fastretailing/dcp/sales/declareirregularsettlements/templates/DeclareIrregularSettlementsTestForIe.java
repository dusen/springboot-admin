/**
 * @(#)DeclareIrregularSettlementsTestForIe.java
 *
 *                                               Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.sales.declareirregularsettlements.templates;

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
import com.fastretailing.dcp.sales.declareirregularsettlements.templates.constants.Constants;
import com.fastretailing.dcp.sales.declareirregularsettlements.templates.elements.DeclareIrregularSettlementsElements;
import com.fastretailing.dcp.sales.declareirregularsettlements.templates.testcase.DeclareIrregularSettlementsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of declare irregular settlements test screen for ie.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
public class DeclareIrregularSettlementsTestForIe extends DeclareIrregularSettlementsTest {

    /**
     * {@inheritDoc}
     */
    @Override
    @Before
    public void setUp() throws Exception {

        // Internet explorer settings.
        File file = new File(environment.getProperty("app.webdriver.path") + Constants.IE_DRIVER);
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        webDriver = new InternetExplorerDriver();
        webDriver.manage().window().setSize(new Dimension(1920, 1080));

        // Retrieve element information of screen.
        element = PageFactory.initElements(webDriver, DeclareIrregularSettlementsElements.class);
        webDriverWait = new WebDriverWait(webDriver, Constants.PROCESSING_WAIT_TIME);

        // Open declare irregular settlements page.
        webDriver.get(Constants.DECLARE_IRREGULAR_SETTLEMENTS_URL);
    }
}

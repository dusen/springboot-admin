/**
 * @(#)TransferOutInstructionReferenceDetailTestForIe.java
 *
 *                                                         Copyright (c) 2018 Fast Retailing
 *                                                         Corporation.
 */

package com.fastretailing.dcp.sales.alterationdataupload.templates;

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
import com.fastretailing.dcp.sales.alterationdataupload.templates.consts.Consts;
import com.fastretailing.dcp.sales.alterationdataupload.templates.elements.AlterationDataUploadElements;
import com.fastretailing.dcp.sales.alterationdataupload.templates.testcase.AlterationDataUploadTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

/**
 * Test class of transfer out instruction reference detail screen for ie.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)

public class AlterationDataUploadTestForIe extends AlterationDataUploadTest {

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
        element = PageFactory.initElements(webDriver, AlterationDataUploadElements.class);
        webDriverWait = new WebDriverWait(webDriver, Consts.PROCESSING_WAIT_TIME);

        // Open expected delivery list by item page.
        webDriver.get(Consts.ALTERATION_DATA_UPLOAD_URL);
    }
}

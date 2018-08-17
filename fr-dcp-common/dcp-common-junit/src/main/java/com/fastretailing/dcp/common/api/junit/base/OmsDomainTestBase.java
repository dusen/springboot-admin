/**
 * @(#)OmsDomainTestBase.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.base;

import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * TestBase for Domain Layer.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ContextConfiguration(
        locations = {
                "classpath*:junit-test-base-spring-context.xml",
                "classpath*:junit-test-spring-context.xml"
        }
)
public class OmsDomainTestBase extends OmsAssertorBase {

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }

    /**
     * Before method.<br>
     * When writing tests,
     * it is common to find that several tests need similar objects created before they can run.
     */
    @Before
    @Override
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * After method.<br>
     * If you allocate external resources in a
     * @Before method you need to release them after the test runs.
     */
    @After
    @Override
    public void tearDown() throws Exception {
        // Do Nothing.
    }

    @Override
    protected IDataSet buildDataSet(String filePath) throws Exception {
        // Do Nothing.
        return null;
    }

    @Override
    protected DataSource getDataSource() {
        // Do Nothing.
        return null;
    }
}

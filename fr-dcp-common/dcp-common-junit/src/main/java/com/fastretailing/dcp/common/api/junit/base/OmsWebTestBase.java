/**
 * @(#)OmsWebTestBase.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.base;

import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

/**
 * TestBase for Web Layer.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class OmsWebTestBase extends OmsAssertorBase {

    /**
     * MockMvc instance.<br>
     * Main entry point for server-side Spring MVC test support.
     */
    @Autowired
    protected MockMvc mockMvc;

    /**
     * Sometimes several tests need to share computationally expensive setup.<br>
     */
    @BeforeClass
    public static void beforeClass() {
        // Do Nothing.
    }

    /**
     * If you allocate expensive external resources
     * in a @BeforeClass method you need to release them
     * after all the tests in the class have run.<br>
     */
    @AfterClass
    public static void afterClass() {
        // Do Nothing.
    }

    /**
     * Before method.<br>
     * When writing tests,
     * it is common to find that several tests need similar objects created before they can run.
     */
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * After method.<br>
     * If you allocate external resources in a
     * @Before method you need to release them after the test runs.
     */
    @After
    @Override
    public void tearDown() {
        // Do Nothing.
    }

    /**
     * Get dataSource.
     * @return dataSource
     */
    @Override
    protected DataSource getDataSource() {
        // Do Nothing
        return null;
    }

    /**
     * Get dataSet.
     * @return dataSet
     */
    @Override
    protected IDataSet getDataSet() throws Exception {
        // Do Nothing
        return null;
    }

    @Override
    protected IDataSet buildDataSet(String filePath) throws Exception {
        // Do Nothing
        return null;
    }
}

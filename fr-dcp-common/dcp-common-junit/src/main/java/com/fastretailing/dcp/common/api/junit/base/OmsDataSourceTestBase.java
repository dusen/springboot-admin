/**
 * @(#)OmsDataSourceTestBase.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.base;

import com.fastretailing.dcp.common.api.datasource.context.DataSourceContext;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContextHolder;
import com.fastretailing.dcp.common.api.datasource.lookup.RoutingDataSourceResolver;
import com.fastretailing.dcp.common.api.junit.datasource.JunitDataSourceConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.operation.TransactionOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * TestBase for Repository Layer.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public abstract class OmsDataSourceTestBase extends OmsDataSourceBasedDBTestCase {

    /**
     * Initialized flag.<br>
     */
    private static boolean initialized = false;

    /**
     * Clean database's all record SQL.<br>
     */
    private String truncateSql;

    /**
     * JunitDataSourceConfiguration instance.<br>
     */
    @Autowired
    private JunitDataSourceConfiguration dataSourceConfig;

    /**
     * JunitRoutingDataSourceResolver instance.<br>
     */
    @Autowired
    private RoutingDataSourceResolver dataSourceResolver;

    /**
     * Current using DataSource name.<br>
     */
    private String currentDataSourceName;

    /**
     * Current using DataSource type.<br>
     */
    private String currentDataSourceType;

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
     * Initialize test env.<br>
     * @throws Exception Exception
     */
    @Before
    @Override
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        if (!initialized) {
            this.truncateSql = this.genericTruncateSql();
        }
        this.cleanDataBaseAllData();
        initialized = true;
    }

    /**
     * Clean up test env.<br>
     * @throws Exception Exception
     */
    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        this.cleanDataBaseAllData();
    }

    /**
     * Return the test DataSource.<br>
     * @return DataSource
     */
    @Override
    public DataSource getDataSource() {
        return this.dataSourceResolver;
    }

    /**
     * Init Database, clean all record.<br>
     */
    @Override
    public void cleanDataBaseAllData() {
        if (StringUtils.isNotBlank(this.truncateSql)) {
            DataSourceContext dataSourceContext = new DataSourceContext(
                    dataSourceConfig.getDefaultDataSourceName(),
                    JunitDataSourceConfiguration.DATASOURCE_TYPE_WRITE
            );
            DataSourceContextHolder.setDataSourceContext(dataSourceContext);
            new JdbcTemplate(dataSourceResolver).execute(this.truncateSql);
        }
    }

    /**
     * Connect the default datasource.<br>
     * The default datasource must be readable and writable.<br>
     * This method is using for Repository-Layer's test only.<br>
     * @return OmsWebDataSourceTestBase
     */
    public OmsDataSourceTestBase connectDefault() {
        this.currentDataSourceName = dataSourceConfig.getDefaultDataSourceName();
        this.currentDataSourceType = JunitDataSourceConfiguration.DATASOURCE_TYPE_WRITE;
        return this;
    }

    /**
     * Push data into Database.<br>
     * @throws Exception Exception
     */
    @Override
    public void push() throws Exception {
        DataSourceContext dataSourceContext = new DataSourceContext(
                this.currentDataSourceName, this.currentDataSourceType
        );
        DataSourceContextHolder.setDataSourceContext(dataSourceContext);
        super.push();
    }

}

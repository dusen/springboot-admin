/**
 * @(#)OmsWebDataSourceTestBase.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.base;

import com.fastretailing.dcp.common.api.datasource.context.DataSourceContext;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContextHolder;
import com.fastretailing.dcp.common.api.datasource.lookup.RoutingDataSourceResolver;
import com.fastretailing.dcp.common.api.junit.datasource.JunitDataSourceConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TestBase for All Layer.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public abstract class OmsWebDataSourceTestBase extends OmsDataSourceBasedDBTestCase {

    /**
     * MockMvc instance.<br>
     * Main entry point for server-side Spring MVC test support.
     */
    public MockMvc mockMvc;

    /**
     * WebApplicationContext instance.<br>
     */
    @Autowired
    private WebApplicationContext wac;

    /**
     * Initialized flag.<br>
     */
    private static boolean initialized = false;

    /**
     * Clean database's all record SQL.<br>
     */
    private String truncateSql;

    /**
     * Current using DataSource name.<br>
     */
    private String currentDataSourceName;

    /**
     * Current using DataSource type.<br>
     */
    private String currentDataSourceType;

    /**
     * Cleanup storage map.<br>
     */
    private Map<String, DataSourceContext> tearDownStorage = new LinkedHashMap<>();

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
     * Sometimes several tests need to share computationally expensive setup.<br>
     */
    @BeforeClass
    public static void beforeClass() {
    }

    /**
     * If you allocate expensive external resources in a @BeforeClass method you need to release
     * them after all the tests in the class have run.<br>
     */
    @AfterClass
    public static void afterClass() {
    }

    /**
     * Initialize test env.<br>
     * @throws Exception Exception
     */
    @Before
    @Override
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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
        for (Map.Entry<String, DataSourceContext> entry : tearDownStorage.entrySet()) {
            DataSourceContextHolder.setDataSourceContext(entry.getValue());
            super.tearDown();
        }
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
            dataSourceConfig.getDataSources().stream().forEach(
                    holder -> holder.getProperties().forEach(
                        (type, prop) -> {

                            DataSourceContext dataSourceContext = new DataSourceContext(
                                    holder.getDataSourceName(), type
                            );

                            DataSourceContextHolder.setDataSourceContext(dataSourceContext);
                            new JdbcTemplate(dataSourceResolver)
                                    .execute(this.truncateSql);
                        }
                )
            );
        }
    }


    /**
     * Connect the Master datasource.<br>
     * @param dataSourceName datasource's name
     * @return OmsWebDataSourceTestBase
     */
    public OmsWebDataSourceTestBase connectMaster(String dataSourceName) {
        this.currentDataSourceName = dataSourceName;
        this.currentDataSourceType = JunitDataSourceConfiguration.DATASOURCE_TYPE_WRITE;
        return this;
    }

    /**
     * Connect the master Read-Only datasource.<br>
     * @param dataSourceName datasource's name
     * @return OmsWebDataSourceTestBase
     */
    public OmsWebDataSourceTestBase connectReadOnly(String dataSourceName) {
        this.currentDataSourceName = dataSourceName;
        this.currentDataSourceType = JunitDataSourceConfiguration.DATASOURCE_TYPE_READONLY;
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

        String dataSourceKey = JunitDataSourceConfiguration.getDataSourceName(
                this.currentDataSourceName,
                this.currentDataSourceType
        );

        if (!tearDownStorage.containsKey(dataSourceKey)) {
            tearDownStorage.put(dataSourceKey, dataSourceContext);
        }

        super.push();
    }

}

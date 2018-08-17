/**
 * @(#)DataSourceConfigurationTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource;

import com.fastretailing.dcp.common.api.datasource.configuration.DataSourceProperties;
import com.fastretailing.dcp.common.api.datasource.configuration.DataSourcePropertiesHolder;
import com.fastretailing.dcp.common.api.datasource.lookup.RoutingDataSourceResolver;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The unit test for DataSourceConfiguration.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/datasource/test-context.xml")
})
@Slf4j
public class DataSourceConfigurationTest {

    private DataSourceConfiguration target = new DataSourceConfiguration();

    @Before
    public void setUp() {
        this.initDefaultDataSourceName();
        this.initDataSourceHolders();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDataSource() {

        RoutingDataSourceResolver actual = target.dataSource();

        Map<Object, Object> actualTargetDataSource = (Map<Object, Object>) ReflectionTestUtils.getField(actual, "targetDataSources");

        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource1-master"), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource1-readOnly"), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource2-master"), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource2-readOnly"), CoreMatchers.nullValue());

    }

    @Test
    public void testDataSource1() {

        RoutingDataSourceResolver actual = target.dataSource();

        Map<Object, Object> actualTargetDataSource = (Map<Object, Object>) ReflectionTestUtils.getField(actual, "targetDataSources");

        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource1-master"), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource1-readOnly"), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource2-master"), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actualTargetDataSource.get("mockDataSource2-readOnly"), CoreMatchers.nullValue());

    }

    @Test
    public void testAnnotationDrivenTransactionManager() {
        PlatformTransactionManager actual = target.annotationDrivenTransactionManager();
        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }


    private void initDefaultDataSourceName() {
        ReflectionTestUtils.setField(target, "defaultDataSourceName", "mockDataSource1");
    }

    private void initDataSourceHolders() {

        List<DataSourcePropertiesHolder> list = new ArrayList<>();

        DataSourcePropertiesHolder mockDsHolder1 = new DataSourcePropertiesHolder();
        mockDsHolder1.setDataSourceName("mockDataSource1");
        Map<String, DataSourceProperties> prop1 = new HashMap<>();
        DataSourceProperties prop1master = new DataSourceProperties();
        prop1master.setUsername("mockMasterName1");
        prop1master.setPassword("mockPassword1");
        prop1master.setUrl("jdbc:postgresql://basket.oms.fastretailing.com:5432/ds1_master");
        prop1master.setDriverClassName("org.postgresql.Driver");
        prop1.put("master", prop1master);
        DataSourceProperties prop1readOnly = new DataSourceProperties();
        prop1readOnly.setUsername("mockReadOnlyName1");
        prop1readOnly.setPassword("mockPassword1");
        prop1readOnly.setUrl("jdbc:postgresql://basket.oms.fastretailing.com:5432/ds1_readOnly");
        prop1readOnly.setDriverClassName("org.postgresql.Driver");
        prop1.put("readOnly", prop1readOnly);
        mockDsHolder1.setProperties(prop1);

        DataSourcePropertiesHolder mockDsHolder2 = new DataSourcePropertiesHolder();
        mockDsHolder2.setDataSourceName("mockDataSource2");
        Map<String, DataSourceProperties> prop2 = new HashMap<>();
        DataSourceProperties prop2master = new DataSourceProperties();
        prop2master.setUsername("mockMasterName2");
        prop2master.setPassword("mockPassword2");
        prop2master.setUrl("jdbc:postgresql://basket.oms.fastretailing.com:5432/ds2_master");
        prop2master.setDriverClassName("org.postgresql.Driver");
        prop2.put("master", prop2master);
        mockDsHolder2.setProperties(prop2);

        list.add(mockDsHolder1);
        list.add(mockDsHolder2);

        ReflectionTestUtils.setField(target, "dataSources", list);
    }

}

/**
 * @(#)RoutingDataSourceResolverTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.lookup;

import com.fastretailing.dcp.common.api.datasource.context.DataSourceContext;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * The unit test for RoutingDataSourceResolver.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/datasource/test-context.xml")
})
@Slf4j
public class RoutingDataSourceResolverTest {

    private RoutingDataSourceResolver target = new RoutingDataSourceResolver();

    @Before
    public void setUp() {
        this.initDefaultDataSourceName();
    }

    private void initDefaultDataSourceName() {
        ReflectionTestUtils.setField(target, "defaultDataSourceName", "mockDataSource1");
    }

    @Test
    public void testLookup() {
        DataSourceContext ctx = new DataSourceContext();
        ctx.setDataSourceName("ds1");
        ctx.setDataSourceType("master");
        DataSourceContextHolder.setDataSourceContext(ctx);

        Object actual = target.determineCurrentLookupKey();
        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

    @Test
    public void testLookupKeyIsEmpty() {
        DataSourceContext ctx = new DataSourceContext();
        ctx.setDataSourceName("");
        DataSourceContextHolder.setDataSourceContext(ctx);

        Object actual = target.determineCurrentLookupKey();
        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

    @Test
    public void testLookupContextIsNull() {
        Object actual = target.determineCurrentLookupKey();
        MatcherAssert.assertThat(actual, CoreMatchers.notNullValue());
    }

}

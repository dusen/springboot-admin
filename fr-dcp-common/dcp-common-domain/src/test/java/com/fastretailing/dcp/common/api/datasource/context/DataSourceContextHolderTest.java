/**
 * @(#)DataSourceContextHolderTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.context;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * The unit test for DataSourceContextHolder.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class DataSourceContextHolderTest {

    @Test
    public void testNoArgsConstructor() {

        DataSourceContext ctx = new DataSourceContext();
        ctx.setDataSourceName("dsName");
        ctx.setDataSourceType("master");
        DataSourceContextHolder.setDataSourceContext(ctx);

        DataSourceContext actual = DataSourceContextHolder.getDataSourceContext();

        MatcherAssert.assertThat(actual.getDataSourceName(), CoreMatchers.is("dsName"));
        MatcherAssert.assertThat(actual.getDataSourceType(), CoreMatchers.is("master"));

        DataSourceContextHolder.cleanDataSourceContext();

        DataSourceContext cleanActual = DataSourceContextHolder.getDataSourceContext();

        MatcherAssert.assertThat(cleanActual, CoreMatchers.nullValue());
        MatcherAssert.assertThat(new DataSourceContextHolder(), CoreMatchers.notNullValue());

    }

    @Test
    public void testAllArgsConstructor() {

        DataSourceContextHolder.setDataSourceContext(new DataSourceContext("dsName", "master"));

        DataSourceContext actual = DataSourceContextHolder.getDataSourceContext();

        MatcherAssert.assertThat(actual.getDataSourceName(), CoreMatchers.is("dsName"));
        MatcherAssert.assertThat(actual.getDataSourceType(), CoreMatchers.is("master"));

        DataSourceContextHolder.cleanDataSourceContext();

        DataSourceContext cleanActual = DataSourceContextHolder.getDataSourceContext();

        MatcherAssert.assertThat(cleanActual, CoreMatchers.nullValue());

    }


}

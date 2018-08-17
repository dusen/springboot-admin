/**
 * @(#)MockBizClass.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.interceptor;

import com.fastretailing.dcp.common.api.datasource.annotation.InnerTransactional;
import com.fastretailing.dcp.common.api.datasource.annotation.ReadOnlyTransactional;
import com.fastretailing.dcp.common.api.datasource.annotation.WriteTransactional;
import com.fastretailing.dcp.common.api.datasource.annotation.WriteTransactionalAsNew;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContext;
import com.fastretailing.dcp.common.api.datasource.context.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.springframework.stereotype.Component;

/**
 * The unit test for DataSourceSwitchInterceptor.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
@Slf4j
public class MockBizClass {

    @WriteTransactional(name = "ds1")
    public void assertWriteTransactional()  {
        DataSourceContext actual = DataSourceContextHolder.getDataSourceContext();
        MatcherAssert.assertThat(actual.getDataSourceName(), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actual.getDataSourceType(), CoreMatchers.notNullValue());
    }

    @ReadOnlyTransactional(name = "ds1")
    public void assertReadOnlyTransactional()  {
        DataSourceContext actual = DataSourceContextHolder.getDataSourceContext();
        MatcherAssert.assertThat(actual.getDataSourceName(), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actual.getDataSourceType(), CoreMatchers.notNullValue());
    }

    @ReadOnlyTransactional(name = "ds1")
    @InnerTransactional
    public void assertInnerTransactional()  {
        DataSourceContext actual = DataSourceContextHolder.getDataSourceContext();
        MatcherAssert.assertThat(actual.getDataSourceName(), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actual.getDataSourceType(), CoreMatchers.notNullValue());
    }

    @WriteTransactionalAsNew(name = "ds1")
    public void assertWriteTransactionalAsNew()  {
        DataSourceContext actual = DataSourceContextHolder.getDataSourceContext();
        MatcherAssert.assertThat(actual.getDataSourceName(), CoreMatchers.notNullValue());
        MatcherAssert.assertThat(actual.getDataSourceType(), CoreMatchers.notNullValue());
    }

    @InnerTransactional
    public void assertInnerTransactionalError()  {
    }

}

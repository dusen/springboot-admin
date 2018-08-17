/**
 * @(#)DataSourceSwitchInterceptorTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.datasource.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.batch.core.step.tasklet.SystemCommandException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The unit test for DataSourceSwitchInterceptor.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/datasource/test-context.xml")
})
@Slf4j
public class DataSourceSwitchInterceptorTest {

    @Autowired
    private MockBizClass target;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @After
    public void tearDown() {
    }

    @Test
    public void testWriteTransactional() {
        target.assertWriteTransactional();
    }

    public void testReadOnlyTransactional(){
        target.assertReadOnlyTransactional();
    }

    @Test
    public void testInnerInnerTransactionalOK() {
        target.assertInnerTransactional();
    }

    @Test
    public void testWriteTransactionalAsNewOK() {
        target.assertWriteTransactionalAsNew();
    }

    @Test
    public void testInnerTransactionalError() {
        thrown.expect(SystemCommandException.class);
        thrown.expectMessage(CoreMatchers.is("There is no outer transaction."));
        target.assertInnerTransactionalError();
    }

}

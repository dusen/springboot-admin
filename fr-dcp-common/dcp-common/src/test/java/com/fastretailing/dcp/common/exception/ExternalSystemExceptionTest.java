/**
 * @(#)ExternalSystemExceptionTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.exception;

import com.fastretailing.dcp.common.model.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ExternalSystemExceptionTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        ResultObject resultObject = new ResultObject();
        resultObject.setDebugId("0");
        ResultObject resultObject1 = new ResultObject();
        resultObject1.setDebugId("1");
        ExternalSystemException exception = new ExternalSystemException(resultObject);
        exception.setResultObject(resultObject1);

        assertThat(resultObject.equals(exception.getResultObject()), is(false));
        assertThat(resultObject1.equals(exception.getResultObject()), is(true));
        assertThat(Objects.isNull(exception), is(false));
    }

}

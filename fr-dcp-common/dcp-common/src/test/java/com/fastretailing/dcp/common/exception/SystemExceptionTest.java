/**
 * @(#)SystemExceptionTest.java Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class SystemExceptionTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        SystemException exception = new SystemException("123", null);

        assertThat(Objects.isNull(exception), is(false));
        assertThat(exception.getMessage(), is("123"));
    }

    @Test
    public void test2() throws Exception {

        SystemException exception = new SystemException("123");

        assertThat(Objects.isNull(exception), is(false));
        assertThat(exception.getMessage(), is("123"));
    }

    @Test
    public void test3() throws Exception {

        SystemException exception = new SystemException();

        assertThat(Objects.isNull(exception), is(false));
    }

}

/**
 * @(#)BatchBusinessExceptionTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.batch.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.ExitStatus;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class BatchBusinessExceptionTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        BatchBusinessException exception = new BatchBusinessException("123");
        assertThat(Objects.isNull(exception), is(false));
        assertThat(exception.getMessage(), is("123"));
    }

    @Test
    public void test2() throws Exception {

        BatchBusinessException exception = new BatchBusinessException();
        exception.setExitStatus(ExitStatus.FAILED);

        assertThat(Objects.isNull(exception), is(false));
        assertThat(exception.getExitStatus(), is(ExitStatus.FAILED));
    }

    @Test
    public void test3() throws Exception {

        BatchBusinessException exception = new BatchBusinessException("123", ExitStatus.FAILED);

        assertThat(Objects.isNull(exception), is(false));
        assertThat(exception.getMessage(), is("123"));
        assertThat(exception.getExitStatus(), is(ExitStatus.FAILED));
    }

}

/**
 * @(#)InitializeUncompletedExceptionTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
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
public class InitializeUncompletedExceptionTest {

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() throws Exception {

        InitializeUncompletedException exception = new InitializeUncompletedException("123");

        assertThat(Objects.isNull(exception), is(false));
        assertThat(exception.getMessage(), is("123"));
    }

}

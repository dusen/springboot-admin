/**
 * @(#)HmacAuthenticationJoinPointTest.java Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.mail.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@Slf4j
public class MailUtilityTest {

    @InjectMocks
    private MailUtility mailUtility;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * test concatMailAddress
     */
    @Test
    public void testConcatMailAddress() {

        String result = MailUtility.concatMailAddress("123", "abc", "456");

        assertThat(result, is("123,abc,456"));

    }

    @Test
    public void test1() {

        assertThat(Objects.isNull(new MailUtility()), is(false));

    }

}

/**
 * @(#)MD5UtilityTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * test class of MD5Utility.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class MD5UtilityTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void md501() {
        String expected = "";
        String actual = MD5Utility.md5("");
        assertThat(actual,is(expected));
    }

    @Test
    public void md502() {
        int actual = MD5Utility.md5("any string").length();
        assertThat(actual, is(128 / 4));
    }

    @Test

    public void tset1() {
        assertThat(Objects.isNull(new MD5Utility()), is(false));
    }
}

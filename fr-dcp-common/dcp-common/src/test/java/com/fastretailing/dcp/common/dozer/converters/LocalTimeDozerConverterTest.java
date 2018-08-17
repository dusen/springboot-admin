/**
 * @(#)LocalTimeDozerConverterTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.dozer.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.LocalTime;

import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalTimeDozerConverterTest {
    private static LocalTimeDozerConverter localTimeDozerConverter;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        localTimeDozerConverter = new LocalTimeDozerConverter();
    }

    /**
     * source==null
     */
    @Test
    public void convertTo01() {
        log.debug("LocalTimeDozerConverterTest.convertTo01");

        LocalTime source = null;
        LocalTime destination = LocalTime.of(0, 0, 0, 0);
        LocalTime actual = localTimeDozerConverter.convertTo(source ,destination);

        // assert the result
        assertNull(actual);
    }

    /**
     * ok case
     */
    @Test
    public void convertTo02() {
        log.debug("LocalTimeDozerConverterTest.convertTo02");

        LocalTime source = LocalTime.of(23, 59, 59, 999);
        LocalTime destination = LocalTime.of(0, 0, 0, 0);
        LocalTime actual = localTimeDozerConverter.convertTo(source ,destination);

        // assert the result
        assertThat(actual, is(source));
    }

    /**
     * source==null
     */
    @Test
    public void convertFrom01() {
        log.debug("LocalTimeDozerConverterTest.convertFrom01");

        LocalTime source = null;
        LocalTime destination = LocalTime.of(0, 0, 0, 0);
        LocalTime actual = localTimeDozerConverter.convertFrom(source ,destination);

        // assert the result
        assertNull(actual);
    }

    /**
     * ok case
     */
    @Test
    public void convertFrom02() {
        log.debug("LocalTimeDozerConverterTest.convertFrom02");

        LocalTime source = LocalTime.of(23, 59, 59, 999);
        LocalTime destination = LocalTime.of(0, 0, 0, 0);
        LocalTime actual = localTimeDozerConverter.convertFrom(source ,destination);

        // assert the result
        assertThat(actual, is(source));
    }
}

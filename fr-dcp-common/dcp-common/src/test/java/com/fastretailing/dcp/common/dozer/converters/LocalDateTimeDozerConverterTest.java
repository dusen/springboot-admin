/**
 * @(#)LocalDateTimeDozerConverterTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.dozer.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalDateTimeDozerConverterTest {
    private static LocalDateTimeDozerConverter localDateTimeDozerConverter;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        localDateTimeDozerConverter = new LocalDateTimeDozerConverter();
    }

    /**
     * source==null
     */
    @Test
    public void convertTo01() {
        log.debug("LocalDateTimeDozerConverterTest.convertTo01");

        LocalDateTime source = null;
        LocalDateTime destination = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        LocalDateTime actual = localDateTimeDozerConverter.convertTo(source ,destination);

        // assert the result
        assertNull(actual);
    }

    /**
     * ok case
     */
    @Test
    public void convertTo02() {
        log.debug("LocalDateTimeDozerConverterTest.convertTo02");

        LocalDateTime source = LocalDateTime.of(2017, 12, 31, 23, 59, 59);
        LocalDateTime destination = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        LocalDateTime actual = localDateTimeDozerConverter.convertTo(source ,destination);

        // assert the result
        assertThat(actual, is(source));
    }

    /**
     * source==null
     */
    @Test
    public void convertFrom01() {
        log.debug("LocalDateTimeDozerConverterTest.convertFrom01");

        LocalDateTime source = null;
        LocalDateTime destination = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        LocalDateTime actual = localDateTimeDozerConverter.convertFrom(source ,destination);

        // assert the result
        assertNull(actual);
    }

    /**
     * ok case
     */
    @Test
    public void convertFrom02() {
        log.debug("LocalDateTimeDozerConverterTest.convertFrom02");

        LocalDateTime source = LocalDateTime.of(2017, 12, 31, 23, 59, 59);
        LocalDateTime destination = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        LocalDateTime actual = localDateTimeDozerConverter.convertFrom(source ,destination);

        // assert the result
        assertThat(actual, is(source));
    }
}

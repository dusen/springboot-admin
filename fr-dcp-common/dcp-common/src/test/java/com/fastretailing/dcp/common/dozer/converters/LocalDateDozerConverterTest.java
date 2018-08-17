/**
 * @(#)LocalDateDozerConverterTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.dozer.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalDateDozerConverterTest {

    private static LocalDateDozerConverter localDateDozerConverter;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        localDateDozerConverter = new LocalDateDozerConverter();
    }

    /**
     * source==null
     */
    @Test
    public void convertTo01() {
        log.debug("LocalDateDozerConverterTest.convertTo01");

        LocalDate source = null;
        LocalDate destination = LocalDate.of(1900, 1, 1);
        LocalDate actual = localDateDozerConverter.convertTo(source ,destination);

        // assert the result
        assertNull(actual);
    }

    /**
     * ok case
     */
    @Test
    public void convertTo02() {
        log.debug("LocalDateDozerConverterTest.convertTo02");

        LocalDate source = LocalDate.of(2017, 12, 31);
        LocalDate destination = LocalDate.of(1900, 1, 1);
        LocalDate actual = localDateDozerConverter.convertTo(source ,destination);

        // assert the result
        assertThat(actual, is(source));
    }

    /**
     * source==null
     */
    @Test
    public void convertFrom01() {
        log.debug("LocalDateDozerConverterTest.convertFrom01");

        LocalDate source = null;
        LocalDate destination = LocalDate.of(1900, 1, 1);
        LocalDate actual = localDateDozerConverter.convertFrom(source ,destination);

        // assert the result
        assertNull(actual);
    }

    /**
     * ok case
     */
    @Test
    public void convertFrom02() {
        log.debug("LocalDateDozerConverterTest.convertFrom02");

        LocalDate source = LocalDate.of(2017, 12, 31);
        LocalDate destination = LocalDate.of(1900, 1, 1);
        LocalDate actual = localDateDozerConverter.convertFrom(source ,destination);

        // assert the result
        assertThat(actual, is(source));
    }
}

/**
 * @(#)DozerConfigurationTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.dozer.configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.dozer.DozerBeanMapper;
import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath:META-INF/dozer/test-context.xml")
})
@Slf4j
public class DozerConfigurationTest {

    @Autowired
    DozerBeanMapperFactoryBean dozerBeanMapperFactoryBean;

    DozerBeanMapper beanMapper;

    @Before
    public void setUp() throws Exception {
        beanMapper = (DozerBeanMapper)dozerBeanMapperFactoryBean.getObject();
    }

    /**
     * test LocalDateDozerConverter
     */
    @Test
    public void testLocalDateDozerConverter01() throws Exception {
        log.debug("DozerConfigurationTest.testLocalDateDozerConverter01");


        LocalDate sourceLocalDate = LocalDate.of(2018, 3, 26);

        LocalDate actual = beanMapper.map(sourceLocalDate, LocalDate.class);

        // assert
        MatcherAssert.assertThat(actual, CoreMatchers.is(sourceLocalDate));
    }

    /**
     * test LocalDateTimeDozerConverter
     */
    @Test
    public void testLocalDateTimeDozerConverter01() throws Exception {
        log.debug("DozerConfigurationTest.testLocalDateTimeDozerConverter01");

        LocalDateTime sourceLocalDateTime = LocalDateTime.of(2018, 3, 26, 14, 05 , 59);

        LocalDateTime actual = beanMapper.map(sourceLocalDateTime, LocalDateTime.class);

        // assert
        MatcherAssert.assertThat(actual, CoreMatchers.is(sourceLocalDateTime));
    }

    /**
     * test LocalTimeDozerConverter
     */
    @Test
    public void testLocalTimeDozerConverter01() throws Exception {
        log.debug("DozerConfigurationTest.testLocalTimeDozerConverter01");

        LocalTime sourceLocalTime = LocalTime.of(14, 05, 59);

        LocalTime actual = beanMapper.map(sourceLocalTime, LocalTime.class);

        // assert
        MatcherAssert.assertThat(actual, CoreMatchers.is(sourceLocalTime));
    }

}

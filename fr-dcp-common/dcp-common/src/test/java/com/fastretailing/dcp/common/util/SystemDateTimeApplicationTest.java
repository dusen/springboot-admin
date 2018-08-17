/**
 * @(#)SystemDateTimeApplicationTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SystemDateTimeApplicationConfig.class)
public class SystemDateTimeApplicationTest {

    @Autowired
    private SystemDateTime systemDateTime;

    @Autowired
    private SystemDateTimeApplication app;

    @Test
    public void fixDateTimeUsingMock() {
        when(systemDateTime.now()).thenReturn(OffsetDateTime.parse("2018-06-29T10:30:00Z"));

        OffsetDateTime result = app.get();

        assertEquals(OffsetDateTime.parse("2018-06-29T10:30:00Z"), result);
    }
}

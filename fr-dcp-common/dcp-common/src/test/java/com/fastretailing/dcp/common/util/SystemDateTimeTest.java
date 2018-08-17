/**
 * @(#)SystemDateTimeTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.util;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

public class SystemDateTimeTest {

    @Test
    public void getOffsetShouldBeUTC() {
        SystemDateTime systemDateTime = new SystemDateTime();

        OffsetDateTime now = systemDateTime.now();

        assertEquals(ZoneOffset.UTC, now.getOffset());
    }
}
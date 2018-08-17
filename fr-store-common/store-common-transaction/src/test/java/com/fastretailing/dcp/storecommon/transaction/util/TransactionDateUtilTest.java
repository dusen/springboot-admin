/**
 * @(#)TransactionDateUtilTest.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.util;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.Test;

/**
 * TransactionDateUtil test class.
 */
public class TransactionDateUtilTest {

    /**
     * test createCurrentDateMinute method.
     * 
     * @see TransactionDateUtil#createCurrentDateMinute()
     */
    @Test
    public void testCreateCurrentDateMinute() {

        // compare current date minute
        Instant currentDate = Instant.ofEpochMilli(System.currentTimeMillis());
        DateTimeFormatter MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String currentDateMinute = MINUTE_FORMATTER
                .format(LocalDateTime.ofInstant(currentDate, ZoneId.systemDefault()));

        // target current date minute
        String targetCurrentDateMinute = TransactionDateUtil.createCurrentDateMinute();

        assertEquals(currentDateMinute.compareTo(targetCurrentDateMinute), 0);
    }

    /**
     * test formatDateMinute method.
     * 
     * @see TransactionDateUtil#formatDateMinute()
     */
    @Test
    public void testFormatDateMinute() {

        // compare current date minute
        String currentDateMinute = "201801170127";

        // target current date minute
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.set(2018, 0, 17, 1, 27, 35);
        String targetCurrentDateMinute = TransactionDateUtil
                .formatDateMinute(targetCalendar.toInstant());

        assertEquals(currentDateMinute.compareTo(targetCurrentDateMinute), 0);
    }

}

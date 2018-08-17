/**
 * @(#)TransactionDateUtil.java
 *
 * Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TransactionDateUtil {

    /** Date and time formatter for status registered minutes. */
    private static final DateTimeFormatter MINUTE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * Gets current date and formats it to yyyyMMddHHmm.
     * 
     * @return
     */
    public static String createCurrentDateMinute() {
        Instant currentDate = Instant.ofEpochMilli(System.currentTimeMillis());
        return MINUTE_FORMATTER
                .format(LocalDateTime.ofInstant(currentDate, ZoneId.systemDefault()));
    }

    /**
     * format the specified date to yyyyMMddHHmm.
     * 
     * @param formattedDate date instant to be formatted.
     * @return
     */
    public static String formatDateMinute(Instant formattedDate) {
        return MINUTE_FORMATTER
                .format(LocalDateTime.ofInstant(formattedDate, ZoneId.systemDefault()));
    }
}

/**
 * @(#)DateTimeFormatterEnum.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.constants;

import java.time.format.DateTimeFormatter;

/**
 * date time formatter Builder enum.
 * @author Fast Retailing
 * @version $Revision$
 */
public enum DateTimeFormatterEnum {

    /**
     * date time formatter: ISO_OFFSET_DATE_TIME.
     * The ISO date-time formatter that formats or parses a date-time with an
     * offset, such as '2011-12-03T10:15:30+01:00'.
     */
    ISO_OFFSET_DATE_TIME(DateTimeFormatter.ISO_OFFSET_DATE_TIME),

    /**
     * date time formatter: ISO_LOCAL_DATE_TIME.
     * The ISO date-time formatter that formats or parses a date-time without
     * an offset, such as '2011-12-03T10:15:30'.
     */
    ISO_LOCAL_DATE_TIME(DateTimeFormatter.ISO_LOCAL_DATE_TIME),

    /**
     * date time formatter: ISO_ZONED_DATE_TIME.
     * The ISO-like date-time formatter that formats or parses a date-time with
     * offset and zone, such as '2011-12-03T10:15:30+01:00[Europe/Paris]'.
     */
    ISO_ZONED_DATE_TIME(DateTimeFormatter.ISO_ZONED_DATE_TIME),

    /**
     * date time formatter: ISO_DATE_TIME.
     * The ISO-like date-time formatter that formats or parses a date-time with
     * the offset and zone if available, such as '2011-12-03T10:15:30',
     * '2011-12-03T10:15:30+01:00' or '2011-12-03T10:15:30+01:00[Europe/Paris]'.
     */
    ISO_DATE_TIME(DateTimeFormatter.ISO_DATE_TIME);

    /**
     * log level.
     */
    private DateTimeFormatter dateTimeFormatter;

    /**
     * constructor.
     * @param dateTimeFormatter dateTimeFormatter
     */
    DateTimeFormatterEnum(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    /**
     * get date time formatter.
     * @return DateTimeFormatter
     */
    public DateTimeFormatter getFormatter() {
        return dateTimeFormatter;
    }
}

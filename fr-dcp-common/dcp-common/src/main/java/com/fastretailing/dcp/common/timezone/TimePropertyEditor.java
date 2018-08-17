/**
 * @(#)TimePropertyEditor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
  * TimePropertyEditor.<br>
 *     the property editor of type LocalTime <br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class TimePropertyEditor extends PropertyEditorSupport {

    /**
     * get the formatted time of UTC.
     * @return time of UTC
     */
    @Override
    public String getAsText() {
        LocalTime localDateTime = (LocalTime)getValue();
        if (localDateTime == null) {
            return  null;
        } else {
            return localDateTime.atOffset(ZoneOffset.UTC).toString();
        }
    }

    /**
     * set the value from string.
     * @param time string
     */
    @Override
    public void setAsText(String time) {
        if (!StringUtils.isEmpty(time)) {
            try {
                // convert the date time to UTC
                String dateTime = LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "T" + time;
                ZonedDateTime zonedDateTime =
                        ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                LocalDateTime localDateTime =
                        zonedDateTime.toInstant()
                                .atZone(ZoneId.of("UTC", ZoneId.SHORT_IDS))
                                .toLocalDateTime();
                setValue(localDateTime.toLocalTime());
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
    }
}

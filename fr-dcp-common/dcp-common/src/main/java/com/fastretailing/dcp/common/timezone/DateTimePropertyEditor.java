/**
 * @(#)DateTimePropertyEditor.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
  * DateTimePropertyEditor.<br>
 *     the property editor of type of LocalDateTime <br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class DateTimePropertyEditor extends PropertyEditorSupport {

    /**
     * get the formatted date time of UTC.
     * @return date time of UTC
     */
    @Override
    public String getAsText() {
        LocalDateTime dateTime = (LocalDateTime) getValue();
        if (dateTime == null) {
            return null;
        } else {
            return dateTime.atOffset(ZoneOffset.UTC).toString();
        }
    }

    /**
     * set the value from string.
     * @param dateTime string
     */
    @Override
    public void setAsText(String dateTime) {
        if (!StringUtils.isEmpty(dateTime)) {
            // convert the date time to UTC
            try {
                ZonedDateTime zonedDateTime =
                        ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                LocalDateTime localDateTime =
                        zonedDateTime.toInstant()
                                .atZone(ZoneId.of("UTC", ZoneId.SHORT_IDS))
                                .toLocalDateTime();
                setValue(localDateTime);
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
    }
}

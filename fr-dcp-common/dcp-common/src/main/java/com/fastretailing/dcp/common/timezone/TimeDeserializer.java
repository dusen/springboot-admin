/**
 * @(#)TimeDeserializer.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeDeserializer.<br>
 *     the deserializer of type LocalDateTime <br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class TimeDeserializer extends JsonDeserializer<LocalTime> {

    /**
     * Do deserialize.
     * @param parser parser
     * @param context context
     * @return LocalTime
     * @throws IOException IOException
     */
    public LocalTime deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        String text = parser.getText();
        if (StringUtils.isEmpty(text)) {
            return null;
        } else {
            // convert the time to UTC
            String dateTime = LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "T" + text;
            ZonedDateTime zonedDateTime =
                    ZonedDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return zonedDateTime.toInstant()
                    .atZone(ZoneId.of("UTC", ZoneId.SHORT_IDS))
                    .toLocalTime();
        }
    }
}

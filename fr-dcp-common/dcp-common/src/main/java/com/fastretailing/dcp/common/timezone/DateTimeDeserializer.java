/**
 * @(#)DateTimeDeserializer.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * DateTimeDeserializer.<br>
 *     the deserializer of type LocalDateTime <br>
 * @author Fast Retailing
 * @version $Revision$
 */
public class DateTimeDeserializer extends JsonDeserializer<LocalDateTime>
        implements ContextualDeserializer {

    /**
     * datetime format string.
     */
    private String datetimeFormatString;

    /**
     * Constructor.
     */
    public DateTimeDeserializer() {
        super();
    }

    /**
     * Constructor.
     */
    public DateTimeDeserializer(String datetimeFormatString) {
        this.datetimeFormatString = datetimeFormatString;
    }

    /**
     * Do deserialize.
     * @param parser parser
     * @param context context
     * @return LocalDateTime
     * @throws IOException IOException
     */
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        String text = parser.getText();
        if (StringUtils.isEmpty(text)) {
            return  null;
        } else {
            if(StringUtils.isEmpty(datetimeFormatString)){
                // convert to UTC
                ZonedDateTime zonedDateTime =
                        ZonedDateTime.parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                return  zonedDateTime.toInstant()
                        .atZone(ZoneId.of("UTC", ZoneId.SHORT_IDS))
                        .toLocalDateTime();
            }else {
                // convert to UTC
                ZonedDateTime zonedDateTime =
                        ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(datetimeFormatString));
                return  zonedDateTime.toInstant()
                        .atZone(ZoneId.of("UTC", ZoneId.SHORT_IDS))
                        .toLocalDateTime();
            }
        }
    }

    /**
     *Create contextual deserializer for {@link LocalDateTime}.<br>
     *     <p>
     *         If destination bean has been annotated with {@link DateTimeFormat},
     *         Created deserializer by this method will use annotated format to deserialize {@link LocalDateTime}.
     *     </p>
     */
    @Override
    public JsonDeserializer<LocalDateTime> createContextual(DeserializationContext ctxt,
                                                            BeanProperty property) throws JsonMappingException {

        DateTimeFormat formatAnnotation = null;
        String formatString = null;

        if (property != null) {
            formatAnnotation = property.getAnnotation(DateTimeFormat.class);
        }

        if (formatAnnotation != null) {
            formatString = formatAnnotation.pattern();
        }

        return new DateTimeDeserializer(formatString);
    }
}

/**
 * @(#)DateTimeSerializer.java
 *
 *                             Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * DateTimeSerializer.<br>
 * the Serializer of type LocalDateTime <br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class DateTimeSerializer extends StdSerializer<LocalDateTime>
        implements ContextualSerializer {

    /**
     * datetime format string.
     */
    private String datetimeFormatString;

    /**
     * Constructor.
     */
    public DateTimeSerializer() {
        super(LocalDateTime.class);
    }

    /**
     * Constructor.
     */
    public DateTimeSerializer(String datetimeFormatString) {
        super(LocalDateTime.class);
        this.datetimeFormatString = datetimeFormatString;
    }

    /**
     * Do serialize.
     *
     * @param value the value
     * @param generator generator
     * @param provider provider
     * @throws IOException IOException
     */
    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        if (value == null) {
            generator.writeNull();
        } else {
            if (StringUtils.isEmpty(datetimeFormatString)) {
                generator.writeString(value.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeFormatString);
                generator.writeString(formatter.format(value.atOffset(ZoneOffset.UTC)));
            }
        }
    }

    /**
     *Create contextual serializer for {@link LocalDateTime}.<br>
     *     <p>
     *         If source bean has been annotated with {@link DateTimeFormat},
     *         Created serializer by this method will use annotated format to serialize {@link LocalDateTime}.
     *     </p>
     */
    @Override
    public JsonSerializer<LocalDateTime> createContextual(SerializerProvider serializer,
            BeanProperty property) throws JsonMappingException {

        DateTimeFormat formatAnnotation = null;
        String formatString = null;

        if (property != null) {
            formatAnnotation = property.getAnnotation(DateTimeFormat.class);
        }

        if (formatAnnotation != null) {
            formatString = formatAnnotation.pattern();
        }

        return new DateTimeSerializer(formatString);
    }

}

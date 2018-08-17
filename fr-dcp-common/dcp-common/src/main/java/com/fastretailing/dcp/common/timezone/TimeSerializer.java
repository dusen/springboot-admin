/**
 * @(#)TimeSerializer.java
 *
 *                         Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.timezone;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneOffset;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * TimeSerializer.<br>
 * the serializer of type LocalTime <br>
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class TimeSerializer extends StdSerializer<LocalTime> {

    /**
     *
     */
    private static final long serialVersionUID = 8859589372131061694L;

    /**
     * Constructor.
     */
    public TimeSerializer() {
        super(LocalTime.class);
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
    public void serialize(LocalTime value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        if (value == null) {
            generator.writeNull();
        } else {
            generator.writeString(value.atOffset(ZoneOffset.UTC).toString());
        }
    }

}

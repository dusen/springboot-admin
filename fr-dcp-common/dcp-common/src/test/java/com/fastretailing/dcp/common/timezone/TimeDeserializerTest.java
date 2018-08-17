package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TimeDeserializerTest {

    @Mock
    private UTF8StreamJsonParser parser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deserializeCase1() throws IOException {
        TimeDeserializer deserializer = new TimeDeserializer();
        Mockito.when(parser.getText()).thenReturn(null);
        LocalTime localTime =  deserializer.deserialize(parser, null);
        assertThat(localTime, is(nullValue()));
    }

    @Test
    public void deserializeCase2() throws IOException {
        TimeDeserializer deserializer = new TimeDeserializer();
        Mockito.when(parser.getText()).thenReturn("03:04:05-06:00");
        LocalTime localTime =  deserializer.deserialize(parser, null);
        assertThat(localTime, is(LocalTime.of(9, 4, 5)));
    }

    @Test
    public void deserializeCase3()  {
        try {
            TimeDeserializer deserializer = new TimeDeserializer();
            Mockito.when(parser.getText()).thenReturn("11103:04:05-06:00");
            deserializer.deserialize(parser, null);
        } catch (Exception ex) {
            assertThat(ex.getClass().getSimpleName(), is("DateTimeParseException"));
        }
    }
}

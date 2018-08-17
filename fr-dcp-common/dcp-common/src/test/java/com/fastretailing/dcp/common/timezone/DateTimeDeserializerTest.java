package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class DateTimeDeserializerTest {

    @Mock
    private UTF8StreamJsonParser parser;

    @Mock
    private BeanProperty property;

    @Mock
    private DateTimeFormat dateTimeFormat;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deserializeCase1() throws IOException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        Mockito.when(parser.getText()).thenReturn(null);
        LocalDateTime localDateTime =  deserializer.deserialize(parser, null);
        assertThat(localDateTime, is(nullValue()));
    }

    @Test
    public void deserializeCase2() throws IOException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        Mockito.when(parser.getText()).thenReturn("2016-01-02T03:04:05-06:00");
        LocalDateTime localDateTime =  deserializer.deserialize(parser, null);
        assertThat(localDateTime, is(LocalDateTime.of(2016, 1, 2, 9, 4,5)));
    }

    @Test
    public void deserializeCase3()  {
        try {
            DateTimeDeserializer deserializer = new DateTimeDeserializer();
            Mockito.when(parser.getText()).thenReturn("2016-01-02T111103:04:05-06:00");
            deserializer.deserialize(parser, null);
        } catch (Exception ex) {
            assertThat(ex.getClass().getSimpleName(), is("DateTimeParseException"));
        }
    }

    @Test
    public void deserializeCase4() throws IOException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer("yyyy-MM-dd'T'HH:mm:ssZ");
        Mockito.when(parser.getText()).thenReturn("2016-01-02T03:04:05+0000");
        LocalDateTime localDateTime =  deserializer.deserialize(parser, null);
        assertThat(localDateTime, is(LocalDateTime.of(2016, 1, 2, 3, 4,5)));
    }

    @Test
    public void deserializeCase5() throws IOException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        Mockito.when(parser.getText()).thenReturn("2016-01-02T03:04:05Z");
        LocalDateTime localDateTime =  deserializer.deserialize(parser, null);
        assertThat(localDateTime, is(LocalDateTime.of(2016, 1, 2, 3, 4,5)));
    }

    @Test
    public void createContextualCase1() throws IOException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        JsonDeserializer<LocalDateTime> result =  deserializer.createContextual(null, property);
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void createContextualCase2() throws IOException {
        DateTimeDeserializer deserializer = new DateTimeDeserializer();
        Mockito.when(property.getAnnotation(DateTimeFormat.class)).thenReturn(dateTimeFormat);
        Mockito.when(dateTimeFormat.pattern()).thenReturn("yyyy-MM-dd");
        JsonDeserializer<LocalDateTime> result =  deserializer.createContextual(null, property);
        assertThat(result, is(notNullValue()));
    }
}

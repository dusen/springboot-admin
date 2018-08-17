package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class DateTimeSerializerTest {

    @Mock
    private UTF8JsonGenerator generator;

    @Mock
    private BeanProperty property;

    @Mock
    private DateTimeFormat dateTimeFormat;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void serializeCase1() throws IOException{
        DateTimeSerializer serializer = new DateTimeSerializer();
        try {
            serializer.serialize(null, generator, null);
        } catch (Exception e) {
        }
        verify(generator).writeNull();
    }

    @Test
    public void serializeCase2() throws IOException{
        DateTimeSerializer serializer = new DateTimeSerializer();
        LocalDateTime dateTime = LocalDateTime.of(2016, 10, 12, 13, 14, 15);
        try {
            serializer.serialize(dateTime, generator, null);
        } catch (Exception e) {
        }
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(generator).writeString(captor.capture());
        assertThat(captor.getValue(), is("2016-10-12T13:14:15Z"));
    }

    @Test
    public void serializeCase3() throws IOException{
        DateTimeSerializer serializer = new DateTimeSerializer("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime dateTime = LocalDateTime.of(2016, 10, 12, 13, 14, 15);
        try {
            serializer.serialize(dateTime, generator, null);
        } catch (Exception e) {
        }
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(generator).writeString(captor.capture());
        assertThat(captor.getValue(), is("2016-10-12T13:14:15Z"));
    }

    @Test
    public void serializeCase4() throws IOException{
        DateTimeSerializer serializer = new DateTimeSerializer("yyyy-MM-dd'T'HH:mm:ssZ");
        LocalDateTime dateTime = LocalDateTime.of(2016, 10, 12, 13, 14, 15);
        try {
            serializer.serialize(dateTime, generator, null);
        } catch (Exception e) {
        }
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(generator).writeString(captor.capture());
        assertThat(captor.getValue(), is("2016-10-12T13:14:15+0000"));
    }

    @Test
    public void createContextualCase1() throws IOException {
        DateTimeSerializer Serializer = new DateTimeSerializer();
        JsonSerializer<LocalDateTime> result =  Serializer.createContextual(null, property);
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void createContextualCase2() throws IOException {
        DateTimeSerializer Serializer = new DateTimeSerializer();
        Mockito.when(property.getAnnotation(DateTimeFormat.class)).thenReturn(dateTimeFormat);
        Mockito.when(dateTimeFormat.pattern()).thenReturn("yyyy-MM-dd");
        JsonSerializer<LocalDateTime> result =  Serializer.createContextual(null, property);
        assertThat(result, is(notNullValue()));
    }

}

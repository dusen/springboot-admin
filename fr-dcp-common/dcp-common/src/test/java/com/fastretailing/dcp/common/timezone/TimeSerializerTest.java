package com.fastretailing.dcp.common.timezone;

import com.fasterxml.jackson.core.json.UTF8JsonGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class TimeSerializerTest {

    @Mock
    private UTF8JsonGenerator generator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void serializeCase1() throws IOException{
        TimeSerializer serializer = new TimeSerializer();
        try {
            serializer.serialize(null, generator, null);
        } catch (Exception e) {
        }
        verify(generator).writeNull();
    }

    @Test
    public void serializeCase2() throws IOException{
        TimeSerializer serializer = new TimeSerializer();
        LocalTime dateTime = LocalTime.of(13, 14, 15);
        try {
            serializer.serialize(dateTime, generator, null);
        } catch (Exception e) {
        }
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(generator).writeString(captor.capture());
        assertThat(captor.getValue(), is("13:14:15Z"));
    }

}

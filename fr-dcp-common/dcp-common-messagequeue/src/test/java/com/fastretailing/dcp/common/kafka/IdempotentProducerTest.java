/**
 * @(#)IdempotentProducerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test class of IdempotentProducer.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class IdempotentProducerTest {

    @InjectMocks
    IdempotentProducer target;
    
    @Mock
    KafkaTemplate<String, String> template;
    
    @Mock
    SettableListenableFuture<SendResult<String, String>> future;

    @Mock
    SendResult<String, String> sendResult;

    @Spy
    private OmsKafkaHelper helper;

    @Captor
    private ArgumentCaptor<String> topic;
    @Captor
    private ArgumentCaptor<String> key;
    @Captor
    private ArgumentCaptor<String> message;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void send() throws Exception {
        
        ReflectionTestUtils.setField(target, "template", template);
        
        when(template.send(anyString(),anyString(),anyString())).thenReturn(future);
        when(future.get()).thenReturn(sendResult);
        target.send("test", "testkey", "this is a test message");
        
        verify(template).send(topic.capture(), key.capture(), message.capture());
        MatcherAssert.assertThat(topic.getValue(),CoreMatchers.is("test"));
        MatcherAssert.assertThat(key.getValue(),CoreMatchers.is("testkey"));
        MatcherAssert.assertThat(message.getValue(),CoreMatchers.is("\"this is a test message\""));
        
    }

    @Test
    public void send1() throws Exception {

        ReflectionTestUtils.setField(target, "template", template);

        when(template.send(anyString(), anyString(), anyString())).thenReturn(future);
        when(future.get()).thenReturn(sendResult);
        when(sendResult.getRecordMetadata()).thenReturn(new RecordMetadata(new TopicPartition("", 0), 0, 0, 0, 0, 0, 0));
        target.send("test", "testkey", "this is a test message");

        verify(template).send(topic.capture(), key.capture(), message.capture());
        MatcherAssert.assertThat(topic.getValue(),CoreMatchers.is("test"));
        MatcherAssert.assertThat(key.getValue(),CoreMatchers.is("testkey"));
        MatcherAssert.assertThat(message.getValue(),CoreMatchers.is("\"this is a test message\""));

    }
}

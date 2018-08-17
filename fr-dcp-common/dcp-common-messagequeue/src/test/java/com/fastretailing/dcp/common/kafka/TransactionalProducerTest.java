/**
 * @(#)TransactionalProducerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.fastretailing.dcp.common.kafka.TransactionalProducer;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.SettableListenableFuture;

/**
 * test class of TransactionalProducer.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
public class TransactionalProducerTest {

    @InjectMocks
    TransactionalProducer target;
    
    @Mock
    KafkaTemplate<String, String> template;
    
    @Mock
    SettableListenableFuture<SendResult<String, String>> future;
    
    @Mock
    SendResult<String, String> sendResult;
    
    @Captor
    private ArgumentCaptor<String> topic;
    @Captor
    private ArgumentCaptor<String> key;
    @Captor
    private ArgumentCaptor<String> message;

    @Spy
    private OmsKafkaHelper helper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void send() throws Exception {
        
        ReflectionTestUtils.setField(target, "template", template);
        
        when(template.send(anyString(),anyString(),anyString())).thenReturn(future);
        when(future.get()).thenReturn(sendResult);
        List<String> messages = new ArrayList<>();
        messages.add("test message 1");
        messages.add("test message 2");
        target.send("test", "testkey", messages);
        
        verify(template,times(2)).send(topic.capture(), key.capture(), message.capture());
        MatcherAssert.assertThat(topic.getValue(),CoreMatchers.is("test"));
        MatcherAssert.assertThat(key.getValue(),CoreMatchers.is("testkey"));
        List<String> sendMessages = message.getAllValues();
        MatcherAssert.assertThat(sendMessages.get(0),CoreMatchers.is("\"test message 1\""));
        MatcherAssert.assertThat(sendMessages.get(1),CoreMatchers.is("\"test message 2\""));
        
    }
}

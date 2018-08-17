/**
 * @(#)CommonConsumerTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.fastretailing.dcp.common.exception.InitializeUncompletedException;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ApiException;
import org.apache.kafka.common.errors.WakeupException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.retry.TerminatedRetryException;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.NoTransactionException;

import java.lang.reflect.Field;
import java.time.zone.ZoneRulesException;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * test class of CommonConsumer.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@SuppressWarnings("restriction")
public class CommonConsumerTest {

    @Mock
    protected WorkerInterface<String> worker;

    @Mock
    protected Consumer<String,String> mockConsumer;

    @Mock
    protected Producer<String, String> mockProducer;

    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @Captor
    private ArgumentCaptor<String> message;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DeadLetterProducer deadLetterProducer;

    @Mock
    private DeadLetterProducer deadLetterProducer1;

    private KafkaRetryTemplateFactory kafkaRetryTemplateFactory;

    @Mock
    private KafkaRetryTemplateFactory retryTemplateFactory;

    @Mock
    private RetryTemplate retryTemplate;

    /**
     * Initialize test.<br>
     * @throws Exception any exception
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        Mockito.when(mockAppender.getName()).thenReturn("consoleLog");
        root.addAppender(mockAppender);

        deadLetterProducer = new DeadLetterProducer("test01");
        kafkaRetryTemplateFactory = new KafkaRetryTemplateFactory();

        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", mockProducer);
        ReflectionTestUtils.setField(deadLetterProducer, "deadLetterRetryCount", 1);
        ReflectionTestUtils.setField(deadLetterProducer, "deadLetterRetryInterval", 100);
        ReflectionTestUtils.setField(deadLetterProducer, "retryTemplateFactory", kafkaRetryTemplateFactory);

    }

    @Test
    public void setPartitionsException() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();
        thrown.expect(JobParametersInvalidException.class);
        target.setPartitions("0,n");
    }

    @Test
    public void consumeMessagesPartitionSet() throws Exception {

        CommonConsumerEx target = new CommonConsumerEx();
        target.setPartitions("0,9");

        Field field = target.getClass().getSuperclass().getDeclaredField("partitions");
        field.setAccessible(true);

        List<Integer> actual = (List<Integer>) field.get(target);

        MatcherAssert.assertThat(actual.size(), CoreMatchers.is(2));
        MatcherAssert.assertThat(actual.get(0), CoreMatchers.is(0));
        MatcherAssert.assertThat(actual.get(1), CoreMatchers.is(9));
    }

    @Test
    public void consumeMessagesValidateFalse() throws Exception {

        CommonConsumerEx target = new CommonConsumerEx();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record =
                new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();

        // assert
        Mockito.verify(mockAppender).doAppend(Mockito.argThat((ILoggingEvent argument) ->
                argument.getFormattedMessage().contains(
                        "Receive an error record[\"test message\"] from topic[test], partition[1].")
        ));
        MatcherAssert.assertThat(record.value(),
                CoreMatchers.is(producer.history().get(0).value()));

        // TODO
    }
    
    @Test
    public void consumeMessagesSendDeadLetterException() throws Exception {

        CommonConsumerEx target = new CommonConsumerEx();
        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        when(mockProducer.send(any())).thenThrow(new KafkaException("send dead letter error1"));
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", mockProducer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();
        // assert
        Mockito.verify(mockAppender).doAppend(Mockito.argThat((ILoggingEvent argument) ->
                argument.getFormattedMessage().contains(
                        "An Exception Occurred while transferring message[\"test message\"] to dead letter topic[test01].")
        ));

    }
    
    @Test
    public void consumeMessagesDoWorkFalse() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(true);
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record =new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();

        // assert
        MatcherAssert.assertThat(record.value(), CoreMatchers.is(producer.history().get(0).value()));

    }

    @Test
    public void test6() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(true);
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);
        doThrow(new NoTransactionException("")).when(worker).doWork(any(), anyString(), anyMap());
        doThrow(new NoTransactionException("")).when(worker).doWorkFail(any(), anyString(), anyMap());

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record =new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();

        // assert
        MatcherAssert.assertThat(record.value(), CoreMatchers.is(producer.history().get(0).value()));

    }

    @Test
    public void test5() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(true);
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);
        doThrow(new NoTransactionException("")).when(worker).doWork(any(), anyString(), anyMap());
        doThrow(new NoTransactionException("")).when(worker).doWorkFail(any(), anyString(), anyMap());
        doThrow(new ZoneRulesException("")).when(worker).validate(any(), anyString(), anyMap());
        doThrow(new ConcurrentModificationException()).when(worker).validateFail(any(), anyString(), anyMap());

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record =new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", retryTemplateFactory);

        ReflectionTestUtils.setField(deadLetterProducer, "retryTemplateFactory", retryTemplateFactory);

        Mockito.when(retryTemplateFactory.buildTemplate(anyInt(), anyInt())).thenReturn(new RetryTemplate());

        Mockito.when(retryTemplateFactory.buildTemplate(anyInt(), anyInt())).thenThrow(new TerminatedRetryException(""));

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();

    }

    @Test
    public void test1() throws Exception {
        CommonConsumerEx1 target = new CommonConsumerEx1();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record =new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();

        // assert
        MatcherAssert.assertThat(record.value(), CoreMatchers.is(producer.history().get(0).value()));

    }

    @Test
    public void test2() throws Exception {
        CommonConsumerEx1 target = new CommonConsumerEx1();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 10000L);
        ReflectionTestUtils.setField(target, "sessionTimeoutMsConfig", 5000);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenThrow(new RuntimeException());
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        ReflectionTestUtils.setField(target, "consumer", null);


        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        target.initialize();

    }

    @Test
    public void test3() throws Exception {

        CommonConsumerEx1 target = new CommonConsumerEx1();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 10000L);
        ReflectionTestUtils.setField(target, "sessionTimeoutMsConfig", 5000);
        ReflectionTestUtils.setField(target, "partitions", Arrays.asList(10, 20));
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenThrow(new NoTransactionException(""));
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        ReflectionTestUtils.setField(target, "consumer", null);


        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        target.initialize();

    }

    @Test(expected = InitializeUncompletedException.class)
    public void test4() throws Exception {

        CommonConsumerEx1 target = new CommonConsumerEx1();

        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 10000L);
        ReflectionTestUtils.setField(target, "sessionTimeoutMsConfig", 5000);
        ReflectionTestUtils.setField(target, "partitions", Arrays.asList(10, 20));
        ReflectionTestUtils.setField(target, "topic", "");
        ReflectionTestUtils.setField(target, "groupId", "");
        ReflectionTestUtils.setField(target, "typeParameterClass", null);
        ReflectionTestUtils.setField(target, "deadLetterProducer", null);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenThrow(new RuntimeException());
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(false);

        // set consumer
        ReflectionTestUtils.setField(target, "consumer", null);


        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        target.initialize();

    }

    @Test
    public void consumeMessagesSuccess() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();
        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(true);
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(true);

        // set consumer
        MockConsumer<String, String> consumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
        List<TopicPartition> partitions = new ArrayList<>();
        TopicPartition tp = new TopicPartition("test", 1);
        partitions.add(tp);
        consumer.assign(partitions);
        Map<TopicPartition, Long> newOffsets = new HashMap<>();
        newOffsets.put(tp, 0L);
        consumer.updateBeginningOffsets(newOffsets);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        consumer.addRecord(record);
        ReflectionTestUtils.setField(target, "consumer", consumer);

        // set producer
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        // run
        Thread t = new Thread(target);
        t.start();
        Thread.sleep(5000);
        t.interrupt();

        // assert
        Mockito.verify(mockAppender).doAppend(Mockito.argThat((ILoggingEvent argument) ->
                argument.getFormattedMessage().contains(
                        "1 records has processed.")
        ));

    }

    @Test
    public void consumeMessagesCommitException() throws Exception {

        CommonConsumerEx target = new CommonConsumerEx();
        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "consumer", mockConsumer);
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);
        ReflectionTestUtils.setField(target, "worker", worker);
        when(worker.validate(any(), anyString(), anyMap())).thenReturn(true);
        when(worker.doWork(any(), anyString(), anyMap())).thenReturn(true);

        TopicPartition tp = new TopicPartition("test", 1);
        ConsumerRecord<String, String> record = new ConsumerRecord<>("test", 1, 1, "key", "\"test message\"");
        Map<TopicPartition, List<ConsumerRecord<String, String>>> recordsMap = new HashMap<>();
        recordsMap.put(tp,Arrays.asList(record));
        ConsumerRecords<String, String> records = new ConsumerRecords(recordsMap);
        when(mockConsumer.poll(anyLong())).thenReturn(records);
        doThrow(new KafkaException("a KafkaException")).when(mockConsumer).commitSync();

        thrown.expect(KafkaException.class);

        target.consumeMessages();
    }

    @Test
    public void consumeMessagesWakeupException() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();
        ReflectionTestUtils.setField(target, "worker", worker);
        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "consumer", mockConsumer);
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);
        Exception ex = new WakeupException();
        when(mockConsumer.poll(anyLong())).thenThrow(ex);

        thrown.expect(WakeupException.class);
        target.consumeMessages();
    }
    
    @Test
    public void consumeMessagesException() throws Exception {
        CommonConsumerEx target = new CommonConsumerEx();
        ReflectionTestUtils.setField(target, "worker", worker);
        ReflectionTestUtils.setField(target, "servers", Arrays.asList("localhost:9092"));
        ReflectionTestUtils.setField(target, "pollTimeout", 100L);
        ReflectionTestUtils.setField(target, "consumer", mockConsumer);
        MockProducer<String, String> producer = new MockProducer<>(true, null, null);
        ReflectionTestUtils.setField(deadLetterProducer, "kafkaProducer", producer);
        ReflectionTestUtils.setField(target, "deadLetterProducer", deadLetterProducer);
        ReflectionTestUtils.setField(target, "retryTemplateFactory", kafkaRetryTemplateFactory);

        when(mockConsumer.poll(anyLong())).thenThrow(new ApiException("an ApiException"));

        thrown.expect(ApiException.class);
        target.consumeMessages();
    }

    static class CommonConsumerEx extends CommonConsumer<String> implements Runnable {

        public CommonConsumerEx() {
            super("test", "testGroup", String.class, true);
        }

        public void run() {
            try {
                this.consumeMessages();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class CommonConsumerEx1 extends CommonConsumer<Integer> implements Runnable {

        public CommonConsumerEx1() {
            super("test", "testGroup", Integer.class, true);
        }

        public void run() {
            try {
                this.consumeMessages();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

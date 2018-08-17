/**
 * @(#)OmsWebKafkaDataSourceTestBase.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.junit.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * TestBase for KAFKA.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public class OmsWebKafkaDataSourceTestBase extends OmsWebDataSourceTestBase {

    /** embedded KAFKA server. */
    @Autowired
    protected KafkaEmbedded embeddedKafka;

    /** A KAFKA template. */
    protected static KafkaTemplate<String, String> template;

    /** A KAFKA Consumer. */
    protected static Consumer<String, String> consumer;
    
    /**
     * Before method.<br>
     * When writing tests, it is common to find that several tests need similar
     * objects created before they can run.
     * @param topics topic name
     * @throws Exception any exception
     */
    public void setUp(String... topics) throws Exception {
        super.setUp();
        if (topics == null || topics.length == 0) {
            throw new IllegalArgumentException("At least one topic is necessary");
        }
        // create KAFKA template
        if (template == null) {
            Map<String, Object> senderProps =
                    KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
            senderProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            ProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(senderProps);
            template = new KafkaTemplate<>(pf);
        }

        // create consumer
        if (consumer == null) {
            Map<String, Object> consumerProps =
                    KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka);
            consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringDeserializer");
            ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
            consumer = cf.createConsumer();
            embeddedKafka.consumeFromEmbeddedTopics(consumer, topics);
        }
    }
    
    /**
     * Create a mock producer for test.
     * 
     * @param producerType
     *            IdempotentProducer or TransactionalProducer
     * @return a mock of the producer
     * @throws Exception
     *             any exception
     */
    protected <T> T createMockProducer(Class<T> producerType) throws Exception {
        T result = producerType.newInstance();
        ReflectionTestUtils.setField(result, "template", template);
        return result;
    }
    
    /**
     * Get record from embedded KAFKA server.
     * 
     * @param recordType
     *            record's java type
     * @return records from embedded kafka server
     * @throws IOException any IOException
     */
    protected <T> List<T> getRecords(Class<T> recordType) throws IOException {
        List<T> result = new ArrayList<>();
        ConsumerRecords<String, String> records = KafkaTestUtils
                .getRecords(consumer);
        for (ConsumerRecord<String, String> record : records) {
            result.add(readJsonToBean(record.value(), recordType));
        }
        consumer.commitSync();
        return result;
    }
    

    /**
     * After Class.<br>
     * release kafka resources.
     */
    @AfterClass
    public static void destorykafka() {
        template = null;
        consumer.close();
        consumer = null;
    }
}

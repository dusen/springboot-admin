/**
 * @(#)KafkaConfiguration.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for KAFKA support.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@ConditionalOnMissingBean(IdempotentProducer.class)
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.kafka"})
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfiguration {

    /**
     * Configuration properties.
     */
    @Autowired
    private KafkaProperties kafkaProperties;

    /**
     * Producer Factory instance.
     */
    private ProducerFactory<String, String> transactionProducerFactory = null;

    /**
     * Create an initial map of producer properties.
     * 
     * @return the producer properties map
     */
    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getAcks());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getLingerMs());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProperties.getEnableIdempotence());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getBufferMemory());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProperties.getRequestTimeoutMs());
        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG,
                kafkaProperties.getTransactionTimeoutMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    /**
     * Create an producerFactory instance.
     * 
     * @return producer factory instance
     */
    private ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Create a transactionProducerFactory instance.
     * 
     * @return producer factory instance
     */
    private ProducerFactory<String, String> transactionProducerFactory() {
        if (this.transactionProducerFactory == null) {
            DefaultKafkaProducerFactory<String, String> producerFactory =
                    new DefaultKafkaProducerFactory<>(producerConfigs());
            producerFactory.setTransactionIdPrefix(kafkaProperties.getTransactionIdPrefix());
            this.transactionProducerFactory = producerFactory;
        }
        return this.transactionProducerFactory;
    }

    /**
     * Create a KafkaTransactionManager instance.
     * 
     * @return a KafkaTransactionManager instance
     */
    @Bean("kafkaTransactionManager")
    public KafkaTransactionManager<String, String> kafkaTransactionManager() {
        return new KafkaTransactionManager<>(transactionProducerFactory());
    }

    /**
     * Create a KafkaTemplate instance.
     * 
     * @return a KafkaTemplate instance
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(), true);
    }

    /**
     * Create a KafkaTemplate instance with name transactionKafkaTemplate.
     * 
     * @return a KafkaTemplate instance
     */
    @Bean(name = "transactionKafkaTemplate")
    public KafkaTemplate<String, String> transactionKafkaTemplate() {
        return new KafkaTemplate<>(transactionProducerFactory());
    }

    /**
     * Create a IdempotentProducer instance.
     * 
     * @return a IdempotentProducer instance
     */
    @Bean
    public IdempotentProducer idempotentProducer() {
        return new IdempotentProducer();
    }

    /**
     * Create a TransactionalProducer instance.
     * 
     * @return a TransactionalProducer instance
     */
    @Bean
    public TransactionalProducer transactionalProducer() {
        return new TransactionalProducer();
    }
}

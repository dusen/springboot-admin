/**
 * @(#)KafkaProperties.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Configuration properties for Apache Kafka.
 * 
 * @author Fast Retailing
 * @version $Revision$
 */
@Data
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    /**
     * Comma-delimited list of host:port pairs to use for establishing the initial connection to the
     * Kafka cluster.
     */
    private List<String> bootstrapServers;

    /**
     * Number of acknowledgments the producer requires the leader to have received before
     * considering a request complete.
     */
    private String acks;

    /**
     * When greater than zero, enables retrying of failed sends.
     */
    private int retries;

    /**
     * Number of records to batch before sending.
     */
    private int batchSize;

    /**
     * Delay of send,so that sends can be batched together.
     */
    private long lingerMs;

    /**
     * When set to 'true', the producer will ensure that exactly one copy of each message is written
     * in the stream.
     */
    private Boolean enableIdempotence;

    /**
     * Total bytes of memory the producer can use to buffer records waiting to be sent to the
     * server.
     */
    private long bufferMemory;

    /**
     * The transactional.id prefix.
     */
    private String transactionIdPrefix;
    
    /**
     * request timeout configuration.
     */
    private int requestTimeoutMs;
    
    /**
     * transaction timeout configuration.
     */
    private int transactionTimeoutMs;
}

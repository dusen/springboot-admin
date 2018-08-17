/**
 * @(#)IdempotentProducer.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;

/**
 * The idempotent producer.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class IdempotentProducer {

    /**
     * The kafka template.
     */
    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> template;

    /**
     * Kafka's Helper.
     */
    @Autowired
    private OmsKafkaHelper helper;

    /**
     * Send the given record.
     * 
     * @param topic The topic the record will be appended to
     * @param key The key that will be included in the record
     * @param message The record contents
     * @return MetaData's Map
     * @throws Exception if Kafka producer threw any exception
     */
    public Map<OmsKafkaHelper.MetadataKey, String> send(String topic, String key, Object message)
            throws Exception {

        String jsonStr = JsonUtility.toJson(message);
        log.info("Send record [{}] to topic [{}] with key [{}].", jsonStr, topic, key);
        RecordMetadata metadata = template.send(topic, key, jsonStr).get().getRecordMetadata();

        return helper.convertMetadataToMap(metadata);
    }
}

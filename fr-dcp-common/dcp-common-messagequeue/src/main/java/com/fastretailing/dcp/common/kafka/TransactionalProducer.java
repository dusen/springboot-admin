/**
 * @(#)TransactionalProducer.java
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The transaction producer.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class TransactionalProducer {

    /**
     * The spring kafka template.
     */
    @Autowired
    @Qualifier("transactionKafkaTemplate")
    private KafkaTemplate<String, String> template;

    /**
     * Kafka's Helper.
     */
    @Autowired
    private OmsKafkaHelper helper;
    
    /**
     * sent messages within a transaction.
     * @param topic The topic the record will be appended to
     * @param key The key that will be included in the record
     * @param messages The record contents
     * @throws Exception if Kafka producer threw any exception
     */
    @Transactional(value = "kafkaTransactionManager", rollbackFor = Throwable.class)
    public List<Map<OmsKafkaHelper.MetadataKey, String>> send(
            String topic, String key, List<?> messages
    ) throws Exception {

        List<Map<OmsKafkaHelper.MetadataKey, String>> metadataList = new ArrayList<>();

        List<String> jsonMsgList = new ArrayList<>();
        for (Object msg : messages) {
            jsonMsgList.add(JsonUtility.toJson(msg));
        }

        for (String msg : jsonMsgList) {
            log.info("Send record [{}] to topic [{}] with key [{}].", msg, topic, key);
            RecordMetadata metadata = template.send(topic, key, msg).get().getRecordMetadata();
            metadataList.add(helper.convertMetadataToMap(metadata));
        }

        template.flush();

        return metadataList;
    }
}

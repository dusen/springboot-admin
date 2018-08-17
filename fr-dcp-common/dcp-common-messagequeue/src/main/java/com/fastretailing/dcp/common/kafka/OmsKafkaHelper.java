/**
 * @(#)OmsKafkaHelper.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka's Helper class.<br>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class OmsKafkaHelper {

    /**
     * Kafka Metadata's storage map's key.
     */
    public enum MetadataKey {
        /**
         * Key of Kafka's RecordMetadata's checksum.
         */
        CHECKSUM,

        /**
         * Key of Kafka's RecordMetadata's offset.
         */
        OFFSET,

        /**
         * Key of Kafka's RecordMetadata's timestamp.
         */
        TIMESTAMP,

        /**
         * Key of Kafka's RecordMetadata's partition.
         */
        PARTITION,

        /**
         * Key of Kafka's RecordMetadata's topic.
         */
        TOPIC
    }

    /**
     * Convert RecordMetadata to a HashMap.<br>
     * @param metadata RecordMetadata
     * @return RecordMetadata's HashMap.
     */
    protected Map<MetadataKey, String> convertMetadataToMap(RecordMetadata metadata) {

        Map<MetadataKey, String> metadataMap = new HashMap<>();

        if (metadata == null) {
            metadataMap.put(MetadataKey.CHECKSUM,  StringUtils.EMPTY);
            metadataMap.put(MetadataKey.OFFSET,    StringUtils.EMPTY);
            metadataMap.put(MetadataKey.TIMESTAMP, StringUtils.EMPTY);
            metadataMap.put(MetadataKey.PARTITION, StringUtils.EMPTY);
            metadataMap.put(MetadataKey.TOPIC,     StringUtils.EMPTY);
        } else {
            metadataMap.put(MetadataKey.CHECKSUM,  String.valueOf(metadata.checksum()));
            metadataMap.put(MetadataKey.OFFSET,    String.valueOf(metadata.offset()));
            metadataMap.put(MetadataKey.TIMESTAMP, String.valueOf(metadata.timestamp()));
            metadataMap.put(MetadataKey.PARTITION, String.valueOf(metadata.partition()));
            metadataMap.put(MetadataKey.TOPIC,     metadata.topic());
        }

        return metadataMap;
    }

}

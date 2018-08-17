/**
 * @(#)WorkerInterface.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

/**
 * message processor's interface.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
public interface WorkerInterface<M> {

    /**
     * validate message.
     *
     * @param record record fetched from KAFKA
     * @param t message's java DTO
     * @param context context's map
     * @return validate result
     */
    boolean validate(ConsumerRecord<String, String> record, M t, Map<String, Object> context);

    /**
     * execute when validate is success.
     *
     * @param t message's java DTO
     * @param context context's map
     */
    void validateSuccess(M t, Map<String, Object> context);

    /**
     * execute when validate is failed.
     *
     * @param record record fetched from KAFKA
     * @param t message's java DTO
     * @param context context's map
     */
    void validateFail(ConsumerRecord<String, String> record, M t, Map<String, Object> context);

    /**
     * do business logic.
     *
     * @param record record fetched from KAFKA
     * @param t message's java type
     * @param context context's map
     * @return whether the business processing succeeded or not
     */
    boolean doWork(ConsumerRecord<String, String> record, M t, Map<String, Object> context);

    /**
     * execute when business logic is success.
     *
     * @param record record fetched from KAFKA
     * @param t message's java DTO
     * @param context context's map
     */
    void doWorkSuccess(ConsumerRecord<String, String> record, M t, Map<String, Object> context);

    /**
     * execute when business logic is failed.
     *
     * @param record record fetched from KAFKA
     * @param t      message's java DTO
     * @param context context's map
     */
    void doWorkFail(ConsumerRecord<String, String> record, M t, Map<String, Object> context);
}

/**
 * @(#)DeadLetterProducer.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.TerminatedRetryException;

/**
 * DeadLetter's producer class.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Slf4j
public class DeadLetterProducer {

    /**
     * The number of retry to send dead letter.
     * */
    @Value("${kafka.producer.dead-letter-retries:1}")
    private int deadLetterRetryCount;

    /**
     * The interval of retry to send dead letter.
     * */
    @Value("${kafka.producer.dead-letter-retry-interval:3000}")
    private int deadLetterRetryInterval;

    /**
     * Kafka's retry template's instance.<br>
     */
    @Autowired
    private KafkaRetryTemplateFactory retryTemplateFactory;

    /**
     * Kafka's producer. <br>
     */
    @Autowired
    private Producer<String, String> kafkaProducer;

    /**
     * DeadLetter's callback.<br>
     */
    private DeadLetterCallBack deadLetterCallBack = new DeadLetterCallBack();

    /**
     * DeadLetter's topic.<br>
     */
    private String deadLetterTopic;

    /**
     * Constructor.<br>
     * @param deadLetterTopic DeadLetter's topic
     */
    public DeadLetterProducer(String deadLetterTopic) {
        this.deadLetterTopic = deadLetterTopic;
    }

    /**
     * Send current record.<br>
     * @param record current record
     */
    public void send(ConsumerRecord<String, String> record) {
        deadLetterCallBack.setRecord(record);
        try {
            retryTemplateFactory
                .buildTemplate(deadLetterRetryCount, deadLetterRetryInterval)
                .execute(deadLetterCallBack, deadLetterCallBack);
        } catch (TerminatedRetryException e) {
            // Ignore TerminatedRetryException.
            if (log.isDebugEnabled()) {
                log.debug("Ignore TerminatedRetryException. "
                            + "(Dead letter retry has been manually terminated by a listener.)", e
                );
            }
        }
    }

    /**
     * CallBack for DeadLetter's retry and recovery.<br>
     */
    private class DeadLetterCallBack implements RetryCallback, RecoveryCallback {

        @Setter
        private ConsumerRecord<String, String> record;

        /**
         * Execute an operation with retry semantics. Operations should generally be
         * idempotent, but implementations may choose to implement compensation
         * semantics when an operation is retried.
         * @param context the current retry context.
         * @return the result of the successful operation.
         * @throws Throwable if processing fails
         */
        @Override
        public Object doWithRetry(RetryContext context) throws Throwable {
            return kafkaProducer.send(new ProducerRecord<>(deadLetterTopic, record.value())).get();
        }

        /**
         * Callback for stateful retry after all tries are exhausted.
         *
         * @param context the current retry context
         * @return an Object that can be used to replace the callback result that failed
         * @throws Exception when something goes wrong
         */
        @Override
        public Object recover(RetryContext context) throws Exception {

            log.error(
                    "An Exception Occurred while transferring message["
                    + record.value()
                    + "] to dead letter topic[" + deadLetterTopic + "].",
                    context.getLastThrowable()
            );
            throw new Exception(context.getLastThrowable());
        }

    }

}

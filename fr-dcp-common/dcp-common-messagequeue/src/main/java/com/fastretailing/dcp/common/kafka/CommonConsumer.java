/**
 * @(#)CommonConsumer.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.kafka;

import com.fastretailing.dcp.common.exception.InitializeUncompletedException;
import com.fastretailing.dcp.common.util.JsonUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.IsolationLevel;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.TerminatedRetryException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * <p>
 * message consumer's common logic.
 * </p>
 * This common logic provide the ability to fetch messages from kafka with long polling model.<br>
 * And you can invoke shutdown hook from external thread to stop the long polling.<br>
 * For business logic, you must extend this class and construct your own instances with kafka
 * topic.<br>
 * Then you can use your own consumer to consume messages with your business logic.<br>
 *
 * {@link WorkerInterface}.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@SuppressWarnings("restriction")
@Slf4j
public abstract class CommonConsumer<T extends Serializable>
        implements RetryCallback, RecoveryCallback {

    /** Apache consumer. */
    private Consumer<String, String> consumer = null;

    /** Topic name. */
    private String topic = null;

    /** Indicate whether to send dead letter topic. */
    private boolean enableSendDeadLetter = false;

    /** Partition. */
    private List<Integer> partitions = new ArrayList<>();

    /** Consumer group. */
    private String groupId = null;

    /** The java type of the JSON content string. */
    private Class<T> typeParameterClass = null;

    /** Address of consumer's KAFKA cluster. */
    @Value("#{'${kafka.consumer.bootstrap-servers}'.split(',')}")
    private List<String> servers;

    /** The time spent waiting in poll if data is not available in the buffer. */
    @Value("${kafka.consumer.poll-timeout}")
    private long pollTimeout;

    /** The number of retry to commit message. */
    @Value("${kafka.consumer.commit-retries:1}")
    private int commitRetryCount;

    /** The interval of retry to commit message. */
    @Value("${kafka.consumer.commit-retry-interval:3000}")
    private int commitRetryInterval;

    /** The session's timeout. */
    @Value("${kafka.consumer.session-timeout-ms-config:5000}")
    private int sessionTimeoutMsConfig;

    /** The isolation level. */
    private String isolationLevel = IsolationLevel.READ_UNCOMMITTED.toString().toLowerCase(Locale.ROOT);

    /** Poll interval. */
    @Value("${kafka.consumer.poll-interval:1000}")
    private long pollInterval;

    /** Business worker. */
    @Autowired
    private WorkerInterface<T> worker;

    /** Kafka's RetryTemplate's factory. */
    @Autowired
    private KafkaRetryTemplateFactory retryTemplateFactory;

    /** DeadLetter's producer. **/
    @Autowired
    private DeadLetterProducer deadLetterProducer;

    /**
     * Constructor.
     *
     * @param topic topic name
     * @param groupId consumer group this consumer belongs to
     * @param typeParameterClass the java type of the message string
     * @param enableSendDeadLetter true to send dead letter after received an error message
     */
    public CommonConsumer(
            String topic, String groupId,
            Class<T> typeParameterClass, boolean enableSendDeadLetter) {

        this.topic = topic;
        this.groupId = groupId;
        this.typeParameterClass = typeParameterClass;
        this.enableSendDeadLetter = enableSendDeadLetter;

    }

    /**
     * Constructor.
     *
     * @param topic topic name
     * @param groupId consumer group this consumer belongs to
     * @param typeParameterClass the java type of the message string
     * @param enableSendDeadLetter true to send dead letter after received an error message
     * @param isolationLevel Controls how to read messages written transactionally
     */
    public CommonConsumer(
            String topic, String groupId,
            Class<T> typeParameterClass, boolean enableSendDeadLetter, IsolationLevel isolationLevel) {

        this(topic, groupId, typeParameterClass, enableSendDeadLetter);
        if (!Objects.isNull(isolationLevel)) {
            this.isolationLevel = isolationLevel.toString().toLowerCase(Locale.ROOT);
        }

    }

    /**
     * Split partitions string.
     *
     * @param partitions partitions
     * @throws JobParametersInvalidException job parameters partitions is invalid
     */
    public void setPartitions(String partitions) throws JobParametersInvalidException {
        if (StringUtils.isNoneBlank(partitions)) {
            String[] partitionArray = partitions.split(",", -1);
            for (String partition : partitionArray) {
                if (!NumberUtils.isDigits(partition)) {
                    throw new JobParametersInvalidException(
                        "Job parameter is invalid.[partitions = " + partitions + "]");
                }
                this.partitions.add(Integer.valueOf(partition));
            }
        }
    }

    /**
     * Fetch message(JSON content string) from the topics then process business logic.
     *
     * @throws Exception if KAFKA consumer threw any exception
     */
    public void consumeMessages() throws Exception {

        this.checkKafkaEnvIsReady();

        try {

            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(pollTimeout);
                if (records.count() == 0) {
                    Thread.sleep(pollInterval);
                    continue;
                }

                log.info("Receive {} records.", records.count());

                // Handle new records
                for (ConsumerRecord<String, String> record : records) {

                    logProcessStart(record);

                    Map<String, Object> contextMap = new HashMap<>();

                    T acc = null;
                    try {
                        acc = JsonUtility.toObject(record.value(), typeParameterClass);
                    } catch (Exception e) {
                        // Ignore JSON format error.
                        if (log.isDebugEnabled()) {
                            log.debug("Ignore JSON format error and return null value."
                                        + " JSON String : {}", record.value(), e
                            );
                        }
                    }

                    if (!this.doValidateProcess(record, acc, contextMap)) {
                        if (enableSendDeadLetter) {
                            deadLetterProducer.send(record);
                        }
                        continue;
                    }

                    if (!this.doWorkProcess(record, acc, contextMap)) {
                        if (enableSendDeadLetter) {
                            deadLetterProducer.send(record);
                        }
                        continue;
                    }

                }

                commitMessages();
                log.info("{} records has processed.", records.count());
            }

        } catch (Exception e) {
            log.error("An Exception Occurred while consuming messages.", e);
            throw e;
        }
    }

    /**
     * Do validate process.<br>
     *
     * @param record kafka's record
     * @param acc java bean
     * @param contextMap context map
     * @return process status
     */
    private boolean doValidateProcess(
            ConsumerRecord<String, String> record, T acc, Map<String, Object> contextMap
    ) {

        boolean processIsSuccess = true;

        try {
            if (worker.validate(record, acc, contextMap)) {
                worker.validateSuccess(acc, contextMap);
            } else {
                processIsSuccess = false;
            }
        } catch (RuntimeException e) {
            processIsSuccess = false;
            if (log.isWarnEnabled()) {
                log.warn("Topic[" + this.topic + "] : "
                        + "Execution failed when execute validate() or validateSuccess().", e);
            }

        }

        if (!processIsSuccess) {
            logErrorRecord(record);
            try {
                worker.validateFail(record, acc, contextMap);
            } catch (RuntimeException e) {
                // ignore error because of [processIsSuccess] variable has been set false.
                if (log.isWarnEnabled()) {
                    log.warn("Topic[" + this.topic + "] : "
                            + "Execution failed when execute validateFail().", e);
                }

            }
        }

        return processIsSuccess;
    }

    /**
     * Do work process.<br>
     *
     * @param record kafka's record
     * @param acc java bean
     * @param contextMap context map
     * @return process status
     */
    private boolean doWorkProcess(
            ConsumerRecord<String, String> record, T acc, Map<String, Object> contextMap
    ) {

        boolean processIsSuccess = true;

        try {
            if (worker.doWork(record, acc, contextMap)) {
                worker.doWorkSuccess(record, acc, contextMap);
            } else {
                processIsSuccess = false;
            }
        } catch (RuntimeException e) {
            processIsSuccess = false;
            if (log.isWarnEnabled()) {
                log.warn("Topic[" + this.topic + "] : "
                        + "Execution failed when execute doWork() or doWorkSuccess().", e);
            }

        }

        if (!processIsSuccess) {
            try {
                worker.doWorkFail(record, acc, contextMap);
            } catch (RuntimeException e) {
                // ignore error because of [processIsSuccess] variable has been set false.
                if (log.isWarnEnabled()) {
                    log.warn("Topic[" + this.topic + "] : "
                            + "Execution failed when execute doWorkFail().", e);
                }
            }
        }

        return processIsSuccess;
    }

    /**
     * Initialize.
     * Check parameters,create consumer.
     *
     * @throws IllegalArgumentException when necessary arguments are not set
     */
    public void initialize() {

        if (consumer == null) {

            this.checkKafkaEnvIsReady();

            Properties props = new Properties();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            props.put(
                    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringDeserializer"
            );
            props.put(
                    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringDeserializer"
            );
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeoutMsConfig);
            props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, isolationLevel);

            consumer = new KafkaConsumer<>(props);

            if (CollectionUtils.isNotEmpty(partitions)) {
                List<TopicPartition> topicPartitions = new ArrayList<>();
                for (int partition : partitions) {
                    topicPartitions.add(new TopicPartition(this.topic, partition));
                }
                consumer.assign(topicPartitions);
            } else {
                List<String> topicList = new ArrayList<>();
                topicList.add(topic);
                consumer.subscribe(topicList);
            }
        }

    }

    /**
     * Log record's information before process.
     *
     * @param record a record received from Kafka
     */
    private void logProcessStart(ConsumerRecord<String, String> record) {
        log.info("Start processing record[{}] from topic[{}], partition[{}].", record.value(),
                record.topic(), record.partition());
    }

    /**
     * Log error record's information.
     *
     * @param record a record received from Kafka
     */
    private void logErrorRecord(ConsumerRecord<String, String> record) {
        log.error("Receive an error record[{}] from topic[{}], partition[{}].", record.value(),
                record.topic(), record.partition());
    }


    /**
     * Commit offsets returned on the last poll for all the subscribed list of
     * topics and partitions.
     *
     * @throws Exception any Exception
     */
    private void commitMessages() throws Exception {
        try {
            retryTemplateFactory
                .buildTemplate(commitRetryCount, commitRetryInterval)
                .execute(this,  this);
        } catch (TerminatedRetryException e) {
            // Ignore TerminatedRetryException.
            if (log.isDebugEnabled()) {
                log.debug("Ignore TerminatedRetryException. "
                            + "(Commit message retry has been manually terminated by a listener.)",
                        e
                );
            }
        }

    }

    /**
     * Kafka commit's retry operation.
     * @param context the current retry context.
     * @return an Object that can be used to replace the callback result that failed
     * @throws Throwable if processing fails
     */
    @Override
    public Object doWithRetry(RetryContext context) throws Throwable {
        consumer.commitSync();
        return null;
    }

    /**
     * Kafka commit's recover operation.
     * @param context the current retry context.
     * @return an Object that can be used to replace the callback result that failed
     * @throws Exception if processing fails
     */
    @Override
    public Object recover(RetryContext context) throws Exception {
        throw new KafkaException(context.getLastThrowable());
    }

    /**
     * Check kafka's environment is ready.<br>
     */
    private void checkKafkaEnvIsReady() {

        boolean isReady = true;

        // InitializeUncompletedException
        if (StringUtils.isBlank(this.topic)) {
            isReady = false;
            log.error("The topic is necessary.");
        }

        if (StringUtils.isBlank(this.groupId)) {
            isReady = false;
            log.error("The groupId is necessary.");
        }

        if (Objects.isNull(this.typeParameterClass)) {
            isReady = false;
            log.error("The java type of the message is necessary.");
        }

        if (this.enableSendDeadLetter) {
            if (Objects.isNull(deadLetterProducer)) {
                isReady = false;
                log.error("The dead letter's producer is necessary.");
            }
        }

        if (!isReady) {
            throw new InitializeUncompletedException("Kafka's necessary arguments are not set.");
        }

    }

}

/**
 * @(#)SubscriberApplication.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.fastretailing.dcp.storecommon.transaction.businessprocessor.config.BusinessProcessorConfiguration;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is the abstract class that connects to Redis and get a string with Subscribe.
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.fastretailing.dcp.storecommon",
        "com.fastretailing.dcp.storecommon.transaction.log"})
@MapperScan({"com.fastretailing.dcp.storecommon.transaction.repository"})
public class SubscriberApplication {

    /** The name of the method that handles messages retrieved from Redis. */
    private static final String TRANSACTION_SUBSCRIBE_METHOD_NAME = "receiveMessage";

    /** Spring cloud batch parameter. */
    @Autowired
    private BusinessProcessorConfiguration currentBusinessProcessorConfiguration;

    /**
     * Connects to Redis and execute processing by Subscribe.
     * 
     * @param connectionFactory The instance of the connection factory class to connect with Redis
     *        thread-safe (long time).
     * @param listenerAdapter The instance of a class that performs flexible string manipulation to
     *        separate string parts from logic parts.
     * @return Connection container for thread-safe connection with Redis.
     */
    @Bean
    protected RedisMessageListenerContainer createListenerContainer(
            RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {

        checkConnection(connectionFactory);

        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.addMessageListener(listenerAdapter,
                new PatternTopic(currentBusinessProcessorConfiguration.getChannelCode()));
        log.info("channel name: " + currentBusinessProcessorConfiguration.getChannelCode());
        listenerContainer.setTaskExecutor(createThreadPoolTaskExecutor());
        listenerContainer.setSubscriptionExecutor(Executors.newSingleThreadExecutor());
        listenerContainer.afterPropertiesSet();
        return listenerContainer;
    }

    /**
     * Receives a message from Redis and returns an instance that performs string conversion.
     *
     * @param tranDispatchDataSubscriber The object to call when receiving the message.
     * @return The instance that specifies the objects and methods that pass string conversion
     *         results.
     */
    @Bean
    protected MessageListenerAdapter createListenerAdapter(
            TransactionDispatchDataSubscriber tranDispatchDataSubscriber) {
        return new MessageListenerAdapter(tranDispatchDataSubscriber,
                TRANSACTION_SUBSCRIBE_METHOD_NAME);
    }

    /**
     * Generates ThreadPoolTaskExecutor. The setting values of "core number of threads", "maximum
     * number of threads", and "queue capacity" of ThreadPoolTaskExecutor are obtained from the
     * setting file.
     *
     * @return Returns the generated ThreadPoolTaskExecutor.
     */
    private ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(currentBusinessProcessorConfiguration.getMaxThreadCount());
        taskExecutor.setMaxPoolSize(currentBusinessProcessorConfiguration.getMaxThreadCount());
        taskExecutor.setQueueCapacity(currentBusinessProcessorConfiguration.getQueueCapacity());
        taskExecutor.setRejectedExecutionHandler(this::rejectedExecutionHandler);
        taskExecutor.initialize();

        return taskExecutor;
    }

    /**
     * Check if the redis server can be connected.
     * 
     * @param connectionFactory Factory of Redis connections.
     */
    private void checkConnection(RedisConnectionFactory connectionFactory) {

        try {
            RedisConnection connection = connectionFactory.getConnection();
            connection.close();
        } catch (RuntimeException e) {
            log.error("Redis connection error.", e);
            throw e;
        }
    }

    /**
     * A handler for tasks that cannot be executed by a ThreadPoolExecutor. When tasks were rejected
     * by executor,do nothing.
     * 
     * @param task Task rejected by executor.
     * @param executor Message task executor.
     */
    private void rejectedExecutionHandler(Runnable task, ThreadPoolExecutor executor) {
        log.info("A message execution task was rejected.Thread pool:executing={}, waiting={}",
                executor.getPoolSize(), executor.getQueue().size());
    }
}

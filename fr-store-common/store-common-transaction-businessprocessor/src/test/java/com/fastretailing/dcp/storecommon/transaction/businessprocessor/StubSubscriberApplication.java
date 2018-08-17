/**
 * @(#)StubSubscriberApplication.java
 *
 *                                Copyright (c) 2018 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.storecommon.transaction.businessprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * This class is the application class for unit test.
 */
public class StubSubscriberApplication extends SubscriberApplication {

    /**
     * Returns a stub Redis connection factory instance.
     * 
     * @return A stub Redis connection factory instance.
     */
    @Bean
    protected RedisConnectionFactory getRedisConnectionFactory() {
        return new StubRedisConnectionFactory();
    }
}

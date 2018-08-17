/**
 * @(#)CacheConfiguration.java
 *
 * @Copyright (c) 2017 Fast Retailing Corporation.
 *
 */

package com.fastretailing.dcp.common.api.cache;

import com.fastretailing.dcp.common.api.cache.configuration.CacheRedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * the configuration of cache.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@EnableCaching
@ComponentScan(basePackages = {"com.fastretailing.dcp.common.api.cache"})
public class CacheConfiguration extends CachingConfigurerSupport {

    /**
     *  cache setting properties.
     */
    @Autowired
    private CacheRedisProperties properties;

    /**
     * set default KeyGenerator.
     * @return KeyGenerator KeyGenerator
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            if (params != null) {
                Arrays.stream(params).forEach(param -> sb.append(param.toString()));
            }
            return sb.toString();
        };
    }

    /**
     * Create the RedisCacheManager instance.<br>
     * @param redisTemplate the redis template
     * @return RedisCacheManager instance
     */
    @Bean
    RedisCacheManager createCacheManager(RedisTemplate<Object, Object> redisTemplate) {

        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        // set cache value and its expire time
        redisCacheManager.setExpires(properties.getExpire());
        return redisCacheManager;
    }
}

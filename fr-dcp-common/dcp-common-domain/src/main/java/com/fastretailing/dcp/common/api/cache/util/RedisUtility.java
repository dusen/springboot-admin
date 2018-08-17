/**
 * @(#)RedisUtility.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * the utility of redis cache.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Component
public class RedisUtility {

    /**
     * redisTemplate.
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * delete cache.
     *
     * @param key key
     */
    public void delete(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * check weather appointed key exist in cache.
     *
     * @param key key
     * @return true : exist
     */
    private boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * get cache.
     *
     * @param key key
     * @return cache
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}

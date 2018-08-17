/**
 * @(#)CacheRedisProperties.java
 *
 * @Copyright (c) 2017 Fast Retailing Corporation.
 */

package com.fastretailing.dcp.common.api.cache.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * the properties info bean of redis.
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cache.redis")
@Data
public class CacheRedisProperties {

    /**
     * the cache expire time map.
     */
    private Map<String, Long> expire;
}

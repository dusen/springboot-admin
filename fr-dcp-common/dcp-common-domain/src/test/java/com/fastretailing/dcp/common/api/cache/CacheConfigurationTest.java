/**
 * @(#)HmacAuthenticationJoinPointTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.cache;

import com.fastretailing.dcp.common.api.cache.configuration.CacheRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import redis.embedded.RedisServer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/cache/test-context.xml")
})
@Slf4j
public class CacheConfigurationTest {

    private CacheConfiguration cacheConfiguration = new CacheConfiguration();

    CacheRedisProperties cacheRedisProperties = new CacheRedisProperties();

    @Mock
    private RedisTemplate redisTemplate;

    @Mock
    private Environment env;

    private RedisServer redisServer;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        Map<String, Long> map = new HashMap<>();
        map.put("key", 10L);

        cacheRedisProperties.setExpire(map);

        ReflectionTestUtils.setField(cacheConfiguration, "properties", cacheRedisProperties);
        redisServer = new RedisServer();

    }

    @After
    public void tearDown() throws Exception {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
        redisServer = null;
    }


    /**
     * test keyGenerator
     */
    @Test
    public void testKeyGenerator()  {

        assertEquals("com.fastretailing.dcp.common.api.cache.CacheConfigurationkeyGeneratorparam1param2",
                cacheConfiguration.keyGenerator().generate(cacheConfiguration, CacheConfiguration.class.getMethods()[0], "param1", "param2").toString());

    }

    /**
     * test keyGenerator
     */
    @Test
    public void testCreateCacheManager()  {

        try {
            RedisCacheManager redisCacheManager = cacheConfiguration.createCacheManager(redisTemplate);

            Field field = RedisCacheManager.class.getDeclaredField("expires");
            field.setAccessible(true);
            assertEquals(field.get(redisCacheManager), cacheRedisProperties.getExpire());
        } catch (Exception e) {
            fail();
        }
    }



}

/**
 * @(#)HmacAuthenticationJoinPointTest.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.cache;

import com.fastretailing.dcp.common.api.cache.configuration.CacheRedisProperties;
import com.fastretailing.dcp.common.api.cache.util.RedisUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration("classpath*:com/fastretailing/dcp/common/api/cache/test-context.xml")
})
@Slf4j
public class RedisUtilityTest extends EmbeddedRedisServer {

    RedisUtility redisUtility = new RedisUtility();

    CacheRedisProperties cacheRedisProperties = new CacheRedisProperties();

    private RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        Map<String, Long> map = new HashMap<>();
        map.put("key", 10L);
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        cacheRedisProperties.setExpire(map);
        ReflectionTestUtils.setField(redisUtility, "redisTemplate", redisTemplate);
    }


    /**
     * test get
     */
    @Test
    public void testGet()  {

        Map<String, String> codeList1 = new HashMap<>();
        codeList1.put("key1", "value1");

        redisTemplate.opsForValue().set("codeList1", codeList1);

        assertEquals(codeList1, redisUtility.get("codeList1"));

    }

    /**
     * test delete
     */
    @Test
    public void testDelete()  {

        Map<String, String> codeList1 = new HashMap<>();
        codeList1.put("key1", "value1");

        redisTemplate.opsForValue().set("codeList1", codeList1);

        redisUtility.delete("codeList1");

        assertNull(redisTemplate.opsForValue().get("codeList1"));

    }

}

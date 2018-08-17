package com.fastretailing.dcp.common.api.cache;

import org.junit.After;
import org.junit.Before;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author Fast Retailing
 * @version v0.1 on 2017-12-06
 */
public class EmbeddedRedisServer {


    private RedisServer redisServer;

    JedisConnectionFactory factory;

    /**
     * start embedded redis server and set connection factory
     * @throws IOException
     */
    @Before
    public void postConstruct() throws IOException {
        // This redis test will use port from 50000 to 60000 to contribute a embedded redis server.
        int port = (int) (Math.random() * 10000) + 50000;

        redisServer = RedisServer.builder()
                .port(port)
                .setting("maxmemory 128M") //maxheap 128M
                .build();


        if (redisServer.isActive()) {
            redisServer.stop();
        }

        if (!redisServer.isActive()) {
            redisServer.start();
        }


        factory = new JedisConnectionFactory(jedisPoolConfig());

        JedisShardInfo jedisShardInfo = new JedisShardInfo("localhost", port);

        factory.setShardInfo(jedisShardInfo);

    }


    @After
    public void shutDown() {
        if (redisServer.isActive()) {
            redisServer.stop();
        }
        redisServer = null;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(100);
        config.setMinIdle(1);
        config.setMaxWaitMillis(-1);
        return config;
    }
}

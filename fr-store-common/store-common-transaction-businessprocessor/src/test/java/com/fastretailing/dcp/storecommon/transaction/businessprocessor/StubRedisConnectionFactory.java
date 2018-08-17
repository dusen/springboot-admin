/**
 * @(#)StubRedisConnectionFactory.java
 *
 *                                     Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.transaction.businessprocessor;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;

/**
 * Stub Redis connection factory for unit test.
 */
public class StubRedisConnectionFactory implements RedisConnectionFactory {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.dao.support.PersistenceExceptionTranslator#translateExceptionIfPossible(
     * java.lang.RuntimeException)
     */
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException arg0) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnectionFactory#getClusterConnection()
     */
    @Override
    public RedisClusterConnection getClusterConnection() {
        return null;
    }

    /**
     * Returns a stub Redis connection instance.
     * 
     * @return A stub Redis connection.
     */
    @Override
    public RedisConnection getConnection() {
        return new StubRedisConnection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnectionFactory#
     * getConvertPipelineAndTxResults()
     */
    @Override
    public boolean getConvertPipelineAndTxResults() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnectionFactory#getSentinelConnection()
     */
    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return null;
    }

}

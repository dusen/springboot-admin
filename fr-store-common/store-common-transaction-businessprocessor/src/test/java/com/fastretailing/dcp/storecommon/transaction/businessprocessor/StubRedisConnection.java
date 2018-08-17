/**
 * @(#)StubRedisConnection.java
 *
 *                              Copyright (c) 2018 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.storecommon.transaction.businessprocessor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPipelineException;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.Subscription;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.core.types.RedisClientInfo;

/**
 * Stub Redis connection for unit test.
 */
public class StubRedisConnection implements RedisConnection {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisCommands#execute(java.lang.String,
     * byte[][])
     */
    @Override
    public Object execute(String arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#del(byte[][])
     */
    @Override
    public Long del(byte[]... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#dump(byte[])
     */
    @Override
    public byte[] dump(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#exists(byte[])
     */
    @Override
    public Boolean exists(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#expire(byte[], long)
     */
    @Override
    public Boolean expire(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#expireAt(byte[], long)
     */
    @Override
    public Boolean expireAt(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#keys(byte[])
     */
    @Override
    public Set<byte[]> keys(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#move(byte[], int)
     */
    @Override
    public Boolean move(byte[] arg0, int arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#pExpire(byte[], long)
     */
    @Override
    public Boolean pExpire(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#pExpireAt(byte[], long)
     */
    @Override
    public Boolean pExpireAt(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#pTtl(byte[])
     */
    @Override
    public Long pTtl(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#pTtl(byte[],
     * java.util.concurrent.TimeUnit)
     */
    @Override
    public Long pTtl(byte[] arg0, TimeUnit arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#persist(byte[])
     */
    @Override
    public Boolean persist(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#randomKey()
     */
    @Override
    public byte[] randomKey() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#rename(byte[], byte[])
     */
    @Override
    public void rename(byte[] arg0, byte[] arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#renameNX(byte[], byte[])
     */
    @Override
    public Boolean renameNX(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#restore(byte[], long, byte[])
     */
    @Override
    public void restore(byte[] arg0, long arg1, byte[] arg2) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisKeyCommands#scan(org.springframework.data.
     * redis.core.ScanOptions)
     */
    @Override
    public Cursor<byte[]> scan(ScanOptions arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#sort(byte[],
     * org.springframework.data.redis.connection.SortParameters)
     */
    @Override
    public List<byte[]> sort(byte[] arg0, SortParameters arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#sort(byte[],
     * org.springframework.data.redis.connection.SortParameters, byte[])
     */
    @Override
    public Long sort(byte[] arg0, SortParameters arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#ttl(byte[])
     */
    @Override
    public Long ttl(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#ttl(byte[],
     * java.util.concurrent.TimeUnit)
     */
    @Override
    public Long ttl(byte[] arg0, TimeUnit arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisKeyCommands#type(byte[])
     */
    @Override
    public DataType type(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#append(byte[], byte[])
     */
    @Override
    public Long append(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#bitCount(byte[])
     */
    @Override
    public Long bitCount(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#bitCount(byte[], long,
     * long)
     */
    @Override
    public Long bitCount(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisStringCommands#bitOp(org.springframework.data.
     * redis.connection.RedisStringCommands.BitOperation, byte[], byte[][])
     */
    @Override
    public Long bitOp(BitOperation arg0, byte[] arg1, byte[]... arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#decr(byte[])
     */
    @Override
    public Long decr(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#decrBy(byte[], long)
     */
    @Override
    public Long decrBy(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#get(byte[])
     */
    @Override
    public byte[] get(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#getBit(byte[], long)
     */
    @Override
    public Boolean getBit(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#getRange(byte[], long,
     * long)
     */
    @Override
    public byte[] getRange(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#getSet(byte[], byte[])
     */
    @Override
    public byte[] getSet(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#incr(byte[])
     */
    @Override
    public Long incr(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#incrBy(byte[], long)
     */
    @Override
    public Long incrBy(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#incrBy(byte[], double)
     */
    @Override
    public Double incrBy(byte[] arg0, double arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#mGet(byte[][])
     */
    @Override
    public List<byte[]> mGet(byte[]... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#mSet(java.util.Map)
     */
    @Override
    public void mSet(Map<byte[], byte[]> arg0) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#mSetNX(java.util.Map)
     */
    @Override
    public Boolean mSetNX(Map<byte[], byte[]> arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#pSetEx(byte[], long,
     * byte[])
     */
    @Override
    public void pSetEx(byte[] arg0, long arg1, byte[] arg2) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#set(byte[], byte[])
     */
    @Override
    public void set(byte[] arg0, byte[] arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#set(byte[], byte[],
     * org.springframework.data.redis.core.types.Expiration,
     * org.springframework.data.redis.connection.RedisStringCommands.SetOption)
     */
    @Override
    public void set(byte[] arg0, byte[] arg1, Expiration arg2, SetOption arg3) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#setBit(byte[], long,
     * boolean)
     */
    @Override
    public Boolean setBit(byte[] arg0, long arg1, boolean arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#setEx(byte[], long,
     * byte[])
     */
    @Override
    public void setEx(byte[] arg0, long arg1, byte[] arg2) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#setNX(byte[], byte[])
     */
    @Override
    public Boolean setNX(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#setRange(byte[], byte[],
     * long)
     */
    @Override
    public void setRange(byte[] arg0, byte[] arg1, long arg2) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisStringCommands#strLen(byte[])
     */
    @Override
    public Long strLen(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#bLPop(int, byte[][])
     */
    @Override
    public List<byte[]> bLPop(int arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#bRPop(int, byte[][])
     */
    @Override
    public List<byte[]> bRPop(int arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#bRPopLPush(int, byte[],
     * byte[])
     */
    @Override
    public byte[] bRPopLPush(int arg0, byte[] arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lIndex(byte[], long)
     */
    @Override
    public byte[] lIndex(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lInsert(byte[],
     * org.springframework.data.redis.connection.RedisListCommands.Position, byte[], byte[])
     */
    @Override
    public Long lInsert(byte[] arg0, Position arg1, byte[] arg2, byte[] arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lLen(byte[])
     */
    @Override
    public Long lLen(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lPop(byte[])
     */
    @Override
    public byte[] lPop(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lPush(byte[], byte[][])
     */
    @Override
    public Long lPush(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lPushX(byte[], byte[])
     */
    @Override
    public Long lPushX(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lRange(byte[], long, long)
     */
    @Override
    public List<byte[]> lRange(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lRem(byte[], long, byte[])
     */
    @Override
    public Long lRem(byte[] arg0, long arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lSet(byte[], long, byte[])
     */
    @Override
    public void lSet(byte[] arg0, long arg1, byte[] arg2) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#lTrim(byte[], long, long)
     */
    @Override
    public void lTrim(byte[] arg0, long arg1, long arg2) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#rPop(byte[])
     */
    @Override
    public byte[] rPop(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#rPopLPush(byte[], byte[])
     */
    @Override
    public byte[] rPopLPush(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#rPush(byte[], byte[][])
     */
    @Override
    public Long rPush(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisListCommands#rPushX(byte[], byte[])
     */
    @Override
    public Long rPushX(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sAdd(byte[], byte[][])
     */
    @Override
    public Long sAdd(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sCard(byte[])
     */
    @Override
    public Long sCard(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sDiff(byte[][])
     */
    @Override
    public Set<byte[]> sDiff(byte[]... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sDiffStore(byte[], byte[][])
     */
    @Override
    public Long sDiffStore(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sInter(byte[][])
     */
    @Override
    public Set<byte[]> sInter(byte[]... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sInterStore(byte[], byte[][])
     */
    @Override
    public Long sInterStore(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sIsMember(byte[], byte[])
     */
    @Override
    public Boolean sIsMember(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sMembers(byte[])
     */
    @Override
    public Set<byte[]> sMembers(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sMove(byte[], byte[], byte[])
     */
    @Override
    public Boolean sMove(byte[] arg0, byte[] arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sPop(byte[])
     */
    @Override
    public byte[] sPop(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sRandMember(byte[])
     */
    @Override
    public byte[] sRandMember(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sRandMember(byte[], long)
     */
    @Override
    public List<byte[]> sRandMember(byte[] arg0, long arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sRem(byte[], byte[][])
     */
    @Override
    public Long sRem(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sScan(byte[],
     * org.springframework.data.redis.core.ScanOptions)
     */
    @Override
    public Cursor<byte[]> sScan(byte[] arg0, ScanOptions arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sUnion(byte[][])
     */
    @Override
    public Set<byte[]> sUnion(byte[]... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisSetCommands#sUnionStore(byte[], byte[][])
     */
    @Override
    public Long sUnionStore(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zAdd(byte[], java.util.Set)
     */
    @Override
    public Long zAdd(byte[] arg0, Set<Tuple> arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zAdd(byte[], double, byte[])
     */
    @Override
    public Boolean zAdd(byte[] arg0, double arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zCard(byte[])
     */
    @Override
    public Long zCard(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zCount(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Long zCount(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zCount(byte[], double,
     * double)
     */
    @Override
    public Long zCount(byte[] arg0, double arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zIncrBy(byte[], double,
     * byte[])
     */
    @Override
    public Double zIncrBy(byte[] arg0, double arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zInterStore(byte[],
     * byte[][])
     */
    @Override
    public Long zInterStore(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zInterStore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Aggregate, int[], byte[][])
     */
    @Override
    public Long zInterStore(byte[] arg0, Aggregate arg1, int[] arg2, byte[]... arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRange(byte[], long, long)
     */
    @Override
    public Set<byte[]> zRange(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByLex(byte[])
     */
    @Override
    public Set<byte[]> zRangeByLex(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByLex(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Set<byte[]> zRangeByLex(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByLex(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range,
     * org.springframework.data.redis.connection.RedisZSetCommands.Limit)
     */
    @Override
    public Set<byte[]> zRangeByLex(byte[] arg0, Range arg1, Limit arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Set<byte[]> zRangeByScore(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScore(byte[],
     * double, double)
     */
    @Override
    public Set<byte[]> zRangeByScore(byte[] arg0, double arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScore(byte[],
     * java.lang.String, java.lang.String)
     */
    @Override
    public Set<byte[]> zRangeByScore(byte[] arg0, String arg1, String arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range,
     * org.springframework.data.redis.connection.RedisZSetCommands.Limit)
     */
    @Override
    public Set<byte[]> zRangeByScore(byte[] arg0, Range arg1, Limit arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScore(byte[],
     * double, double, long, long)
     */
    @Override
    public Set<byte[]> zRangeByScore(byte[] arg0, double arg1, double arg2, long arg3, long arg4) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScore(byte[],
     * java.lang.String, java.lang.String, long, long)
     */
    @Override
    public Set<byte[]> zRangeByScore(byte[] arg0, String arg1, String arg2, long arg3, long arg4) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScoreWithScores(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Set<Tuple> zRangeByScoreWithScores(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScoreWithScores(byte[],
     * double, double)
     */
    @Override
    public Set<Tuple> zRangeByScoreWithScores(byte[] arg0, double arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScoreWithScores(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range,
     * org.springframework.data.redis.connection.RedisZSetCommands.Limit)
     */
    @Override
    public Set<Tuple> zRangeByScoreWithScores(byte[] arg0, Range arg1, Limit arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRangeByScoreWithScores(byte[],
     * double, double, long, long)
     */
    @Override
    public Set<Tuple> zRangeByScoreWithScores(byte[] arg0, double arg1, double arg2, long arg3,
            long arg4) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRangeWithScores(byte[],
     * long, long)
     */
    @Override
    public Set<Tuple> zRangeWithScores(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRank(byte[], byte[])
     */
    @Override
    public Long zRank(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRem(byte[], byte[][])
     */
    @Override
    public Long zRem(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRemRange(byte[], long,
     * long)
     */
    @Override
    public Long zRemRange(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRemRangeByScore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Long zRemRangeByScore(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRemRangeByScore(byte[],
     * double, double)
     */
    @Override
    public Long zRemRangeByScore(byte[] arg0, double arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRange(byte[], long,
     * long)
     */
    @Override
    public Set<byte[]> zRevRange(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Set<byte[]> zRevRangeByScore(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScore(byte[],
     * double, double)
     */
    @Override
    public Set<byte[]> zRevRangeByScore(byte[] arg0, double arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Range,
     * org.springframework.data.redis.connection.RedisZSetCommands.Limit)
     */
    @Override
    public Set<byte[]> zRevRangeByScore(byte[] arg0, Range arg1, Limit arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScore(byte[],
     * double, double, long, long)
     */
    @Override
    public Set<byte[]> zRevRangeByScore(byte[] arg0, double arg1, double arg2, long arg3,
            long arg4) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScoreWithScores(byte[]
     * , org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Set<Tuple> zRevRangeByScoreWithScores(byte[] arg0, Range arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScoreWithScores(byte[]
     * , double, double)
     */
    @Override
    public Set<Tuple> zRevRangeByScoreWithScores(byte[] arg0, double arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScoreWithScores(byte[]
     * , org.springframework.data.redis.connection.RedisZSetCommands.Range,
     * org.springframework.data.redis.connection.RedisZSetCommands.Limit)
     */
    @Override
    public Set<Tuple> zRevRangeByScoreWithScores(byte[] arg0, Range arg1, Limit arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeByScoreWithScores(byte[]
     * , double, double, long, long)
     */
    @Override
    public Set<Tuple> zRevRangeByScoreWithScores(byte[] arg0, double arg1, double arg2, long arg3,
            long arg4) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRangeWithScores(byte[],
     * long, long)
     */
    @Override
    public Set<Tuple> zRevRangeWithScores(byte[] arg0, long arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zRevRank(byte[], byte[])
     */
    @Override
    public Long zRevRank(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zScan(byte[],
     * org.springframework.data.redis.core.ScanOptions)
     */
    @Override
    public Cursor<Tuple> zScan(byte[] arg0, ScanOptions arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zScore(byte[], byte[])
     */
    @Override
    public Double zScore(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zUnionStore(byte[],
     * byte[][])
     */
    @Override
    public Long zUnionStore(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisZSetCommands#zUnionStore(byte[],
     * org.springframework.data.redis.connection.RedisZSetCommands.Aggregate, int[], byte[][])
     */
    @Override
    public Long zUnionStore(byte[] arg0, Aggregate arg1, int[] arg2, byte[]... arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hDel(byte[], byte[][])
     */
    @Override
    public Long hDel(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hExists(byte[], byte[])
     */
    @Override
    public Boolean hExists(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hGet(byte[], byte[])
     */
    @Override
    public byte[] hGet(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hGetAll(byte[])
     */
    @Override
    public Map<byte[], byte[]> hGetAll(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hIncrBy(byte[], byte[],
     * long)
     */
    @Override
    public Long hIncrBy(byte[] arg0, byte[] arg1, long arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hIncrBy(byte[], byte[],
     * double)
     */
    @Override
    public Double hIncrBy(byte[] arg0, byte[] arg1, double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hKeys(byte[])
     */
    @Override
    public Set<byte[]> hKeys(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hLen(byte[])
     */
    @Override
    public Long hLen(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hMGet(byte[], byte[][])
     */
    @Override
    public List<byte[]> hMGet(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hMSet(byte[], java.util.Map)
     */
    @Override
    public void hMSet(byte[] arg0, Map<byte[], byte[]> arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hScan(byte[],
     * org.springframework.data.redis.core.ScanOptions)
     */
    @Override
    public Cursor<Entry<byte[], byte[]>> hScan(byte[] arg0, ScanOptions arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hSet(byte[], byte[], byte[])
     */
    @Override
    public Boolean hSet(byte[] arg0, byte[] arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hSetNX(byte[], byte[],
     * byte[])
     */
    @Override
    public Boolean hSetNX(byte[] arg0, byte[] arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisHashCommands#hVals(byte[])
     */
    @Override
    public List<byte[]> hVals(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisTxCommands#discard()
     */
    @Override
    public void discard() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisTxCommands#exec()
     */
    @Override
    public List<Object> exec() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisTxCommands#multi()
     */
    @Override
    public void multi() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisTxCommands#unwatch()
     */
    @Override
    public void unwatch() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisTxCommands#watch(byte[][])
     */
    @Override
    public void watch(byte[]... arg0) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisPubSubCommands#getSubscription()
     */
    @Override
    public Subscription getSubscription() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisPubSubCommands#isSubscribed()
     */
    @Override
    public boolean isSubscribed() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisPubSubCommands#pSubscribe(org.springframework.
     * data.redis.connection.MessageListener, byte[][])
     */
    @Override
    public void pSubscribe(MessageListener arg0, byte[]... arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisPubSubCommands#publish(byte[], byte[])
     */
    @Override
    public Long publish(byte[] arg0, byte[] arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisPubSubCommands#subscribe(org.springframework.
     * data.redis.connection.MessageListener, byte[][])
     */
    @Override
    public void subscribe(MessageListener arg0, byte[]... arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnectionCommands#echo(byte[])
     */
    @Override
    public byte[] echo(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnectionCommands#ping()
     */
    @Override
    public String ping() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnectionCommands#select(int)
     */
    @Override
    public void select(int arg0) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#bgReWriteAof()
     */
    @Override
    public void bgReWriteAof() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#bgSave()
     */
    @Override
    public void bgSave() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#bgWriteAof()
     */
    @Override
    public void bgWriteAof() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#dbSize()
     */
    @Override
    public Long dbSize() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#flushAll()
     */
    @Override
    public void flushAll() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#flushDb()
     */
    @Override
    public void flushDb() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#getClientList()
     */
    @Override
    public List<RedisClientInfo> getClientList() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#getClientName()
     */
    @Override
    public String getClientName() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisServerCommands#getConfig(java.lang.String)
     */
    @Override
    public List<String> getConfig(String arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#info()
     */
    @Override
    public Properties info() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#info(java.lang.String)
     */
    @Override
    public Properties info(String arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisServerCommands#killClient(java.lang.String,
     * int)
     */
    @Override
    public void killClient(String arg0, int arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#lastSave()
     */
    @Override
    public Long lastSave() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#migrate(byte[],
     * org.springframework.data.redis.connection.RedisNode, int,
     * org.springframework.data.redis.connection.RedisServerCommands.MigrateOption)
     */
    @Override
    public void migrate(byte[] arg0, RedisNode arg1, int arg2, MigrateOption arg3) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#migrate(byte[],
     * org.springframework.data.redis.connection.RedisNode, int,
     * org.springframework.data.redis.connection.RedisServerCommands.MigrateOption, long)
     */
    @Override
    public void migrate(byte[] arg0, RedisNode arg1, int arg2, MigrateOption arg3, long arg4) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#resetConfigStats()
     */
    @Override
    public void resetConfigStats() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#save()
     */
    @Override
    public void save() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#setClientName(byte[])
     */
    @Override
    public void setClientName(byte[] arg0) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisServerCommands#setConfig(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void setConfig(String arg0, String arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#shutdown()
     */
    @Override
    public void shutdown() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisServerCommands#shutdown(org.springframework.
     * data.redis.connection.RedisServerCommands.ShutdownOption)
     */
    @Override
    public void shutdown(ShutdownOption arg0) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#slaveOf(java.lang.String,
     * int)
     */
    @Override
    public void slaveOf(String arg0, int arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#slaveOfNoOne()
     */
    @Override
    public void slaveOfNoOne() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisServerCommands#time()
     */
    @Override
    public Long time() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisScriptingCommands#eval(byte[],
     * org.springframework.data.redis.connection.ReturnType, int, byte[][])
     */
    @Override
    public <T> T eval(byte[] arg0, ReturnType arg1, int arg2, byte[]... arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.redis.connection.RedisScriptingCommands#evalSha(java.lang.String,
     * org.springframework.data.redis.connection.ReturnType, int, byte[][])
     */
    @Override
    public <T> T evalSha(String arg0, ReturnType arg1, int arg2, byte[]... arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisScriptingCommands#evalSha(byte[],
     * org.springframework.data.redis.connection.ReturnType, int, byte[][])
     */
    @Override
    public <T> T evalSha(byte[] arg0, ReturnType arg1, int arg2, byte[]... arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisScriptingCommands#scriptExists(java.lang.
     * String[])
     */
    @Override
    public List<Boolean> scriptExists(String... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisScriptingCommands#scriptFlush()
     */
    @Override
    public void scriptFlush() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisScriptingCommands#scriptKill()
     */
    @Override
    public void scriptKill() {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisScriptingCommands#scriptLoad(byte[])
     */
    @Override
    public String scriptLoad(byte[] arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoAdd(byte[],
     * org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation)
     */
    @Override
    public Long geoAdd(byte[] arg0, GeoLocation<byte[]> arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoAdd(byte[], java.util.Map)
     */
    @Override
    public Long geoAdd(byte[] arg0, Map<byte[], Point> arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoAdd(byte[],
     * java.lang.Iterable)
     */
    @Override
    public Long geoAdd(byte[] arg0, Iterable<GeoLocation<byte[]>> arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoAdd(byte[],
     * org.springframework.data.geo.Point, byte[])
     */
    @Override
    public Long geoAdd(byte[] arg0, Point arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoDist(byte[], byte[],
     * byte[])
     */
    @Override
    public Distance geoDist(byte[] arg0, byte[] arg1, byte[] arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoDist(byte[], byte[],
     * byte[], org.springframework.data.geo.Metric)
     */
    @Override
    public Distance geoDist(byte[] arg0, byte[] arg1, byte[] arg2, Metric arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoHash(byte[], byte[][])
     */
    @Override
    public List<String> geoHash(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoPos(byte[], byte[][])
     */
    @Override
    public List<Point> geoPos(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoRadius(byte[],
     * org.springframework.data.geo.Circle)
     */
    @Override
    public GeoResults<GeoLocation<byte[]>> geoRadius(byte[] arg0, Circle arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoRadius(byte[],
     * org.springframework.data.geo.Circle,
     * org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs)
     */
    @Override
    public GeoResults<GeoLocation<byte[]>> geoRadius(byte[] arg0, Circle arg1,
            GeoRadiusCommandArgs arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoRadiusByMember(byte[],
     * byte[], double)
     */
    @Override
    public GeoResults<GeoLocation<byte[]>> geoRadiusByMember(byte[] arg0, byte[] arg1,
            double arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoRadiusByMember(byte[],
     * byte[], org.springframework.data.geo.Distance)
     */
    @Override
    public GeoResults<GeoLocation<byte[]>> geoRadiusByMember(byte[] arg0, byte[] arg1,
            Distance arg2) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoRadiusByMember(byte[],
     * byte[], org.springframework.data.geo.Distance,
     * org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs)
     */
    @Override
    public GeoResults<GeoLocation<byte[]>> geoRadiusByMember(byte[] arg0, byte[] arg1,
            Distance arg2, GeoRadiusCommandArgs arg3) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisGeoCommands#geoRemove(byte[], byte[][])
     */
    @Override
    public Long geoRemove(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.HyperLogLogCommands#pfAdd(byte[], byte[][])
     */
    @Override
    public Long pfAdd(byte[] arg0, byte[]... arg1) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.HyperLogLogCommands#pfCount(byte[][])
     */
    @Override
    public Long pfCount(byte[]... arg0) {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.HyperLogLogCommands#pfMerge(byte[], byte[][])
     */
    @Override
    public void pfMerge(byte[] arg0, byte[]... arg1) {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#close()
     */
    @Override
    public void close() throws DataAccessException {


    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#closePipeline()
     */
    @Override
    public List<Object> closePipeline() throws RedisPipelineException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#getNativeConnection()
     */
    @Override
    public Object getNativeConnection() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#getSentinelConnection()
     */
    @Override
    public RedisSentinelConnection getSentinelConnection() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#isClosed()
     */
    @Override
    public boolean isClosed() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#isPipelined()
     */
    @Override
    public boolean isPipelined() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#isQueueing()
     */
    @Override
    public boolean isQueueing() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.redis.connection.RedisConnection#openPipeline()
     */
    @Override
    public void openPipeline() {


    }

}

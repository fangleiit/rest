package com.biz.bizunited.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository public class CommonRedisDao {

    @Autowired protected ShardedJedisPool jedisPool;

    /**
     * Returns the value associated with field in the hash stored at key.
     */
    protected byte[] hget(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key.getBytes(), field.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Removes the specified fields from the hash stored at key.
     * Specified fields that do not exist within this hash are ignored.
     * If key does not exist, it is treated as an empty hash and this command returns 0.
     */
    protected Long hdel(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hdel(key.getBytes(), field.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Sets field in the hash stored at key to value.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists in the hash, it is overwritten.
     */
    protected long hset(String key, String field, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key.getBytes(), field.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Sets field in the hash stored at key to value, only if field does not yet exist.
     * If key does not exist, a new key holding a hash is created.
     * If field already exists, this operation has no effect.
     */
    protected boolean hsetnx(String key, String field, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hsetnx(key.getBytes(), field.getBytes(), value).intValue() == 1;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Sets the specified fields to their respective values in the hash stored at key.
     * This command overwrites any existing fields in the hash.
     * If key does not exist, a new key holding a hash is created.
     */
    protected String hmset(String key, Map<byte[], byte[]> hash) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmset(key.getBytes(), hash);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns the values associated with the specified fields in the hash stored at key.
     */
    protected List<byte[]> hmget(String key, byte[]... fields) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hmget(key.getBytes(), fields);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns all fields and values of the hash stored at key.
     * In the returned value, every field name is followed by its value, so the length of the reply is twice the size of the hash.
     */
    protected Map<byte[], byte[]> hgetAll(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns if field is an existing field in the hash stored at key.
     */
    protected boolean hexists(String key, String field) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hexists(key.getBytes(), field.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Add the specified members to the set stored at key.
     * Specified members that are already a member of this set are ignored.
     * If key does not exist, a new set is created before adding the specified members.
     * An error is returned when the value stored at key is not a set.
     */
    protected Long sadd(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns if member is a member of the set stored at key.
     */
    protected boolean sismember(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns all the members of the set value stored at key.
     * This has the same effect as running SINTER with one argument key.
     */
    protected Set<byte[]> smembers(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Remove the specified members from the set stored at key.
     * Specified members that are not a member of this set are ignored.
     * If key does not exist, it is treated as an empty set and this command returns 0.
     * An error is returned when the value stored at key is not a set.
     */
    protected Long srem(String key, byte[] members) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key.getBytes(), members);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Removes the specified keys. A key is ignored if it does not exist.
     */
    protected Long del(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns if key exists.
     */
    protected boolean exists(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Adds all the specified members with the specified scores to the sorted set stored at key.
     */
    protected boolean zadd(String key, double score, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long result = jedis.zadd(key.getBytes(), score, member);
            return result == 1l || result == 0l;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Removes the specified members from the sorted set stored at key. Non existing members are ignored.
     */
    protected Long zrem(String key, byte[]... members) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key.getBytes(), members);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * {@linkplain #zrem(String, byte[]...)}
     */
    protected Long zrem(String key, String member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key.getBytes(), member.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * The elements are considered to be ordered from the lowest to the highest score.
     * Lexicographical order is used for elements with equal score.
     */
    protected Set<byte[]> zRange(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns the specified range of elements in the sorted set stored at key.
     * The elements are considered to be ordered from the highest to the lowest score.
     * Descending lexicographical order is used for elements with equal score.
     * Apart from the reversed ordering, ZREVRANGE is similar to {@linkplain #zRange(String, long, long)}.
     */
    protected Set<byte[]> zrevrange(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Set<Tuple> zrevrangeWithScore(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeWithScores(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns all the elements in the sorted set at key with a score between min and max (including elements with score equal to min or max).
     * The elements are considered to be ordered from low to high scores.
     * The elements having the same score are returned in lexicographical order (this follows from a property of the sorted set implementation in Redis and does not involve further computation).
     * <ul>
     * <li>
     * <span>ZRANGEBYSCORE zset (1 5</span><br/>
     * <span>Will return all elements with 1 < score <= 5 while:</span>
     * </li>
     * <li>
     * <span>ZRANGEBYSCORE zset (5 (10</span></br>
     * <span>Will return all the elements with 5 < score < 10 (5 and 10 excluded).</span>
     * </li>
     * </ul>
     */
    protected Set<byte[]> zangeByScore(String key, String min, String max) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key.getBytes(), min.getBytes(), max.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    protected Set<Tuple> zrangeWithScores(String key, long start, long end) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeWithScores(key.getBytes(), start, end);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    //获取列表元素总数
    protected Long zCard(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    //获取sort set score
    protected Double zscore(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zscore(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Integer zscoreToInt(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Double d = jedis.zscore(key.getBytes(), member);
            return d == null ? null : d.intValue();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    protected Long zscoreToLong(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Double d = jedis.zscore(key.getBytes(), member);
            return d == null ? null : d.longValue();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Long zrank(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrank(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Double zincrby(String key, double score, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(key.getBytes(), score, member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    protected Long rpush(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Long rpush(String key, byte[]... strings) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key.getBytes(), strings);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Long lpush(String key, byte[]... strings) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key.getBytes(), strings);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected long llen(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected byte[] lpop(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpop(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Set<byte[]> zrevrangeByScore(String key, byte[] max, byte[] min) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key.getBytes(), max, min);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    protected Set<byte[]> zrevrangeByScore(String key, String max, String min) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Set<byte[]> zrevrangeByScore(String key, String max, String min, int offset,
        int count) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis
                .zrevrangeByScore(key.getBytes(), max.getBytes(), min.getBytes(), offset, count);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    protected long zcount(String key, String min, String max) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long count = jedis.zcount(key.getBytes(), min.getBytes(), max.getBytes());
            return count == null ? 0 : count;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 根据评分删除数据
     *
     * @author maliang
     * @date 上午10:31:39  2014-9-29
     */
    protected Long zremrangeByScore(String key, String start, String end) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByScore(key.getBytes(), start.getBytes(), end.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 通过score范围获取成员数量
     */
    protected Long zcount(String key, double min, double max) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcount(key.getBytes(), min, max);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max, offset, count);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Set<String> zrangeByScore(String key, String min, String max) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected Set<String> zrangeByScore(String key, long min, long max) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected void expire(String key, int seconds) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key.getBytes(), seconds);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected void expireAt(String key, Long unixTime) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expireAt(key.getBytes(), unixTime);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected List pipeHgetall(List<String> ids) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String id : ids) {
                jedisPipeline.hgetAll(id.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected void pipeZadd(String key, double score, List<String> members) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String member : members) {
                jedisPipeline.zadd(key.getBytes(), score, member.getBytes());
            }
            jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    protected List pipeZcard(List<String> ids) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (String id : ids) {
                jedisPipeline.zcard(id.getBytes());
            }
            return jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    //管道存入hash
    protected void pipeHmset(String key, List<Map<byte[], byte[]>> hashs) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            ShardedJedisPipeline jedisPipeline = jedis.pipelined();
            for (Map<byte[], byte[]> hash : hashs) {
                jedisPipeline.hmset(key.getBytes(), hash);
            }
            jedisPipeline.syncAndReturnAll();
        } finally {
            jedisPool.returnResource(jedis);
        }
    }


    /**
     * Increments the number stored at field in the hash stored at key by increment.
     * If key does not exist, a new key holding a hash is created.
     * If field does not exist the value is set to 0 before the operation is performed.
     */
    protected Long hincrBy(String key, byte[] field, long value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key.getBytes(), field, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Get the value of key.
     * If the key does not exist the special value nil is returned.
     * An error is returned if the value stored at key is not a string, because GET only handles string values.
     */
    protected byte[] get(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Set key to hold the string value.
     * If key already holds a value, it is overwritten, regardless of its type.
     * Any previous time to live associated with the key is discarded on successful SET operation.
     */
    protected void set(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Set key to hold the string value and set key to timeout after a given number of seconds.
     * This command is equivalent to executing the following commands:
     * <p>SET mykey value<br/>
     * EXPIRE mykey seconds</p>
     */
    protected void setex(String key, int seconds, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key.getBytes(), seconds, value);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Set key to hold string value if key does not exist.
     * In that case, it is equal to SET.
     * When key already holds a value, no operation is performed.
     * SETNX is short for "SET if Not eXists".
     */
    protected boolean setnx(String key, byte[] value) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long r = jedis.setnx(key.getBytes(), value);
            return r != null && r.intValue() == 1;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns the remaining time to live of a key that has a timeout.
     * This introspection capability allows a Redis client to check how many seconds a given key will continue to be part of the dataset.
     *
     * @return TTL in seconds, or a negative value.
     */
    protected Long getTTL(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.ttl(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Increments the number stored at key by one.
     * If the key does not exist, it is set to 0 before performing the operation.
     * An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer.
     */
    protected Long incr(String key) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key.getBytes());
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * Returns the rank of member in the sorted set stored at key, with the scores ordered from high to low.
     * The rank (or index) is 0-based, which means that the member with the highest score has rank 0.
     * Use {@linkplain #zrank(String, byte[])} to get the rank of an element with the scores ordered from low to high.
     */
    protected Long zrevrank(String key, byte[] member) {
        ShardedJedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrank(key.getBytes(), member);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}

package com.biz.bizunited.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;

public final class RedisUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);

    private static ShardedJedisPool JEDIS_POOL;

    public static byte[] currentTs() {
        return toByteArray(System.currentTimeMillis());
    }

    public static byte[] toByteArray(boolean value) {
        return (value ? "1" : "0").getBytes();
    }

    public static byte[] toByteArray(byte value) {
        return Byte.toString(value).getBytes();
    }

    public static byte[] toByteArray(short value) {
        return Short.toString(value).getBytes();
    }

    public static byte[] toByteArray(int value) {
        return Integer.toString(value).getBytes();
    }

    public static byte[] toByteArray(long value) {
        return Long.toString(value).getBytes();
    }

    public static byte[] toByteArray(java.util.Date value) {
        if (value == null)
            return null;
        return Long.toString(value.getTime()).getBytes();
    }

    public static byte[] toByteArray(String value) {
        if (value == null)
            return null;
        return value.getBytes();
    }

    public static byte[] toByteArray6(BigDecimal value) {
        return toByteArray(value.movePointRight(6).intValue());
    }

    public static BigDecimal byteArrayToBigDecimal6(byte[] b) {
        int intValue = byteArrayToInt(b);
        return new BigDecimal(intValue).movePointLeft(6);
    }

    public static byte[][] toByteArray(int[] value) {
        byte[][] result = new byte[value.length][];
        for (int i = 0; i < value.length; i++) {
            result[i] = toByteArray(value[i]);
        }
        return result;
    }


    public static byte[][] toByteArray(List<Integer> list) {
        byte[][] result = new byte[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            result[i] = toByteArray(list.get(i));
        }
        return result;
    }

    public static byte[][] toByteArray(long[] value) {
        byte[][] result = new byte[value.length][];
        for (int i = 0; i < value.length; i++) {
            result[i] = toByteArray(value[i]);
        }
        return result;
    }

    public static int byteArrayToInt(byte[] b) {
        return Integer.parseInt(new String(b));
    }

    public static long byteArrayToLong(byte[] b) {
        return Long.parseLong(new String(b));
    }

    public static short byteArrayToShort(byte[] b) {
        return Short.parseShort(new String(b));
    }

    public static byte byteArrayToByte(byte[] b) {
        return Byte.parseByte(new String(b));
    }

    public static boolean byteArrayToBoolean(byte[] b) {
        return "1".equals(new String(b));
    }

    public static String byteArrayToStr(byte[] b) {
        return b == null ? null : new String(b);
    }

    public static byte[] toBitByteArray(long value) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((value >> offset) & 0xff);
        }
        return byteNum;
    }

    public static byte[] toBitByteArray(int value) {
        byte[] byteNum = new byte[4];
        for (int ix = 0; ix < 4; ++ix) {
            int offset = 32 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((value >> offset) & 0xff);
        }
        return byteNum;
    }


    public static int bitByteArrayToInt(byte[] b) {
        int num = 0;
        for (int ix = 0; ix < b.length; ++ix) {
            num <<= 8;
            num |= (b[ix] & 0xff);
        }
        return num;
    }

    public static long bitByteArrayToLong(byte[] b) {
        long num = 0;
        for (int ix = 0; ix < b.length; ++ix) {
            num <<= 8;
            num |= (b[ix] & 0xff);
        }
        return num;
    }


    /**
     * @param bytesValue
     * @return
     * @author defei
     * @date 2014年9月3日
     */
    public static Date byteArrayToDate(byte[] bytesValue) {
        long longValue = byteArrayToLong(bytesValue);
        return new Date(longValue);
    }

    /**
     * @param bytesSet
     * @return
     * @author defei
     * @date 2014年10月24日
     */
    public static int[] bytesSetToIntArray(Set<byte[]> bytesSet) {
        if (bytesSet == null || bytesSet.isEmpty()) {
            return null;
        }

        int[] userIdArr = new int[bytesSet.size()];
        int count = 0;
        for (byte[] userIdBytes : bytesSet) {
            userIdArr[count] = byteArrayToInt(userIdBytes);
            count++;
        }
        return userIdArr;
    }

    public static List<Integer> bytesSetToIntList(Set<byte[]> bytesSet) {
        if (bytesSet == null || bytesSet.isEmpty()) {
            return  new ArrayList<Integer>();
        }

        List<Integer> userIdArr = new ArrayList<>(bytesSet.size());
        for (byte[] userIdBytes : bytesSet) {
            userIdArr.add(byteArrayToInt(userIdBytes));
        }
        return userIdArr;
    }

    public static Long[] bytesSetToLongArray(Set<byte[]> bytesSet) {
        if (CollectionUtils.isEmpty(bytesSet)) {
            return null;
        }
        Long[] idArr = new Long[bytesSet.size()];
        int count = 0;
        for (byte[] userIdBytes : bytesSet) {
            idArr[count] = byteArrayToLong(userIdBytes);
            count++;
        }
        return idArr;
    }

    public static List<Long> bytesSetToLongList(Set<byte[]> bytesSet) {
        if (CollectionUtils.isEmpty(bytesSet)) {
            return null;
        }
        List<Long> idList = new ArrayList<Long>(bytesSet.size());
        for (byte[] userIdBytes : bytesSet) {
            idList.add(byteArrayToLong(userIdBytes));
        }
        return idList;
    }

    public static String[] bytesSetToStringArray(Set<byte[]> bytesSet) {
        if (bytesSet == null || bytesSet.isEmpty()) {
            return null;
        }

        String[] userIdArr = new String[bytesSet.size()];
        int count = 0;
        for (byte[] userIdBytes : bytesSet) {
            userIdArr[count] = byteArrayToStr(userIdBytes);
            count++;
        }
        return userIdArr;
    }

    public static List<Long> stringSetToLongList(Set<String> stringSet) {
        if (CollectionUtils.isEmpty(stringSet)) {
            return null;
        }
        List<Long> idList = new ArrayList<Long>(stringSet.size());
        for (String s : stringSet) {
            idList.add(Long.valueOf(s));
        }
        return idList;
    }

    /**
     * 获取redis 连接池, 支持多host主机列表配置
     *
     * @return
     * @author defei
     */
    synchronized public static ShardedJedisPool getJedisPool() {
        if (JEDIS_POOL == null) {
            LOG.debug("开始初始化redis池对象");

            PropertiesConfiguration config = null;
            try {
                config = new PropertiesConfiguration("redis.properties");
            } catch (ConfigurationException e) {
                throw new RuntimeException("加载redis配置出错", e);
            }

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(config.getInt("redis.pool.maxTotal"));
            poolConfig.setMaxIdle(config.getInt("redis.pool.maxIdle"));
            poolConfig.setTimeBetweenEvictionRunsMillis(
                config.getLong("redis.pool.timeBetweenEvictionRunsMillis"));
            poolConfig.setMinEvictableIdleTimeMillis(
                config.getLong("redis.pool.minEvictableIdleTimeMillis"));
            poolConfig.setTestOnBorrow(config.getBoolean("redis.pool.testOnBorrow"));


            LOG.debug("开始加载redis主机列表");
            String ipKeyPrefix = "redis.ip"; // redis主机ip配置前缀
            String portKeyPrefix = "redis.port"; // redis主机port配置前缀
            String nameKeyPrefix = "redis.name"; // redis主机name配置前缀
            List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
            for (Iterator<String> keys = config.getKeys(); keys.hasNext(); ) {
                String key = keys.next();
                if (key.startsWith(ipKeyPrefix)) {
                    // 发现redis主机配置
                    String host = config.getString(key);
                    String hostNo = key.substring(ipKeyPrefix.length());
                    String name = config.getString(nameKeyPrefix + hostNo);
                    int port = config.getInt(portKeyPrefix + hostNo);
                    LOG.debug("redis主机, no: {}, host: {}, port: {}, name: {}", hostNo, host, port,
                        name);
                    shards.add(new JedisShardInfo(host, port,
                        config.getInt("redis.pool.socketTimeout", 2000), name));
                }
            }

            if (shards.isEmpty()) {
                throw new RuntimeException("请指定至少一个redis主机");
            }
            JEDIS_POOL = new ShardedJedisPool(poolConfig, shards, new MyHashing());
        }
        return JEDIS_POOL;

    }

    public static class MyHashing implements Hashing {

        static final String keys = "global user shop colony timeline deal";

        /**
         * @see redis.clients.util.Sharded#
         * 把 全局放在第一个,如果没有找到已经分组的就放在 global中
         * @see redis.clients.util.Sharded#getShardInfo
         * user shop timeline colony global
         */
        @Override public long hash(String key) {
            if (key.indexOf("SHARD-") == 0) {
                return 0;
            } else {
                return keys.indexOf(StringUtils.substringBefore(key, "*"));
            }
        }

        /**
         * @see redis.clients.util.Sharded#getShardInfo(byte[] key)
         */
        @Override public long hash(byte[] key) {
            return keys.indexOf(StringUtils.substringBefore(new String(key), ":"));
        }
    }

}

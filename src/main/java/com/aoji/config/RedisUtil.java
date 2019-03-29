package com.aoji.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author yangsaixing
 * @description Redis操作工具类
 * @date Created in 上午9:22 2017/10/17
 */
@Component
public class RedisUtil {

        @Autowired
        private RedisTemplate redisTemplate;

        /**
         * 批量删除对应的value
         *
         * @param keys
         */
        public void remove(final String... keys) {
            for (String key : keys) {
                remove(key);
            }
        }
        /**
         * 批量删除key
         *
         * @param pattern
         */
        public void removePattern(final String pattern) {
            Set<Serializable> keys = redisTemplate.keys(pattern);
            if (keys.size() > 0){
                redisTemplate.delete(keys);
            }
        }
        /**
         * 删除对应的value
         *
         * @param key
         */
        public void remove(final String key) {
            if (exists(key)) {
                redisTemplate.delete(key);
            }
        }
        /**
         * 判断缓存中是否有对应的value
         *
         * @param key
         * @return
         */
        public boolean exists(final String key) {
            return redisTemplate.hasKey(key);
        }
        /**
         * 读取缓存
         *
         * @param key
         * @return
         */
        public Object get(final String key) {
            Object result = null;
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.get(key);
            return result;
        }
        /**
         * 写入缓存
         *
         * @param key
         * @param value
         * @return
         */
        public boolean set(final String key, Object value) {
            boolean result = false;
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

    /**
     * 设置hash里面的值
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public boolean hset(final String key, String hashKey, Object value) {
        boolean result = false;
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
//            operations.set(key, value);
            operations.put(key, hashKey, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取hash结构中的指定hashKey的值
     * @param key
     * @param hashKey
     * @return
     */
    public Object hGet (final String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 返回hash结构中所的hashKey
     * @param key
     * @return
     */
    public Set<String> hKeys(final String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 将hash结构中的hashKey的值增加1;
     * @param key
     * @param hashKey
     * @return
     */
    public Long hIncr(final String key, String hashKey) {
        return redisTemplate.opsForHash().increment(key, hashKey, 1);
    }

        /**
         * 写入缓存
         *
         * @param key
         * @param value
         * @return
         */
        public boolean set(final String key, Object value, Long expireTime) {
            boolean result = false;
            try {
                ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
                operations.set(key, value);
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

/*    *//**
     * Key值自增
     * @param key
     * @return
     *//*
    public Long incr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        set(key+ CacheKey.INCR,increment);
        return increment;
    }*/
}

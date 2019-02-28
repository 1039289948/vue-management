package com.yotado.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author wangle
 * @date 2018/11/29
 * @description redis工具类
 */

@Service
public class Redis {

    private JedisPool jedisPool;

    public Redis(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Long increment(String key) {
        Jedis jedis = null;
        Long result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incr(key);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public void lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key, value);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public String lpop(String key) {
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lpop(key);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public String set(String key, String value) {
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public String set(String key, String value, int timeout) {
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
            jedis.expire(key, timeout);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hget(key, field);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public String get(String key) {
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } finally {
            if (jedis != null) jedis.close();
        }
        return result;
    }

    public Integer getInt(String key) {
        Jedis jedis = null;
        String result;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
            if(StringUtils.isNotBlank(result)){
                return Integer.parseInt(result);
            }else{
                return 0;
            }
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public void del(final String key){
        if (StringUtils.isEmpty(key)) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

    public boolean exists(final String key){
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            if (jedis != null) jedis.close();
        }
    }

}

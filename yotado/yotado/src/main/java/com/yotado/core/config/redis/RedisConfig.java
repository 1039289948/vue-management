package com.yotado.core.config.redis;

import com.yotado.core.config.property.RedisProperty;
import com.yotado.core.utils.Redis;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author wangle
 * @date 2018/11/29
 * @description redis连接池配置
 */

@Configuration
public class RedisConfig {

    @Autowired
    RedisProperty redisProperty;

    @Bean
    @Qualifier("redisPool")
    public JedisPool jedisPool() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig, redisProperty.getHost(), redisProperty.getPort(),
                redisProperty.getTimeout(), redisProperty.getPassword(), redisProperty.getDatabase());
        return jedisPool;
    }

    @Bean
    public Redis jedisClientPool(@Qualifier("redisPool") JedisPool jedisPool) {
        Redis redis = new Redis(jedisPool);
        return redis;
    }

}

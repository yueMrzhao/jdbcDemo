package com.zy.conf;

import com.zy.comm.model.JedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zy on 2017/4/2.
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisProperties getRedisPo(){
        return new RedisProperties();
    }

    @Bean
    public JedisTemplate jedisTemplateBean() {
        JedisTemplate jedis = new JedisTemplate(redisProperties.getIpAddress(),
                redisProperties.getPort(),
                redisProperties.getPassword(),
                redisProperties.getMaxTotal(),
                redisProperties.getMaxIdle(),
                redisProperties.getWaitMillis(),
                redisProperties.getTimeout(),
                redisProperties.isTestOnBorrow());

        return jedis;

    }



}

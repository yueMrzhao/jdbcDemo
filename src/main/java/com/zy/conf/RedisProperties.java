package com.zy.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zy on 2017/4/2.
 */
@ConfigurationProperties("spring.redis")
public class RedisProperties {

    private JedisPool jedisPool;
    private JedisPoolConfig config;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer waitMillis;
    private Integer timeout;
    private boolean testOnBorrow;
    private String ipAddress;
    private Integer port;
    private String password;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public JedisPoolConfig getConfig() {
        return config;
    }

    public void setConfig(JedisPoolConfig config) {
        this.config = config;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getWaitMillis() {
        return waitMillis;
    }

    public void setWaitMillis(Integer waitMillis) {
        this.waitMillis = waitMillis;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.zy.comm.model;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;

/**
 * Created by zy on 2017/4/2.
 */
public class JedisTemplate {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory
            .getLogger(JedisTemplate.class);
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

    public JedisTemplate(String ip, Integer port, String password, Integer maxTotal,
                         Integer maxIdle, Integer waitMillis, Integer timeout,
                         boolean testOnBorrow) {
        this.maxTotal = maxTotal;
        this.maxIdle = maxIdle;
        this.waitMillis = waitMillis;
        this.timeout = timeout;
        this.testOnBorrow = testOnBorrow;
        this.ipAddress = ip;
        this.port = port;
        this.password = password;
        initialPool();
    }

    private void initialPool() {
        config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(waitMillis);
        config.setTestOnBorrow(testOnBorrow);
    }

    private Jedis getJedis() {
        Jedis jedis = null;
        if (null == jedisPool) {
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(config, this.ipAddress, this.port, timeout,password);
            }else {
                jedisPool = new JedisPool(config, this.ipAddress, this.port, timeout);
            }
        }
        try {
            jedis = jedisPool.getResource();
        } catch (JedisConnectionException e) {
            logger.error("连接到  Redis 时出错，验证密码错误或未提供密码",e,this.ipAddress,this.port,this.password);
        }
        return jedis;
    }

    public synchronized void deleteKey(String key) {
        Jedis jedis = null;
        boolean flag = true;
        try {
            jedis = getJedis();
            jedis.del(key);
        } catch (JedisException e) {
            flag = false;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public synchronized void setKeyValue(final String key, final String value)
              {
        Jedis jedis = null;
        boolean flag = true;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (JedisException e) {
            flag = false;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public synchronized String getKeyValue(final String key) {
        String value = null;
        Jedis jedis = null;
        boolean flag = true;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (JedisException e) {
            flag = false;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }

        return value;
    }

    @SuppressWarnings("unused")
    private void clearDB() {
        Jedis jedis = null;
        boolean flag = true;
        try {
            jedis = getJedis();
            jedis.flushDB();
        } catch (JedisException e) {
            flag = false;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }
    }


    public synchronized Set<String> getKeys(String pattern) {
        Jedis jedis = null;
        Set<String> keys = new HashSet<String>();
        boolean flag = true;
        try {
            jedis = getJedis();
            keys = jedis.keys(pattern);
        } catch (JedisException e) {
            flag = false;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }
        return keys;
    }

    public synchronized Map<String, String> getKeyValueBatch(
            final List<String> keys) {
        Jedis jedis = null;
        Pipeline pipeline = null;
        boolean flag = true;
        Map<String, Response<String>> response = new HashMap<String, Response<String>>(
                keys.size());
        Map<String, String> result = new HashMap<String, String>();
        try {
            jedis = getJedis();
            pipeline = jedis.pipelined();
            for (String key : keys) {
                response.put(key, pipeline.get(key));
            }
            pipeline.sync();
            for (String k : keys) {
                result.put(k, response.get(k).get());
            }
        } catch (JedisException e) {
            flag = false;
            pipeline = null;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            response = null;
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }

    public synchronized void deleteKey(final List<String> keys) {
        Jedis jedis = null;
        Pipeline pipeline = null;
        boolean flag = true;
        try {
            jedis = getJedis();
            pipeline = jedis.pipelined();
            for (String key : keys) {
                pipeline.del(key);
            }
            pipeline.sync();
        } catch (JedisException e) {
            flag = false;
            pipeline = null;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if (jedisPool != null && jedis != null && flag) {
                jedisPool.returnResource(jedis);
            }
        }
    }


    public synchronized Object hmget(final String mapKey,final String... valKeys)  {
        Object value = null;
        Jedis jedis = null;
        boolean flag = true;
        try {
            jedis = getJedis();
            value = jedis.hmget(mapKey,valKeys);
        } catch (JedisException e) {
            flag = false;
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if(jedisPool != null && jedis !=null && flag){
                jedisPool.returnResource(jedis);
            }
        }
        return value;
    }

    public synchronized void hmset(final String mapKey,final Map<String,String> map) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hmset(mapKey,map);
        } catch (JedisException e) {
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            if(jedisPool != null && jedis !=null){
                jedisPool.returnResource(jedis);
            }
        }
    }


    public static void main(String[] args){
        JedisTemplate client = new JedisTemplate("127.0.0.1", 6379,"", 10000, 5,1000, 60000,false);
        Map<String, String> map = new HashMap<String, String>();
        map.put("mileage", "11");
        map.put("runtime", "222222");
        map.put("fuelConsu", "22");
        map.put("diagScore", "78");
        map.put("driveScore", "56");
        client.hmset("STCURR1207",map);
        client.hmset("STTODAY1207", map);
        System.out.println("hmset 完成");

        /**
         * key: STTODAY47(其中47是车辆id) 需要注意
         * **/
        StringBuffer sb = new StringBuffer();

        ArrayList o = (ArrayList) client.hmget("STCURR1207", "mileage");

        o = (ArrayList) client.hmget("STTODAY47", "mileage", "runtime", "fuelConsu", "diagScore", "driveScore","updateTime");

        /** 注意返回的list<String> 需要解析 **/
        System.out.println(JSON.toJSON(o));
        System.out.println(o.get(5));
        System.out.println(Double.parseDouble(o.get(0).toString())/(Double.parseDouble(o.get(0).toString())/3600));
    }
}

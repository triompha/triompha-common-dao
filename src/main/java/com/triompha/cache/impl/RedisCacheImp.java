package com.triompha.cache.impl;

import java.io.Serializable;
import org.apache.commons.lang.SerializationUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.triompha.cache.Cache;



public class RedisCacheImp implements Cache {
	
    private JedisPool  jedisPool;
    

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public Object get(String key) {
		Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] obj = jedis.get(key.getBytes());
            if(obj!=null) return   SerializationUtils.deserialize(obj);
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != jedisPool) {
            	jedisPool.returnResource(jedis);
            }
        }
        return null;
    }

	@Override
	public void put(String key, Serializable value) {
		Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String rest = jedis.set(key.getBytes(), SerializationUtils.serialize(value));
            System.out.println(rest);

        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != jedisPool) {
            	jedisPool.returnResource(jedis);
            }
        }

    }

	@Override
	public void remove(String key) {
		Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
           
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != jedisPool) {
            	jedisPool.returnResource(jedis);
            }
        }
    }

	@Override
	public void put(String key, Serializable value, int ttl) {
		Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key.getBytes() ,ttl, SerializationUtils.serialize(value));
        } catch (Exception e) {
            e.printStackTrace();
            jedisPool.returnBrokenResource(jedis);
        } finally {
            if (null != jedisPool) {
            	jedisPool.returnResource(jedis);
            }
        }

    }
    
}

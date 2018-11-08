package com.lv.zupu.base.redis;

import org.apache.ibatis.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/7/12 17:26
 * @Version: 1.0
 * modified by:
 */
@Component
public class RedisCache<String,V> implements Cache<String, V>{
    @Autowired
    @Qualifier(value = "redisTemplate")
    private  RedisTemplate<String, V> redisTemplate;
    @Override
    public V get(String var1) throws CacheException {
        return (V) redisTemplate.opsForValue().get(var1);
    }

    @Override
    public void set(String var1, V var2) throws CacheException {
        redisTemplate.opsForValue().set(var1,var2);
    }
    @Override
    public void set(String var1, V var2, long expiretime, TimeUnit timeUnit) throws CacheException {
        redisTemplate.opsForValue().set(var1,var2,expiretime,timeUnit);
    }
    @Override
    public Boolean remove(String var1) throws CacheException {
        return redisTemplate.delete(var1);
    }

    @Override
    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }
}

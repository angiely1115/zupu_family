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
public class RedisCache<K, V> implements Cache<K, V>{
    @Autowired
    private RedisTemplate<K,V> redisCacheTemplate;
    @Override
    public V get(K var1) throws CacheException {
        return (V) redisCacheTemplate.opsForValue().get(var1);
    }

    @Override
    public void set(K var1, V var2) throws CacheException {
        redisCacheTemplate.opsForValue().set(var1,var2);
    }
    @Override
    public void set(K var1, V var2, long expiretime, TimeUnit timeUnit) throws CacheException {
        redisCacheTemplate.opsForValue().set(var1,var2,expiretime,timeUnit);
    }
    @Override
    public Boolean remove(K var1) throws CacheException {
        return redisCacheTemplate.delete(var1);
    }

    @Override
    public Boolean isExist(K key) {
        return redisCacheTemplate.hasKey(key);
    }
}

package com.lv.zupu.base.redis;

import org.apache.ibatis.cache.CacheException;

import java.util.concurrent.TimeUnit;

public interface Cache<K, V> {
    V get(K var1) throws CacheException;

    void set(K var1, V var2) throws CacheException;

    void set(K var1, V var2, long expiretime, TimeUnit timeUnit) throws CacheException;

    Boolean isExist(K key);

    Boolean remove(K var1) throws CacheException;


}
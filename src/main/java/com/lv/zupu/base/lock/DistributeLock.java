package com.lv.zupu.base.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 分布式锁
 * @param <T>
 */
public class DistributeLock<T> {
    private static final Logger log = LoggerFactory.getLogger(DistributeLock.class);
    private StringRedisTemplate stringRedisTemplate;
    private final String lockName;
    //抢到锁保持时间
    private  long expireTime = 60L;
    private  int waitInterval = 10 * 1000;
    //锁等待时间,防止线程饥饿
    private  int waitTimeout = 100;
    //间隔
    private  boolean returnOnError = false;
    public T lock(Supplier<T> redisLoader) {
        String uuid = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        String var5 = this.lockName;
        synchronized(this.lockName) {
            try {
                while(System.currentTimeMillis() <= startTime + (long)this.waitTimeout) {
                    boolean locked = this.tryLock(uuid);
                    if (locked) {
                        Object var7 = redisLoader.get();
                        return (T) var7;
                    }

                    try {
                        Thread.sleep((long)this.waitInterval);
                    } catch (InterruptedException var13) {
                        Thread.currentThread().interrupt();
                    }
                }

                throw new RuntimeException("操作超时");
            } finally {
                this.unLock(uuid);
                log.debug("lock info. name: " + this.lockName + " time: " + (System.currentTimeMillis() - startTime) + "ms");
            }
        }
    }

    private boolean tryLock(String uuid) {
        try {
            if (((Boolean) Optional.ofNullable(this.stringRedisTemplate.opsForValue().setIfAbsent(this.lockName, uuid)).orElse(false)).booleanValue()) {
                this.stringRedisTemplate.expire(this.lockName, this.expireTime, TimeUnit.SECONDS);
                return true;
            } else {
                return false;
            }
        } catch (Exception var3) {
            log.error(var3.getMessage(), var3);
            return this.returnOnError;
        }
    }

    private void unLock(String uuid) {
        try {
            if (uuid.equals(this.stringRedisTemplate.opsForValue().get(this.lockName))) {
                this.stringRedisTemplate.delete(this.lockName);
            }
        } catch (Exception var3) {
            log.error(var3.getMessage(), var3);
        }

    }

    public DistributeLock(StringRedisTemplate stringRedisTemplate, String lockName, long expireTime, int waitInterval, int waitTimeout, boolean returnOnError) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        this.expireTime = expireTime;
        this.waitInterval = waitInterval;
        this.waitTimeout = waitTimeout;
        this.returnOnError = returnOnError;
    }

    public DistributeLock(StringRedisTemplate stringRedisTemplate, String lockName) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
    }
}
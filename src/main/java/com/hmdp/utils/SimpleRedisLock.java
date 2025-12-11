package com.hmdp.utils;

import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements ILock{

    private String name;
    private StringRedisTemplate stringRedisTemplate;

    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private static final String KEY_PREFIX = "lock:";

    @Override
    public boolean tryLock(long timeoutSec) {
        // 获取线程标示
        long threadId = Thread.currentThread().getId();
        // 获取锁
        String key = KEY_PREFIX + name;
        Boolean success = stringRedisTemplate.opsForValue().
                setIfAbsent(key, threadId + "", timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success); // 防止自动拆箱空指针异常
    }

    @Override
    public void unlock() {
        //通过del删除锁
        stringRedisTemplate.delete(KEY_PREFIX + name);
    }
}

package com.flash.framework.mybatis.support.cache.redis;

import com.alibaba.fastjson.JSON;
import com.flash.framework.mybatis.support.cache.CacheInvoker;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author zhurg
 * @date 2020/4/28 - 10:07 PM
 */
@AllArgsConstructor
public class RedisCacheInvoker implements CacheInvoker {

    private final StringRedisTemplate redisTemplate;

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, Object object, long expire) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(object), expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(String... keys) {
        redisTemplate.delete(Lists.newArrayList(keys));
    }
}

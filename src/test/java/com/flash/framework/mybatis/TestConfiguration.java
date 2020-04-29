package com.flash.framework.mybatis;

import com.flash.framework.mybatis.support.cache.CacheInvoker;
import com.flash.framework.mybatis.support.cache.redis.RedisCacheInvoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author zhurg
 * @date 2020/4/29 - 7:36 PM
 */
@Configuration
public class TestConfiguration {

    @Bean
    public CacheInvoker cacheInvoker(StringRedisTemplate redisTemplate) {
        return new RedisCacheInvoker(redisTemplate);
    }
}
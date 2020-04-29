package com.flash.framework.mybatis.support.cache.generator;

import com.flash.framework.mybatis.support.cache.MybatisCache;

import java.lang.reflect.Method;

/**
 * 缓存key生成器
 *
 * @author zhurg
 * @date 2020/4/26 - 9:16 PM
 */
@FunctionalInterface
public interface CacheKeyGenerator {

    /**
     * 生成缓存key
     *
     * @param target
     * @param method
     * @param args
     * @param mybatisCache
     * @return
     */
    String generateKey(Object target, MybatisCache mybatisCache, Method method, Object... args);
}
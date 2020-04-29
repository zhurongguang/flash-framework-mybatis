package com.flash.framework.mybatis.support.cache;

import com.flash.framework.mybatis.support.cache.generator.CacheKeyGenerator;
import com.flash.framework.mybatis.support.cache.generator.IdCacheKeyGenerator;

import java.lang.annotation.*;

/**
 * @author zhurg
 * @date 2019/3/28 - 下午8:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MybatisCache {

    /**
     * 缓存域
     *
     * @return
     */
    String scope() default "mybatis_cache";

    /**
     * 缓存操作
     *
     * @return
     */
    String operation() default CacheOperation.SELECT_BY_ID;

    /**
     * 缓存key生成器
     *
     * @return
     */
    Class<? extends CacheKeyGenerator> keyGenerator() default IdCacheKeyGenerator.class;

    /**
     * 缓存过期时间
     *
     * @return
     */
    long expireTime() default 1000 * 60 * 10L;
}
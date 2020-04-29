package com.flash.framework.mybatis.support.cache.handler;

/**
 * Mybatis 自定义缓存处理接口
 *
 * @author zhurg
 * @date 2020/4/26 - 9:07 PM
 */
public interface MybatisCacheQueryHandler {

    /**
     * 从缓存中查询数据
     *
     * @param key
     * @return
     */
    Object queryCache(String key);

    /**
     * 设置缓存
     *
     * @param key
     * @param object
     * @param expire
     */
    void putCache(String key, Object object, long expire);


    /**
     * 进行缓存的方法
     *
     * @return
     */
    String cacheMethod();
}
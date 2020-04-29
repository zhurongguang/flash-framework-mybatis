package com.flash.framework.mybatis.support.cache.handler;

/**
 * Mybatis 自定义缓存处理接口
 *
 * @author zhurg
 * @date 2020/4/26 - 9:07 PM
 */
public interface MybatisCacheUpdateHandler {

    /**
     * 批量更新缓存
     *
     * @param key
     */
    void updateCaches(String... key);

    /**
     * 清理缓存的方法
     *
     * @return
     */
    String cacheMethod();
}
package com.flash.framework.mybatis.support.cache;

/**
 * @author zhurg
 * @date 2020/4/26 - 9:59 PM
 */
public interface CacheInvoker {

    /**
     * 查询缓存
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 更新缓存
     *
     * @param key
     * @param object
     * @param expire
     */
    void set(String key, Object object, long expire);

    /**
     * 删除缓存
     *
     * @param key
     */
    void delete(String key);

    /**
     * 删除缓存
     *
     * @param keys
     */
    void delete(String... keys);
}
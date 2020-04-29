package com.flash.framework.mybatis.support.cache.handler.impl;

import com.flash.framework.mybatis.support.cache.CacheInvoker;
import com.flash.framework.mybatis.support.cache.handler.MybatisCacheUpdateHandler;
import lombok.AllArgsConstructor;

/**
 * @author zhurg
 * @date 2020/4/27 - 8:39 PM
 */
@AllArgsConstructor
public abstract class AbstractUpdateByIdCacheHandler implements MybatisCacheUpdateHandler {

    private final CacheInvoker cacheInvoker;

    @Override
    public void updateCaches(String... keys) {
        cacheInvoker.delete(keys);
    }
}
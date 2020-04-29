package com.flash.framework.mybatis.support.cache.handler.impl;

import com.flash.framework.mybatis.support.cache.CacheInvoker;
import com.flash.framework.mybatis.support.cache.handler.MybatisCacheQueryHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author zhurg
 * @date 2020/4/26 - 9:55 PM
 */
@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
public class GetByIdCacheHandler implements MybatisCacheQueryHandler {


    private final CacheInvoker cacheInvoker;

    @Override
    public Object queryCache(String key) {
        return cacheInvoker.get(key);
    }

    @Override
    public void putCache(String key, Object object, long expire) {
        cacheInvoker.set(key, object, expire);
    }

    @Override
    public String cacheMethod() {
        return "getById";
    }
}

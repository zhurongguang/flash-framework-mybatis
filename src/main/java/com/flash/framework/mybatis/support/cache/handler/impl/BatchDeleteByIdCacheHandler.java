package com.flash.framework.mybatis.support.cache.handler.impl;

import com.flash.framework.mybatis.support.cache.CacheInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author zhurg
 * @date 2020/4/27 - 8:38 PM
 */
@Component
@ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
public class BatchDeleteByIdCacheHandler extends AbstractUpdateByIdCacheHandler {

    @Autowired
    public BatchDeleteByIdCacheHandler(CacheInvoker cacheInvoker) {
        super(cacheInvoker);
    }

    @Override
    public String cacheMethod() {
        return "removeById";
    }
}
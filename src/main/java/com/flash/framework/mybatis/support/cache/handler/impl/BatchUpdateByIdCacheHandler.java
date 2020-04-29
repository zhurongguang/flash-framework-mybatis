package com.flash.framework.mybatis.support.cache.handler.impl;

import com.flash.framework.mybatis.support.cache.CacheInvoker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author zhurg
 * @date 2020/4/27 - 9:41 PM
 */
@Component
@ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
public class BatchUpdateByIdCacheHandler extends AbstractUpdateByIdCacheHandler {

    public BatchUpdateByIdCacheHandler(CacheInvoker cacheInvoker) {
        super(cacheInvoker);
    }

    @Override
    public String cacheMethod() {
        return "updateBatchById";
    }
}
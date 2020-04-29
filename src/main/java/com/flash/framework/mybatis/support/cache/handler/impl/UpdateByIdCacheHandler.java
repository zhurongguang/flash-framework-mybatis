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
public class UpdateByIdCacheHandler extends AbstractUpdateByIdCacheHandler {

    @Autowired
    public UpdateByIdCacheHandler(CacheInvoker cacheInvoker) {
        super(cacheInvoker);
    }

    @Override
    public String cacheMethod() {
        return "updateById";
    }
}
package com.flash.framework.mybatis.support.cache;

import com.flash.framework.mybatis.support.cache.handler.MybatisCacheQueryHandler;
import com.flash.framework.mybatis.support.cache.handler.MybatisCacheUpdateHandler;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

/**
 * @author zhurg
 * @date 2020/4/26 - 9:44 PM
 */
public class MybatisCacheRegistry {

    private Map<String, MybatisCacheQueryHandler> mybatisCacheQueryHandlers = Maps.newConcurrentMap();

    private Map<String, MybatisCacheUpdateHandler> mybatisCacheUpdateHandlers = Maps.newConcurrentMap();

    @EventListener
    public void init(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        Map<String, MybatisCacheQueryHandler> queryHandlers = applicationContext.getBeansOfType(MybatisCacheQueryHandler.class);
        if (MapUtils.isNotEmpty(queryHandlers)) {
            queryHandlers.values().forEach(queryHandler -> mybatisCacheQueryHandlers.put(queryHandler.cacheMethod(), queryHandler));
        }
        Map<String, MybatisCacheUpdateHandler> updateHandlers = applicationContext.getBeansOfType(MybatisCacheUpdateHandler.class);
        if (MapUtils.isNotEmpty(updateHandlers)) {
            updateHandlers.values().forEach(updateHandler -> mybatisCacheUpdateHandlers.put(updateHandler.cacheMethod(), updateHandler));
        }
    }

    public MybatisCacheQueryHandler getCacheQueryHandler(String cacheMethod) {
        if (mybatisCacheQueryHandlers.containsKey(cacheMethod)) {
            return mybatisCacheQueryHandlers.get(cacheMethod);
        }
        return null;
    }

    public MybatisCacheUpdateHandler getCacheUpdateHandler(String cacheMethod) {
        if (mybatisCacheUpdateHandlers.containsKey(cacheMethod)) {
            return mybatisCacheUpdateHandlers.get(cacheMethod);
        }
        return null;
    }
}
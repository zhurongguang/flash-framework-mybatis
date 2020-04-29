package com.flash.framework.mybatis.support.cache.invoker;

import com.flash.framework.mybatis.support.cache.CacheOperation;
import com.flash.framework.mybatis.support.cache.MybatisCache;
import com.flash.framework.mybatis.support.cache.MybatisCacheInvoker;
import com.flash.framework.mybatis.support.cache.MybatisCacheRegistry;
import com.flash.framework.mybatis.support.cache.generator.CacheKeyGenerator;
import com.flash.framework.mybatis.support.cache.handler.MybatisCacheUpdateHandler;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @authzhurg
 * @date 2020/4/27 - 9:13 PM
 */
@Slf4j
@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
public class BatchUpdateMybatisCacheInvoker implements MybatisCacheInvoker {

    private final MybatisCacheRegistry mybatisCacheRegistry;

    private final ApplicationContext applicationContext;

    @Override
    public Object invoke(ProceedingJoinPoint joinPoint, MybatisCache mybatisCache, Method method, Object... args) throws Throwable {
        Object result = joinPoint.proceed();
        MybatisCacheUpdateHandler updateHandler = mybatisCacheRegistry.getCacheUpdateHandler(method.getName());
        if (Objects.nonNull(updateHandler) && args.length == 1 && args[0] instanceof Collection) {
            try {
                Collection<?> collection = (Collection) args[0];
                if (CollectionUtils.isNotEmpty(collection)) {
                    CacheKeyGenerator keyGenerator = applicationContext.getBean(mybatisCache.keyGenerator());
                    List<String> keys = collection.stream()
                            .map(item -> keyGenerator.generateKey(joinPoint.getTarget(), mybatisCache, method, item))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    updateHandler.updateCaches(keys.toArray(new String[]{}));
                }
            } catch (Exception e) {
                log.error("[Flash Framework] mybatis update cache failed,cause:{}", Throwables.getStackTraceAsString(e));
            }
        }
        return result;
    }

    @Override
    public String operation() {
        return CacheOperation.BATCH_UPDATE_BY_ID;
    }
}

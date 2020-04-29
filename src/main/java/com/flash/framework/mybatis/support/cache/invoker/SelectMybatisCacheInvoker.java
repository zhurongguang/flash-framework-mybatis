package com.flash.framework.mybatis.support.cache.invoker;

import com.alibaba.fastjson.JSON;
import com.flash.framework.commons.utils.ReflectUtil;
import com.flash.framework.mybatis.support.cache.CacheOperation;
import com.flash.framework.mybatis.support.cache.MybatisCache;
import com.flash.framework.mybatis.support.cache.MybatisCacheInvoker;
import com.flash.framework.mybatis.support.cache.MybatisCacheRegistry;
import com.flash.framework.mybatis.support.cache.generator.CacheKeyGenerator;
import com.flash.framework.mybatis.support.cache.handler.MybatisCacheQueryHandler;
import com.google.common.base.Throwables;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author zhurg
 * @date 2020/4/27 - 8:54 PM
 */
@Slf4j
@Component
@AllArgsConstructor
@ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
public class SelectMybatisCacheInvoker implements MybatisCacheInvoker {

    private final MybatisCacheRegistry mybatisCacheRegistry;

    private final ApplicationContext applicationContext;

    private final Environment environment;

    @Override
    public Object invoke(ProceedingJoinPoint joinPoint, MybatisCache mybatisCache, Method method, Object... args) throws Throwable {
        MybatisCacheQueryHandler queryHandler = mybatisCacheRegistry.getCacheQueryHandler(method.getName());
        Object result;
        if (Objects.nonNull(queryHandler)) {
            try {
                CacheKeyGenerator keyGenerator = applicationContext.getBean(mybatisCache.keyGenerator());
                String cacheKey = keyGenerator.generateKey(joinPoint.getTarget(), mybatisCache, method, args);
                result = queryHandler.queryCache(cacheKey);
                if (Objects.nonNull(result)) {
                    return JSON.parseObject(result.toString(), ReflectUtil.getGenericClass(joinPoint.getTarget().getClass(), 1));
                }
                result = joinPoint.proceed();
                if (Objects.nonNull(result)) {
                    String prop = String.format("${mybatis.cache.%s.%s.expire}", method.getDeclaringClass().getSimpleName(), method.getName());
                    long expire = mybatisCache.expireTime();
                    if (environment.containsProperty(prop)) {
                        expire = Long.valueOf(environment.resolvePlaceholders(prop));
                    }
                    queryHandler.putCache(cacheKey, result, expire);
                }
            } catch (Exception e) {
                log.error("[Flash Framework] mybatis select cache failed,cause:{}", Throwables.getStackTraceAsString(e));
                result = joinPoint.proceed();
            }
        } else {
            result = joinPoint.proceed();
        }
        return result;
    }

    @Override
    public String operation() {
        return CacheOperation.SELECT_BY_ID;
    }
}

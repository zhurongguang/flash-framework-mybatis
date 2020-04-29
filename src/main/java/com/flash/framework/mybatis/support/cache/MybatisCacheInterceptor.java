package com.flash.framework.mybatis.support.cache;

import com.flash.framework.commons.utils.AopUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhurg
 * @date 2019/3/28 - 下午4:25
 */
@Slf4j
@Aspect
public class MybatisCacheInterceptor {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, MybatisCacheInvoker> mybatisCacheInvokers;

    @PostConstruct
    public void init() {
        mybatisCacheInvokers = Maps.newConcurrentMap();
        Map<String, MybatisCacheInvoker> beans = applicationContext.getBeansOfType(MybatisCacheInvoker.class);
        if (MapUtils.isNotEmpty(beans)) {
            beans.values().forEach(mybatisCacheInvoker -> mybatisCacheInvokers.put(mybatisCacheInvoker.operation(), mybatisCacheInvoker));
        }
    }

    @Around(value = "@annotation(com.flash.framework.mybatis.support.cache.MybatisCache)")
    public Object operator(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = AopUtils.getMethod(joinPoint);
        Object[] args = joinPoint.getArgs();
        MybatisCache mybatisCache = AnnotationUtils.findAnnotation(method, MybatisCache.class);
        EnableMybatisCache enableMybatisCache = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(), EnableMybatisCache.class);
        if (Objects.nonNull(enableMybatisCache) && mybatisCacheInvokers.containsKey(mybatisCache.operation())) {
            return mybatisCacheInvokers.get(mybatisCache.operation()).invoke(joinPoint, mybatisCache, method, args);
        }
        return joinPoint.proceed();
    }
}
package com.flash.framework.mybatis.support.cache;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * Mybatis 缓存执行器
 *
 * @author zhurg
 * @date 2020/4/27 - 8:48 PM
 */
public interface MybatisCacheInvoker {

    /**
     * 执行dao+缓存操作
     *
     * @param joinPoint
     * @param mybatisCache
     * @param method
     * @param args
     * @return
     */
    Object invoke(ProceedingJoinPoint joinPoint, MybatisCache mybatisCache, Method method, Object... args) throws Throwable;

    /**
     * 缓存操作
     *
     * @return
     */
    String operation();
}
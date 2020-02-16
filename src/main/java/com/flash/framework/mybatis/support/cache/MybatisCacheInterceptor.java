package com.flash.framework.mybatis.support.cache;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.flash.framework.commons.utils.AopUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.CacheManager;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

/**
 * @author zhurg
 * @date 2019/3/28 - 下午4:25
 */
@Slf4j
@Aspect
public class MybatisCacheInterceptor {

    protected final CacheManager cacheManager;

    protected static final String BASE_MYBATIS_CACHE = "mybatis_cache";

    protected static final String DELETE_BY_ID = "removeById";

    protected static final String DELETE_BATCH_IDS = "removeByIds";

    protected static final String UPDATE_BY_ID = "updateById";

    protected static final String UPDATE_BATCH_IDS = "updateBatchById";

    protected static final String SELECT_BY_ID = "getById";

    public MybatisCacheInterceptor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Around(value = "@annotation(com.flash.framework.mybatis.support.cache.Cache)")
    public Object operator(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = AopUtils.getMethod(joinPoint);
        Object[] args = joinPoint.getArgs();
        String methodName = method.getName();
        switch (methodName) {
            case SELECT_BY_ID:
                if (isEmpty(args)) return null;
                Object obj = null;
                try {
                    obj = cacheManager.getCache(BASE_MYBATIS_CACHE).get(cacheKey(method, args[0])).get();
                } catch (Exception e) {
                    log.warn("[MybatisCacheInterceptor] method {} param {} query cache fail", method.getDeclaringClass().getCanonicalName() + "." + methodName, args[0], Throwables.getStackTraceAsString(e));
                }
                if (null == obj) {
                    obj = joinPoint.proceed();
                    if (null != obj) {
                        try {
                            cacheManager.getCache(BASE_MYBATIS_CACHE).put(cacheKey(method, args[0]), obj);
                        } catch (Exception e) {
                            log.error("[MybatisCacheInterceptor] method {} param {} put cache fail", method.getDeclaringClass().getCanonicalName() + "." + methodName, args[0], e);
                        }
                    }
                }
                return obj;
            case UPDATE_BY_ID:
                if (!isEmpty(args)) {
                    Object primaryKey = getPrimaryKey(args[0]);
                    if (null != primaryKey) {
                        evit(method, primaryKey);
                    }
                }
                return joinPoint.proceed();
            case UPDATE_BATCH_IDS:
                if (!isEmpty(args)) {
                    Collection collection = (Collection) args[0];
                    if (!CollectionUtils.isEmpty(collection)) {
                        collection.stream().filter(o -> Objects.nonNull(getPrimaryKey(o))).forEach(o -> evit(method, getPrimaryKey(o)));
                    }
                }
                return joinPoint.proceed();
            case DELETE_BY_ID:
                if (!isEmpty(args))
                    evit(method, args[0]);
                return joinPoint.proceed();
            case DELETE_BATCH_IDS:
                if (!isEmpty(args)) {
                    Collection collection = (Collection) args[0];
                    if (!CollectionUtils.isEmpty(collection)) {
                        collection.forEach(id -> evit(method, id));
                    }
                }
                return joinPoint.proceed();
            default:
                return joinPoint.proceed();
        }
    }


    private boolean isEmpty(Object[] args) {
        return null == args || args.length == 0;
    }

    /**
     * 获取主键
     *
     * @param obj
     * @return
     */
    protected Object getPrimaryKey(Object obj) {
        try {
            Field[] fields = FieldUtils.getFieldsWithAnnotation(obj.getClass(), TableId.class);
            if (null != fields && fields.length > 0) {
                return MethodUtils.getMatchingMethod(obj.getClass(), "get" + fields[0].getName()).invoke(obj);
            }
        } catch (Exception e) {
            log.warn("[MybatisCacheInterceptor] {} getPrimaryKey fail", JSON.toJSONString(obj), Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * 生成缓存key
     *
     * @param method
     * @param arg
     * @return
     */
    protected String cacheKey(Method method, Object arg) {
        StringBuffer key = new StringBuffer();
        key.append(method.getDeclaringClass().getSimpleName())
                .append(":")
                .append(arg.toString());
        return key.toString();
    }

    /**
     * 删除缓存
     *
     * @param method
     * @param id
     */
    protected void evit(Method method, Object id) {
        try {
            cacheManager.getCache(BASE_MYBATIS_CACHE).evict(cacheKey(method, id));
        } catch (Exception e) {
            log.error("[MybatisCacheInterceptor] method {} param {} evict cache fail", method.getDeclaringClass().getCanonicalName() + "." + method.getName(), id, e);
        }
    }
}
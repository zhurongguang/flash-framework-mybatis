package com.flash.framework.mybatis.support.cache.generator;

import com.baomidou.mybatisplus.annotation.TableId;
import com.flash.framework.mybatis.exception.MybatisCacheException;
import com.flash.framework.mybatis.support.cache.MybatisCache;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * ById方法缓存key生成器
 *
 * @author zhurg
 * @date 2020/4/26 - 9:26 PM
 */
@Component
@ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
public class IdCacheKeyGenerator implements CacheKeyGenerator {

    @Override
    public String generateKey(Object target, MybatisCache mybatisCache, Method method, Object... args) {
        Object id = null;
        if (args[0] instanceof Long || args[0] instanceof String || args[0] instanceof Integer || args[0].getClass().isPrimitive()) {
            id = args[0];
        } else {
            Field[] fields = FieldUtils.getFieldsWithAnnotation(args[0].getClass(), TableId.class);
            if (Objects.nonNull(fields) && fields.length == 1) {
                try {
                    id = FieldUtils.readDeclaredField(args[0], fields[0].getName(), true);
                } catch (IllegalAccessException e) {
                    throw new MybatisCacheException(e);
                }
            }
        }
        return String.format("%s:%s:%s", mybatisCache.scope(), target.getClass().getSimpleName(), id.toString());
    }
}
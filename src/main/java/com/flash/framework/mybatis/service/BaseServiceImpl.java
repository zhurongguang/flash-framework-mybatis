package com.flash.framework.mybatis.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flash.framework.mybatis.BaseModel;
import com.flash.framework.mybatis.mapper.AbstractMapper;
import com.flash.framework.mybatis.support.cache.CacheOperation;
import com.flash.framework.mybatis.support.cache.MybatisCache;

import java.io.Serializable;
import java.util.Collection;

/**
 * 基础ServiceImpl
 *
 * @author zhurg
 * @date 2019/1/20 - 下午10:15
 */
public abstract class BaseServiceImpl<Mapper extends AbstractMapper<T>, T extends BaseModel>
        extends ServiceImpl<Mapper, T> implements BaseService<T> {

    @Override
    @MybatisCache
    public T getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @MybatisCache(operation = CacheOperation.UPDATE_BY_ID)
    public boolean updateById(T entity) {
        return super.updateById(entity);
    }

    @Override
    @MybatisCache(operation = CacheOperation.BATCH_UPDATE_BY_ID)
    public boolean updateBatchById(Collection<T> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    @MybatisCache(operation = CacheOperation.UPDATE_BY_ID)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @MybatisCache(operation = CacheOperation.BATCH_UPDATE_BY_ID)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }
}
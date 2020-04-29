package com.flash.framework.mybatis.support.cache;

/**
 * 缓存操作
 *
 * @author zhurg
 * @date 2020/4/26 - 10:09 PM
 */
public interface CacheOperation {

    String SELECT_BY_ID = "selectById";

    String UPDATE_BY_ID = "updateById";

    String BATCH_UPDATE_BY_ID = "batchUpdateById";
}
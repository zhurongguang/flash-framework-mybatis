package com.flash.framework.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flash.framework.mybatis.BaseModel;

/**
 * 基础Mapper
 *
 * @author zhurg
 * @date 2019/1/20 - 下午10:03
 */
public interface AbstractMapper<T extends BaseModel> extends BaseMapper<T> {

}
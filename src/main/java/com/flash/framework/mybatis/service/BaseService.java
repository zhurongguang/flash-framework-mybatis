package com.flash.framework.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.flash.framework.mybatis.BaseModel;

/**
 * 基础Service
 *
 * @author zhurg
 * @date 2019/1/20 - 下午10:14
 */
public interface BaseService<T extends BaseModel> extends IService<T> {
    
}
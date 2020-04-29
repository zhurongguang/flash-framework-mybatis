package com.flash.framework.mybatis.service.impl;

import com.flash.framework.mybatis.dao.UserDao;
import com.flash.framework.mybatis.model.User;
import com.flash.framework.mybatis.service.BaseServiceImpl;
import com.flash.framework.mybatis.service.UserService;
import com.flash.framework.mybatis.support.cache.EnableMybatisCache;
import org.springframework.stereotype.Service;

/**
 * @author zhurg
 * @date 2020/4/28 - 10:01 PM
 */
@Service
@EnableMybatisCache
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {
}
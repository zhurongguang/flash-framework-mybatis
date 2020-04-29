package com.flash.framework.mybatis.test;

import com.alibaba.fastjson.JSON;
import com.flash.framework.mybatis.TestApplication;
import com.flash.framework.mybatis.model.User;
import com.flash.framework.mybatis.service.UserService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhurg
 * @date 2020/4/28 - 10:16 PM
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("test1");
        user.setEmail("test1");
        user.setDeleted(Boolean.FALSE);
        userService.save(user);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test1001");
        userService.updateById(user);
    }

    @Test
    public void testDelete() {
        userService.removeById(1L);
    }

    @Test
    public void testCacheSelect() {
        System.out.println(JSON.toJSONString(userService.getById(1L)));
    }

    @Test
    public void testCacheUpdate() {
        User user = userService.getById(1L);
        System.out.println(JSON.toJSONString(user));
        user.setEmail("test1002");
        System.out.println(userService.updateById(user));;
        System.out.println(JSON.toJSONString(userService.getById(1L)));
        System.out.println(userService.removeById(1L));;
        System.out.println(JSON.toJSONString(userService.getById(1L)));
    }

    @Test
    public void testCacheBatchUpdate(){
        User user = new User();
        user.setId(1L);
        System.out.println(userService.updateBatchById(Lists.newArrayList(user)));
    }

    @Test
    public void testCacheBatchDelete(){
        System.out.println(userService.removeByIds(Lists.newArrayList(1L)));
    }
}
package com.flash.framework.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhurg
 * @date 2020/4/28 - 9:57 PM
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.flash.framework.mybatis.dao"})
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
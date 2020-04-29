package com.flash.framework.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.flash.framework.mybatis.configure.GeneratorConfigure;
import com.flash.framework.mybatis.meta.CommMetaObjectHandler;
import com.flash.framework.mybatis.support.cache.MyBatisCacheConfigure;
import com.flash.framework.mybatis.support.cache.MybatisCacheInterceptor;
import com.flash.framework.mybatis.support.cache.MybatisCacheRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhurg
 * @date 2019/1/20 - 下午9:19
 */
@Configuration
@AutoConfigureAfter(MybatisPlusProperties.class)
@EnableConfigurationProperties({GeneratorConfigure.class, MyBatisCacheConfigure.class})
public class MyBatisPlusConfiguration {

    /**
     * 乐观锁插件
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(OptimisticLockerInterceptor.class)
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 通用填充类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public CommMetaObjectHandler commMetaObjectHandler() {
        return new CommMetaObjectHandler();
    }

    /**
     * 代码生成器
     *
     * @param generatorConfigure
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "mybatis.generator.enable", havingValue = "true")
    public CodeGenerator codeGenerator(GeneratorConfigure generatorConfigure) {
        return new CodeGenerator(generatorConfigure);
    }

    /**
     * 自定义缓存配置
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
    public MybatisCacheInterceptor mybatisCacheInterceptor() {
        return new MybatisCacheInterceptor();
    }

    @Bean
    @ConditionalOnProperty(name = "mybatis.cache.enable", havingValue = "true")
    public MybatisCacheRegistry mybatisCacheRegistry() {
        return new MybatisCacheRegistry();
    }

}
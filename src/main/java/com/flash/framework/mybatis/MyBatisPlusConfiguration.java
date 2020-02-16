package com.flash.framework.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.flash.framework.mybatis.configure.GeneratorConfigure;
import com.flash.framework.mybatis.meta.CommMetaObjectHandler;
import com.flash.framework.mybatis.support.cache.MybatisCacheInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhurg
 * @date 2019/1/20 - 下午9:19
 */
@Configuration
@AutoConfigureAfter(MybatisPlusProperties.class)
@EnableConfigurationProperties(GeneratorConfigure.class)
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
    @Bean(initMethod = "init")
    @ConditionalOnMissingBean(MetaObjectHandler.class)
    public CommMetaObjectHandler commMetaObjectHandler() {
        return new CommMetaObjectHandler();
    }

    @Bean
    @ConditionalOnProperty(name = "flash.generator.enable", havingValue = "true")
    public CodeGenerator codeGenerator(GeneratorConfigure generatorConfigure) {
        return new CodeGenerator(generatorConfigure);
    }

    @Bean
    @ConditionalOnBean(CacheManager.class)
    @ConditionalOnProperty(name = "flash.mybatis.cache.enable", havingValue = "true")
    public MybatisCacheInterceptor mybatisCacheInterceptor(CacheManager cacheManager) {
        return new MybatisCacheInterceptor(cacheManager);
    }
}
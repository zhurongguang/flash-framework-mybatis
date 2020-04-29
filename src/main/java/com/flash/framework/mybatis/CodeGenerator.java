package com.flash.framework.mybatis;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.flash.framework.mybatis.configure.GeneratorConfigure;
import com.google.common.collect.Maps;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

/**
 * Mybatis-plus 代码生成器
 *
 * @author zhurg
 * @date 2019/1/20 - 下午9:42
 */
public class CodeGenerator {

    private final GeneratorConfigure generatorConfigure;

    public CodeGenerator(GeneratorConfigure generatorConfigure) {
        this.generatorConfigure = generatorConfigure;
    }

    /**
     * 获取GlobalConfig
     *
     * @return
     */
    protected GlobalConfig getGlobalConfig() {
        return new GlobalConfig()
                //输出目录
                .setOutputDir(getJavaPath())
                // 是否覆盖文件
                .setFileOverride(generatorConfigure.isFileOverride())
                // 开启 activeRecord 模式
                .setActiveRecord(generatorConfigure.isActiveRecord())
                // XML 二级缓存
                .setEnableCache(generatorConfigure.isEnableCache())
                // XML ResultMap
                .setBaseResultMap(true)
                // XML columList
                .setBaseColumnList(true)
                //是否生成 kotlin 代码
                .setKotlin(generatorConfigure.isKotlin())
                .setOpen(false)
                //作者
                .setAuthor(generatorConfigure.getAuthor())
                //swagger
                .setSwagger2(generatorConfigure.isEnableSwagger())
                //设置时间类型
                .setDateType(DateType.ONLY_DATE)
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName("%s")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
    }

    /**
     * 获取StrategyConfig
     *
     * @return
     */
    protected StrategyConfig getStrategyConfig() {
        StrategyConfig strategyConfig = new StrategyConfig()
                // 全局大写命名
                .setCapitalMode(false)
                // 表名生成策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 需要生成的表
                //.setInclude(new String[] { "user" })
                //自定义实体父类
                .setSuperEntityClass("com.flash.framework.mybatis.BaseModel")
                // 自定义实体，公共字段
                .setSuperEntityColumns("id")
                // 自定义 mapper 父类
                .setSuperMapperClass("com.flash.framework.mybatis.mapper.AbstractMapper")
                // 自定义 controller 父类
                //.setSuperControllerClass("org.crown.framework.controller.SuperController")
                // 自定义 service 实现类父类
                .setSuperServiceImplClass("com.flash.framework.mybatis.service.BaseServiceImpl")
                // 自定义 service 接口父类
                .setSuperServiceClass("com.flash.framework.mybatis.service.BaseService")
                // 【实体】是否生成字段常量（默认 false）
                .setEntityColumnConstant(false)
                // 【实体】是否为构建者模型（默认 false）
                .setEntityBuilderModel(false)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                .setEntityBooleanColumnRemoveIsPrefix(true)
                // 实体类上是否生成注解
                .setEntityTableFieldAnnotationEnable(true)
                // 乐观锁字段
                .setVersionFieldName("version")
                //逻辑删除字段
                .setLogicDeleteFieldName("deleted")
                // 父类的字段
                .setSuperEntityColumns("created_at", "updated_at", "create_by", "update_by", "deleted")
                .setRestControllerStyle(true);
        if (!StringUtils.isEmpty(generatorConfigure.getTablePrefix())) {
            // 去除前缀
            strategyConfig.setTablePrefix(generatorConfigure.getTablePrefix());
        }
        return strategyConfig;
    }

    /**
     * 获取PackageConfig
     *
     * @return
     */
    protected PackageConfig getPackageConfig() {
        return new PackageConfig()
                .setParent(generatorConfigure.getBasePackage())
                .setController("controller")
                .setEntity("model.entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl");
    }

    /**
     * 获取TemplateConfig
     *
     * @return
     */
    protected TemplateConfig getTemplateConfig() {
        return new TemplateConfig().setXml(null);
    }

    /**
     * 获取InjectionConfig
     *
     * @return
     */
    protected InjectionConfig getInjectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(Maps.newHashMap());
            }
        }.setFileOutConfigList(Collections.singletonList(new FileOutConfig(
                "/templates/mapper.xml.vm") {
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
                return getResourcePath() + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
    }

    /**
     * 获取DataSourceConfig
     *
     * @return
     */
    protected DataSourceConfig getDataSourceConfig() {
        // 数据库类型
        return new DataSourceConfig()
                .setDbType(generatorConfigure.getDbType())
                .setDriverName(generatorConfigure.getDriverName())
                .setUsername(generatorConfigure.getUsername())
                .setPassword(generatorConfigure.getPassword())
                .setUrl(generatorConfigure.getJdbcUrl());
    }


    /**
     * 获取AutoGenerator
     *
     * @return
     */
    protected AutoGenerator getAutoGenerator() {
        return new AutoGenerator()
                // 全局配置
                .setGlobalConfig(getGlobalConfig())
                // 数据源配置
                .setDataSource(getDataSourceConfig())
                // 策略配置
                .setStrategy(getStrategyConfig())
                // 包配置
                .setPackageInfo(getPackageConfig())
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                .setCfg(getInjectionConfig())
                .setTemplate(getTemplateConfig());
    }

    /**
     * 获取根目录
     *
     * @return
     */
    private String getRootPath() {
        String file = Objects.requireNonNull(CodeGenerator.class.getClassLoader().getResource("")).getFile();
        return new File(file).getParentFile().getParentFile().getParent();
    }

    /**
     * 获取JAVA目录
     *
     * @return
     */
    protected String getJavaPath() {
        return (StringUtils.isEmpty(generatorConfigure.getRootPath()) ? getRootPath() : generatorConfigure.getRootPath()) + "/" + generatorConfigure.getProject() + "/src/main/java";
    }

    /**
     * 获取Resource目录
     *
     * @return
     */
    protected String getResourcePath() {
        return (StringUtils.isEmpty(generatorConfigure.getRootPath()) ? getRootPath() : generatorConfigure.getRootPath()) + "/" + generatorConfigure.getProject() + "/src/main/resources";
    }

    @EventListener
    public void generator(ContextRefreshedEvent event) {
        if (generatorConfigure.isAutoInit()) {
            getAutoGenerator().execute();
        }
    }

}
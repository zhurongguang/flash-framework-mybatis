package com.flash.framework.mybatis.configure;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhurg
 * @date 2019/2/1 - 上午11:21
 */
@Data
@ConfigurationProperties(prefix = "mybatis.generator")
public class GeneratorConfigure {

    /**
     * 是否启用
     */
    private boolean enable = false;

    /**
     * 是否覆盖历史文件
     */
    private boolean fileOverride = false;

    /**
     * 是否开启activeRecord模式
     */
    private boolean activeRecord = false;

    /**
     * 是否开启二级缓存
     */
    private boolean enableCache = false;

    /**
     * 是否生成Kotlin代码
     */
    private boolean kotlin = false;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 是否开启swagger
     */
    private boolean enableSwagger = false;

    /**
     * 基础包名
     */
    private String basePackage;

    /**
     * 数据库类型
     */
    private DbType dbType = DbType.MYSQL;

    /**
     * 数据库DriverName
     */
    private String driverName;

    /**
     * 数据库用户名
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库连接
     */
    private String jdbcUrl;

    /**
     * 标签前缀
     */
    private String tablePrefix;

    /**
     * 自动初始化
     */
    private boolean autoInit = false;

    /**
     * 生成代码的根路径
     */
    private String rootPath;

    /**
     * 项目
     */
    private String project;
}
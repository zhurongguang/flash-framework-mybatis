package com.flash.framework.mybatis.support.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhurg
 * @date 2020/4/27 - 9:36 PM
 */
@Data
@ConfigurationProperties(prefix = "mybatis.cache")
public class MyBatisCacheConfigure {

    private boolean enable;
}
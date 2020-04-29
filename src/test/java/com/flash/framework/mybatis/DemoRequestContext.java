package com.flash.framework.mybatis;

import com.flash.framework.commons.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * @author zhurg
 * @date 2020/4/28 - 10:34 PM
 */
@Component
public class DemoRequestContext implements RequestContext {
    @Override
    public Long getUserId() {
        return 1L;
    }

    @Override
    public Integer getTenantId() {
        return 1;
    }

    @Override
    public String getUserName() {
        return "demo";
    }

    @Override
    public String getIp() {
        return "192";
    }
}

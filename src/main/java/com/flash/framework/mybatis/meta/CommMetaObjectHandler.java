package com.flash.framework.mybatis.meta;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.flash.framework.commons.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Date;
import java.util.Objects;

/**
 * @author zhurg
 * @date 2019/1/20 - 下午10:24
 */
@Slf4j
public class CommMetaObjectHandler implements MetaObjectHandler, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private RequestContext requestContext;

    public void init() {
        try {
            this.requestContext = applicationContext.getBean(RequestContext.class);
        } catch (Exception e) {
            log.warn("[Flash Framework] can not fund RequestContext in Spring Bean");
        }
    }

    /**
     * 创建时间
     */
    private static final String CREATED_AT = "createdAt";

    /**
     * 更新时间
     */
    private static final String UPDATED_AT = "updatedAt";

    /**
     * 创建人
     */
    private static final String CREATE_BY = "createBy";

    /**
     * 更新人
     */
    private static final String UPDATE_BY = "updateBy";


    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Date date = new Date();
            if (metaObject.hasGetter(CREATED_AT) && Objects.isNull(metaObject.getValue(CREATED_AT))) {
                strictInsertFill(metaObject, CREATED_AT, Date.class, date);
            }
            if (metaObject.hasGetter(UPDATED_AT) && Objects.isNull(metaObject.getValue(UPDATED_AT))) {
                strictInsertFill(metaObject, UPDATED_AT, Date.class, date);
            }
            if (null != requestContext) {
                if (metaObject.hasGetter(CREATE_BY) && Objects.isNull(metaObject.getValue(CREATE_BY))) {
                    strictInsertFill(metaObject, CREATE_BY, String.class, requestContext.getUserId());
                }
                if (metaObject.hasGetter(UPDATE_BY) && Objects.isNull(metaObject.getValue(UPDATE_BY))) {
                    strictInsertFill(metaObject, UPDATE_BY, String.class, requestContext.getUserId());
                }
            }
        } catch (Exception e) {
            log.error("[Flash Framework] MetaObjectHandler auto insert failed,cause:", e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            if (metaObject.hasGetter(UPDATED_AT) && Objects.isNull(metaObject.getValue(UPDATED_AT))) {
                strictUpdateFill(metaObject, UPDATED_AT, Date.class, new Date());
            }
            if (null != requestContext) {
                if (metaObject.hasGetter(UPDATE_BY) && Objects.isNull(metaObject.getValue(UPDATE_BY))) {
                    strictUpdateFill(metaObject, UPDATE_BY, String.class, requestContext.getUserId());
                }
            }
        } catch (Exception e) {
            log.error("[Flash Framework] MetaObjectHandler auto update failed,cause:", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
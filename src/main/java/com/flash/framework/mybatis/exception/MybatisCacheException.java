package com.flash.framework.mybatis.exception;

/**
 * @author zhurg
 * @date 2020/4/26 - 9:33 PM
 */
public class MybatisCacheException extends RuntimeException {

    private static final long serialVersionUID = -7790188413954351134L;

    public MybatisCacheException() {
    }

    public MybatisCacheException(String message) {
        super(message);
    }

    public MybatisCacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public MybatisCacheException(Throwable cause) {
        super(cause);
    }

    public MybatisCacheException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
package com.biz.bizunited.wechat.payment.exceptions;

/**
 * Created by defei on 7/3/15.
 */
public class MissingResourceException extends RuntimeException {

    public MissingResourceException() {
    }

    public MissingResourceException(String message) {
        super(message);
    }

    public MissingResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingResourceException(Throwable cause) {
        super(cause);
    }

    public MissingResourceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

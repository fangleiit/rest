package com.biz.bizunited.wechat.payment.exceptions;

/**
 * Created by defei on 7/3/15.
 */
public class WechatResponseParseException extends RuntimeException {

    public WechatResponseParseException() {
    }

    public WechatResponseParseException(String message) {
        super(message);
    }

    public WechatResponseParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatResponseParseException(Throwable cause) {
        super(cause);
    }

    public WechatResponseParseException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

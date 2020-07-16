package com.biz.bizunited.wechat.payment.exceptions;

public class SignIncorrectException extends RuntimeException {

    public SignIncorrectException() {
    }

    public SignIncorrectException(String message) {
        super(message);
    }

    public SignIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignIncorrectException(Throwable cause) {
        super(cause);
    }

    public SignIncorrectException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.biz.bizunited.exception;

public class CommonException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1463227871616512938L;

    /**
     * 状态码
     */
    private int code;

    /**
     *
     */
    public CommonException() {
    }

    /**
     * @param message
     */
    public CommonException(String message) {
        super(message);
    }

    /**
     * @param message
     */
    public CommonException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * @param cause
     */
    public CommonException(Throwable cause) {
        super(cause);
    }

    /**
     * @param cause
     */
    public CommonException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }


    /**
     * @param message
     * @param cause
     */
    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param code
     * @param cause
     */
    public CommonException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CommonException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }


}

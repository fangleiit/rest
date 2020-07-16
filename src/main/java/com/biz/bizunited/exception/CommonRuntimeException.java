package com.biz.bizunited.exception;

public class CommonRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1463227871685512938L;

    /**
     * 状态码
     */
    private int code;

    /**
     *
     */
    public CommonRuntimeException() {
    }

    /**
     * @param message
     */
    public CommonRuntimeException(String message) {
        super(message);
    }

    /**
     * @param message
     */
    public CommonRuntimeException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * @param cause
     */
    public CommonRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param cause
     */
    public CommonRuntimeException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }


    /**
     * @param message
     * @param cause
     */
    public CommonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param code
     * @param cause
     */
    public CommonRuntimeException(String message, int code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CommonRuntimeException(String message, Throwable cause, boolean enableSuppression,
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
    @Override
    public String getMessage() {
        String message = super.getMessage();
        return message;
    }

}

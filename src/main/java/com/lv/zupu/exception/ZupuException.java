package com.lv.zupu.exception;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2018/6/25 19:52
 * @Version: 1.0
 * modified by:
 */
public class ZupuException extends RuntimeException{
    private String code = "-1";
    private String message;

    public ZupuException() {
        super();
    }

    public ZupuException(String message) {
        super(message);
        this.message = message;
    }

    public ZupuException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public ZupuException(String code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    public ZupuException(Throwable cause) {
        super(cause);
    }


}

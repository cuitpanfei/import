package com.pfinfo.impor.exception;


/**
 * 系统自定义基础异常类
 *
 * @author cuitpanfei
 */
public class SystemBaseException extends RuntimeException {

    public SystemBaseException() {
        super();
    }

    public SystemBaseException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public SystemBaseException(String msg) {
        this(msg, null);
    }
    public SystemBaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

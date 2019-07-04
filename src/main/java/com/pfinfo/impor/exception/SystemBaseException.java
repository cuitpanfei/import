package com.pfinfo.impor.exception;


/**
 * 系统自定义基础异常类
 *
 * @author cuitpanfei
 */
public class SystemBaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3469229905321785626L;
	
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

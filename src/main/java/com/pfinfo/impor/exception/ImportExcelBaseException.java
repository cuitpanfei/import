package com.pfinfo.impor.exception;

import lombok.Getter;

/**
 * Excel批量导入自定义异常类
 *
 * @author cuitpanfei
 */
public class ImportExcelBaseException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 8820109853700827761L;

    @Getter
    private int code = Integer.MIN_VALUE;

    public ImportExcelBaseException() {
        super();
    }

    public ImportExcelBaseException(Throwable cause) {
        this(Integer.MIN_VALUE, cause.getMessage(), cause);
    }

    public ImportExcelBaseException(String msg) {
        this(Integer.MIN_VALUE, msg, null);
    }

    public ImportExcelBaseException(int code, String msg) {
        this(code, msg, null);
    }

    public ImportExcelBaseException(String msg, Throwable cause) {
        this(Integer.MIN_VALUE, msg, cause);
    }

    public ImportExcelBaseException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

}

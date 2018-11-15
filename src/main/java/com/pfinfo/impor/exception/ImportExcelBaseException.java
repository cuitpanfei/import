package com.pfinfo.impor.exception;

/**
 * Excel批量导入自定义异常类
 * 
 * @author pys1714
 *
 */
public class ImportExcelBaseException extends RuntimeException {

	private int code = Integer.MIN_VALUE;

	public int getCode() {
		return code;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8820109853700827761L;

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
	public ImportExcelBaseException(String msg,Throwable cause) {
		this(Integer.MIN_VALUE, msg, cause);
	}

	public ImportExcelBaseException(int code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}
	
	

}

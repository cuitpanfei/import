package com.pfinfo.impor;

import java.io.Serializable;
import java.util.Map;

public class ImportModelBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4332843939588031256L;

	public ImportModelBean() {

	}

	public ImportModelBean(String sheetName,Map<String, String> colsMap) {
		super();
		this.sheetName = sheetName;
		this.colsMap=colsMap;
	}

	/**
	 * sheet 的名称
	 */
	private String sheetName;

	/**
	 * 映射关系，key：excel列名称，value：属性名称
	 */
	private Map<String, String> colsMap;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Map<String, String> getColsMap() {
		return colsMap;
	}

	public void setColsMap(Map<String, String> colsMap) {
		this.colsMap = colsMap;
	}

}

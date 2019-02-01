package com.pfinfo.impor.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * excel导入模板的映射关系类，根据模板类生成，用于存储模板类名及对应sheetName，还有第一行表头和模板类属性名的映射关系。
 * @author cuitpanfei
 */
public class ImportModelBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4332843939588031256L;
    /**
     * sheet 的名称
     */
    private String sheetName;
    /**
     * 映射关系，key：excel列名称，value：属性名称
     */
    private Map<String, String> colsMap;

    public ImportModelBean() {

    }

    public ImportModelBean(String sheetName, Map<String, String> colsMap) {
        super();
        this.sheetName = sheetName;
        this.colsMap = colsMap;
    }

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

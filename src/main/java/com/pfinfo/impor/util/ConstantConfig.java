package com.pfinfo.impor.util;

public class ConstantConfig {
    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    public static final String SAVEPATH = System.getenv("OS").toLowerCase().contains("windows")?System.getenv("USERPROFILE")+"\\Desktop":"/tmp/";
    public static final String UTF_8 = "UTF-8";
    public static final char POINT = '.';
    public static final char VIRGULE = '/';
}

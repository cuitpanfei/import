package com.pfinfo.impor.util;

import com.pfinfo.impor.exception.ImportExcelBaseException;

public class StringUtil {

	/**
	 * 根据文件路径获取文件类型
	 * @param url 文件网络路径
	 * @return 文件类型
	 */
	public static String getFileSuffix(String url) {
		if(NullCheckUtil.isNull(url)){
			throw new ImportExcelBaseException("文件路径不能为空");
		}
		// del url path param
		String uri = url.substring(0,url.indexOf("?"));
		if(!org.apache.poi.util.StringUtil.endsWithIgnoreCase(uri, "xls")
				&& !org.apache.poi.util.StringUtil.endsWithIgnoreCase(uri, "xlsx")){
			throw new ImportExcelBaseException("文件不是Excel格式");
		}
		return url.substring(0,url.lastIndexOf("."));
	}

	/**
	 * 首字母大写，第一个字符减去32
	 * @param str
	 * @return
	 */
	public static String upperCase(String str){
		char[] ch = str.toCharArray();
		ch[0]=(char)(ch[0]-32);
		return new String(ch);
	}
	
	/**
	 * 首字母小写，第一个字符加上32
	 * @param str
	 * @return
	 */
	public static String lowerCase(String str){
		char[] ch = str.toCharArray();
		ch[0]=(char)(ch[0]+32);
		return new String(ch);
	}

}

package com.pfinfo.impor.util;

import com.pfinfo.impor.exception.ImportExcelBaseException;

import static com.pfinfo.impor.util.ConstantConfig.XLS;
import static com.pfinfo.impor.util.ConstantConfig.XLSX;


/**
 * 自定义字符串工具类
 * @author cuitpanfei
 */
public class StringUtil {

    /**
     * 根据Excel文件网络路径获取文件类型
     * <br>
     * 如果url="http://domain.com/filename.xlxs"，返回结果为："xlxs"。
     *
     * @param url 文件网络路径
     * @return 文件类型
     */
    public static String getFileSuffix(String url) {
        if (NullCheckUtil.isNull(url)) {
            throw new ImportExcelBaseException("文件路径不能为空");
        }
        // del url path param
        int index = url.indexOf("?");
        String uri = index == -1 ? url : url.substring(0, index);
        if (!org.apache.poi.util.StringUtil.endsWithIgnoreCase(uri, XLS)
                && !org.apache.poi.util.StringUtil.endsWithIgnoreCase(uri, XLSX)) {
            throw new ImportExcelBaseException("文件不是Excel格式");
        }
        return url.substring(url.lastIndexOf("."));
    }

    /**
     * 首字母大写，第一个字符减去32
     *
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    /**
     * 首字母小写，第一个字符加上32
     *
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String lowerCase(String str) {
        char[] ch = str.toCharArray();
        ch[0] = (char) (ch[0] + 32);
        return new String(ch);
    }

}

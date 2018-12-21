package com.pfinfo.impor.util;

import com.pfinfo.impor.exception.ImportExcelBaseException;

import static com.pfinfo.impor.util.ConstantConfig.XLS;
import static com.pfinfo.impor.util.ConstantConfig.XLSX;


/**
 * 自定义字符串工具类
 *
 * @author cuitpanfei
 */
public class StringUtil {

    /**
     * 根据Excel文件网络路径获取文件类型
     * <be/>
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
        String uri = url.indexOf("?") == -1 ? url : url.substring(0, url.indexOf("?"));
        if (!org.apache.poi.util.StringUtil.endsWithIgnoreCase(uri, XLS)
                && !org.apache.poi.util.StringUtil.endsWithIgnoreCase(uri, XLSX)) {
            throw new ImportExcelBaseException("文件不是Excel格式");
        }
        return url.substring(url.lastIndexOf("."));
    }

    /**
     * 首字母大写，第一个字符减去32
     *
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        ch[0] = (char) (ch[0] - 32);
        return new String(ch);
    }

    /**
     * 首字母小写，第一个字符加上32
     *
     * @param str
     * @return
     */
    public static String lowerCase(String str) {
        char[] ch = str.toCharArray();
        ch[0] = (char) (ch[0] + 32);
        return new String(ch);
    }

    /**
     * 检查给定的字符串{@code String} 是否是 {@code null} 或者长度为 0.
     * <p>Note: 当字符串{@code String} 是空格组成时,本方法也将会返回 {@code true} .
     *
     * @param str 参与检查的字符串 {@code String} (可能为 {@code null})
     * @return 如果字符串 {@code String} 不为 {@code null} 并且长度不为0,就返回{@code true}
     */
    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * 用字符串newPattern {@code String} 替换字符串inString {@code String} 内所有出现的子字符串oldPattern {@code String}
     * @param inString
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        int index = inString.indexOf(oldPattern);
        if (index == -1) {
            // no occurrence -> can return input as-is
            return inString;
        }

        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);
        // our position in the old string
        int pos = 0;
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString.substring(pos, index));
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        // append any characters to the right of a match
        sb.append(inString.substring(pos));
        return sb.toString();
    }

}

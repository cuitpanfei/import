package com.pfinfo.impor.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 判断对象是否为空或null
 *
 * @author pys1714
 */
public class NullCheckUtil {

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (isNull(obj)) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}

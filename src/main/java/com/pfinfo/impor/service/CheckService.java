package com.pfinfo.impor.service;

import com.pfinfo.impor.exception.ImportExcelBaseException;

import java.util.function.Predicate;

/**
 *
 * @param <T>　泛型
 * @author cuitpanfei
 */
@FunctionalInterface
public interface CheckService<T> extends Predicate<T> {

    /**
     * 检查服务,如果检查不通过,抛出自定义异常
     *
     * @throws ImportExcelBaseException 自定义文件上传异常
     */
    boolean test(T t) throws ImportExcelBaseException;
}

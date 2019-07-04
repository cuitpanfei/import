package com.pfinfo.impor.service;

import com.pfinfo.impor.exception.ImportExcelBaseException;

import java.util.function.Predicate;

/**
 *
 * @param <T>
 * @author cuitpanfei
 */
@FunctionalInterface
public interface CheckService<T> extends Predicate<T> {

    /**
     * 检查服务,如果检查出现异常,可能抛出自定义异常
     *
     * @throws ImportExcelBaseException
     */
    boolean test(T t) throws ImportExcelBaseException;
}

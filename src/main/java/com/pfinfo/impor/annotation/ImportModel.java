package com.pfinfo.impor.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportModel {

    /**
     * 必填项，表名
     * @return 表名
     */
    String sheetName();
}

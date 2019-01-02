package com.pfinfo.impor.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelField {

    /**
     * 必填项，列名称
     * @return 列名称
     */
    String columnName();

}

package com.pfinfo.impor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.pfinfo.impor.base.ImportUtil;
import com.pfinfo.impor.context.InitByImportModel;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ImportUtil.class,InitByImportModel.class})
public @interface EnableImportUtil {

}

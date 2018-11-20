package com.pfinfo.impor.annotation;

import com.pfinfo.impor.base.ImportUtil;
import com.pfinfo.impor.context.InitByImportModel;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ImportUtil.class, InitByImportModel.class})
public @interface EnableImportUtil {

}

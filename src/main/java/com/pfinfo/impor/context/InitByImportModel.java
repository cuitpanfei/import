package com.pfinfo.impor.context;

import com.pfinfo.impor.annotation.ImportModel;
import com.pfinfo.impor.annotation.ModelField;
import com.pfinfo.impor.bean.ImportModelBean;
import com.pfinfo.impor.exception.ImportExcelBaseException;
import com.pfinfo.impor.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class InitByImportModel {

    private static final Logger log = LoggerFactory.getLogger(InitByImportModel.class);

    public InitByImportModel() {
        if (log.isDebugEnabled()) {
            log.debug("init import bean map start.");
        }
        Class<?> mainClazz = deduceMainApplicationClass();
        Package pack = mainClazz.getPackage();
        Set<Class<?>> all = ClassUtil.getClasses(pack);
        all.stream()
                .filter(clazz -> clazz.getAnnotation(ImportModel.class) != null)
                .forEach(
                        clazz -> {
                            ImportModel info = clazz.getAnnotation(ImportModel.class);
                            Map<String, String> fieldsMap = getAllFieldMap(clazz);
                            if (fieldsMap.isEmpty() && log.isDebugEnabled()) {
                                log.debug("{}内部没有属性使用了ModelField注解", clazz.getName());
                            }
                            ImportModelBeanCatch.getInstance().addCatch(clazz, new ImportModelBean(info.sheetName(), fieldsMap));
                        });
        if (log.isDebugEnabled()) {
            log.debug("init import bean map end.");
        }
    }

    /**
     * 获取主类
     *
     * @return
     */
    private Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        } catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }

    /**
     * 通过类对象获取字段映射
     *
     * @param clazz
     * @return
     */
    private Map<String, String> getAllFieldMap(Class<?> clazz) {
        Map<String, String> fieldMap = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ModelField modelField = field.getAnnotation(ModelField.class);
            if (modelField != null) {
                if (fieldMap.containsKey(modelField.columnName())) {
                    throw new ImportExcelBaseException(clazz.getName() + " 内部多个属性使用了：[" + modelField.columnName() + "]，请检查");
                }
                fieldMap.put(modelField.columnName(), field.getName());
            }
        }
        return fieldMap;
    }

}

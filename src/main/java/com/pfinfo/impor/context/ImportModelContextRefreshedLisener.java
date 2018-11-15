package com.pfinfo.impor.context;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.pfinfo.impor.ImportModelBean;
import com.pfinfo.impor.annotation.ImportModel;
import com.pfinfo.impor.annotation.ModelField;



public class ImportModelContextRefreshedLisener implements
		ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(log.isDebugEnabled()){
			log.debug("init import bean map start.");
		}
		if(event.getApplicationContext().getParent()==null){
			Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(ImportModel.class);
			beans.forEach((name,bean)->{
				Class<?> clazz = bean.getClass();
				ImportModel info = clazz.getAnnotation(ImportModel.class);
				Map<String,String> fieldsMap = getAllFieldMap(clazz);
				if(fieldsMap.isEmpty()&&log.isDebugEnabled()){
					log.debug("{}内部没有属性使用了ModelField注解",clazz.getName());
				}
				ImportModelBeanCatch.getInstance().addCatch(clazz, new ImportModelBean(info.sheetName(),fieldsMap));
			});
		}
		if(log.isDebugEnabled()){
			log.debug("init import bean map end.");
		}
		
	}
	/**
	 * 通过类对象获取字段映射
	 * @param clazz
	 * @return
	 */
	private Map<String, String> getAllFieldMap(Class<?> clazz) {
		Map<String, String> fieldMap = new HashMap<>();
		Field[] fields = clazz.getFields();
		for(Field field:fields){
			ModelField modelField = field.getAnnotation(ModelField.class);
			if(modelField!=null){
				fieldMap.put(modelField.columnName(),field.getName());
			}
		}
		return fieldMap;
	}

}

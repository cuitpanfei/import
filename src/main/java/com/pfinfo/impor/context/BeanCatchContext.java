package com.pfinfo.impor.context;

import com.pfinfo.impor.annotation.ImportModel;
import com.pfinfo.impor.bean.ImportModelBean;

/**
 * 导入模板映射关系容器的访问类，仅包含对{@link ImportModelBeanCatch} 的读操作
 * @author cuitpanfei
 *
 */
public class BeanCatchContext {

	/**
	 * 你需要确保你传入的Class对象是被注解{@link ImportModel @ImportModel}了的，否则，将无法从容器中找到对应的映射关系，从而导致返回{@code null}。
	 * 
	 * @param clazz 导入excel的sheet映射泛型
	 * @return excel导入模板的映射关系，可能为{@code null}
	 */
	public static ImportModelBean getCatch(Class<?> clazz) {
		return ImportModelBeanCatch.getInstance().getCatch(clazz);
	}
	

}

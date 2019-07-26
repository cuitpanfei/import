package com.pfinfo.impor.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.pfinfo.impor.bean.ImportModelBean;
import com.pfinfo.impor.util.NullCheckUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @desc 导入模板映射关系容器
 * @author cuitpanfei
 */
@Slf4j
final class ImportModelBeanCatch {
	private static final ImportModelBeanCatch instance = new ImportModelBeanCatch();
    private Map<String, ImportModelBean> beanCatch = new HashMap<>();

    /**
     * 禁止通过new的方式创建对象，请通过以下方式获取对象
     * {@link ImportModelBeanCatch#getInstance()}
     */
    private ImportModelBeanCatch() {
    	if(Objects.nonNull(instance)) {
    		throw new RuntimeException("禁止创建容器对象");
    	}
    }

    /**
     * 获取单例对象
     *
     * @return 单例对象
     */
    public static ImportModelBeanCatch getInstance() {
        return instance;
    }

    /**
     * 根据类对象获取映射关系集合
     * @param key
     * @return
     */
    public ImportModelBean getCatch(Class<?> key) {
        if (NullCheckUtil.isEmpty(key)) {
            if (log.isDebugEnabled()) {
                log.debug("The key is empty");
            }
            return null;
        }
        try {
            return beanCatch.get(key.getName());
        } catch (Exception e) {
            log.error("get Catch data by key:{} error", key, e);
            return null;
        }
    }

     synchronized boolean addCatch(Class<?> key, ImportModelBean value) {
        if (NullCheckUtil.isEmpty(key)) {
            if (log.isDebugEnabled()) {
                log.debug("The key is empty");
            }
            return false;
        }
        try {
            beanCatch.put(key.getName(), value);
        } catch (Exception e) {
            log.error("Catch data<{},{}> error", key, value, e);
            return false;
        }
        return true;
    }
}

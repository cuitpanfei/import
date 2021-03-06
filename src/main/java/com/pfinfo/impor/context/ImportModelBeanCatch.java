package com.pfinfo.impor.context;

import com.pfinfo.impor.bean.ImportModelBean;
import com.pfinfo.impor.util.NullCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ImportModelBeanCatch {
    private static final Logger log = LoggerFactory.getLogger(ImportModelBeanCatch.class);
    private static final ImportModelBeanCatch instance = new ImportModelBeanCatch();
    private Map<Class<?>, ImportModelBean> beanCatch = new HashMap<>();

    public static ImportModelBeanCatch getInstance() {
        return instance;
    }

    public synchronized ImportModelBean getCatch(Class<?> key) {
        if (NullCheckUtil.isEmpty(key)) {
            if (log.isDebugEnabled()) {
                log.debug("The key is empty");
            }
            return null;
        }
        try {
            return beanCatch.get(key);
        } catch (Exception e) {
            log.error("get Catch data by key:{} error", key, e);
            return null;
        }
    }

    public synchronized boolean addCatch(Class<?> key, ImportModelBean value) {
        if (NullCheckUtil.isEmpty(key)) {
            if (log.isDebugEnabled()) {
                log.debug("The key is empty");
            }
            return false;
        }
        try {
            beanCatch.put(key, value);
        } catch (Exception e) {
            log.error("Catch data<{},{}> error", key, value, e);
            return false;
        }
        return true;
    }
}

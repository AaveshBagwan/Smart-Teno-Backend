package com.platform.common.utils;

import org.springframework.core.env.Environment;

public class ContextUtils {

    public static <T> T getBean(Class<T> type) {
        return AppSpringContext.getBean(type);
    }

    public static String getProperty(String name) {
        Environment environment = ContextUtils.getBean(Environment.class);
        return environment.getProperty(name);
    }

    public static <T> T getProperty(String name, Class<T> type) {
        Environment environment = ContextUtils.getBean(Environment.class);
        return environment.getProperty(name, type);
    }

}

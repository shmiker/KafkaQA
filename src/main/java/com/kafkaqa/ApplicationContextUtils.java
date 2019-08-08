package com.kafkaqa;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext appContext)
            throws BeansException {
        ctx = appContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return ctx.getBean(beanClass);
    }

    public static String getEnvironmentProperty(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return ctx.getEnvironment().getProperty(key);
    }
}

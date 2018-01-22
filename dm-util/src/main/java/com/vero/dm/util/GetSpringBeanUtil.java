package com.vero.dm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  10:39 2017/7/8.
 * @description
 * @modified by:
 */
public class GetSpringBeanUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;

    public GetSpringBeanUtil()
    {
        super();
    }

    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        applicationContext = arg0;
    }

    public synchronized static Object getBean(String paramString) {
        return applicationContext.getBean(paramString);
    }

}

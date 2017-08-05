package com.dm.org.webconfig;


import ch.qos.logback.ext.spring.web.LogbackConfigListener;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:54 2017/7/19.
 * @since data-minning-platform
 */

public class LoggerInitializer implements WebApplicationInitializer
{
    private static String SH4J_CONFIG_LOCATION = "classpath:logback.xml";

    @Override
    public void onStartup(ServletContext servletContext)
        throws ServletException
    {
        servletContext.setInitParameter("logbackConfigLocation", SH4J_CONFIG_LOCATION);
        servletContext.addListener(LogbackConfigListener.class);
    }
}

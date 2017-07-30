package com.dm.org.webconfig;


import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 14:56 2017/7/17.
 * @description
 * @modified by:
 */
public class DelegatingProxyFilterRegistration implements WebApplicationInitializer
{
    @Override
    public void onStartup(ServletContext servletContext)
        throws ServletException
    {
        FilterRegistration.Dynamic delegatingFilter = servletContext.addFilter("shiroFilter",
            DelegatingFilterProxy.class);
        delegatingFilter.addMappingForUrlPatterns(null, false, "/*");
        setInitParameters(delegatingFilter);
    }

    private void setInitParameters(FilterRegistration.Dynamic filterRegistration)
    {
        Map<String, String> initParameters = new LinkedHashMap<String, String>();
        initParameters.put("targetFilterLifecycle", "true");
        filterRegistration.setInitParameters(initParameters);
    }
}

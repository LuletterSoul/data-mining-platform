package com.dm.org.webconfig;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  12:37 2017/7/18.
 * @since data-minning-platform
 */

public class CharacterEncodingFilterRegistration implements WebApplicationInitializer
 {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
     {
         FilterRegistration.Dynamic filterRegistration
         = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
         filterRegistration.setAsyncSupported(true);
         setInitParameters(filterRegistration);
         filterRegistration.addMappingForUrlPatterns(null,false,"/*");
    }
    private void setInitParameters(FilterRegistration.Dynamic filterRegistration)
    {
        Map<String, String> initParameters = new LinkedHashMap<String, String>();
        initParameters.put("encoding", "UTF-8");
        initParameters.put("forceEncoding", "true");
        filterRegistration.setInitParameters(initParameters);
    }
}

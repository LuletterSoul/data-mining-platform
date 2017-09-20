package com.dm.org.webconfig;


import com.dm.org.webconfig.aop.AopConfiguration;
import com.dm.org.webconfig.cache.EhCacheConfiguration;
import com.dm.org.webconfig.dataSource.impl.ComboPooledDataSourceConfig;
import com.dm.org.webconfig.hibernate.HibernateConfiguration;
import com.dm.org.webconfig.security.ShiroSecurityConfiguration;
import com.dm.org.webconfig.springMvc.SpringMvcConfiguration;
import com.dm.org.webconfig.transaction.HibernateTransactionConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

//import com.dm.org.webconfig.security.ShiroSecurityConfiguration;

/**
 * @author  qq313700046@icloud.com .
 * @date created in 22:39 2017/6/28.
 * @description
 * @modified by:
 */

public class DataMiningPlatformWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[]
        {
                AopConfiguration.class,
                ComboPooledDataSourceConfig.class,
                HibernateConfiguration.class,
                HibernateTransactionConfig.class,
                EhCacheConfiguration.class,
                ShiroSecurityConfiguration.class
        };
    }

    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[] {SpringMvcConfiguration.class};
    }

    protected String[] getServletMappings()
    {
        return new String[] {"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration)
    {
        registration.setLoadOnStartup(5);
        registration.setMultipartConfig(new MultipartConfigElement("/tmp/dm"));
    }
}

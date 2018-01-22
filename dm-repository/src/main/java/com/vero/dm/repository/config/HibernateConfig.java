package com.vero.dm.repository.config;


import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.vero.dm.repository.DaoScanningMarker;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:17 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@PropertySource(value = "classpath:hibernate.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackageClasses = {DaoScanningMarker.class})
//@Import(ComboPooledDataSource.class)
public class HibernateConfig
{
    @Resource
    private Environment environment;

    @Resource
    private DataSource dataSource;


    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SessionFactory sessionFactory()
    {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.setPackagesToScan("com.dm.org.model");
        try
        {
            sessionFactoryBean.afterPropertiesSet();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        return sessionFactoryBean.getObject();
    }

    private Properties hibernateProperties()
    {
        Properties properties = new Properties();
        properties.setProperty("current_session_context_class",
                environment.getProperty("current_session_context_class"));
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
//        properties.setProperty("hibernate.cache.region.factory_class",
//            environment.getProperty("hibernate.cache.region.factory_class"));
        properties.setProperty("hibernate.show_sql",
                environment.getProperty("hibernate.show_sql"));
        properties.setProperty("hibernate.format_sql",
                environment.getProperty("hibernate.format_sql"));
        properties.setProperty("hibernate.hbm2ddl.auto",
                environment.getProperty("hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.connection.url",
                environment.getProperty("hibernate.connection.url"));
        return properties;
    }

}

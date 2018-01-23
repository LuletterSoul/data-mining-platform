package com.vero.dm.repository.config;


import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:17 2017/7/17.
 * @description
 * @modified by:
 */

@Configuration
@EntityScan({"com.vero.dm.model"})
@PropertySource(value = "classpath:hibernate.properties", ignoreResourceNotFound = true)
// @Import(ComboPooledDataSource.class)
public class DataAccessConfiguration
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
        sessionFactoryBean.setPackagesToScan("com.vero.dm.model");
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
        // properties.setProperty("hibernate.cache.region.factory_class",
        // environment.getProperty("hibernate.cache.region.factory_class"));
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

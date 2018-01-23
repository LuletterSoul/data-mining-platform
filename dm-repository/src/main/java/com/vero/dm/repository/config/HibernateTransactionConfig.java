package com.vero.dm.repository.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  21:21 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(DataAccessConfiguration.class)
public class HibernateTransactionConfig
{
    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private DataSource dataSource;


    @Bean
    public HibernateTransactionManager transactionManager()
    {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        transactionManager.setDataSource(dataSource);
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

}

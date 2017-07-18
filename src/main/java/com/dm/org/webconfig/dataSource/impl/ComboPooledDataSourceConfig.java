package com.dm.org.webconfig.dataSource.impl;


import com.dm.org.webconfig.dataSource.DataSourceConfiguration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 19:49 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@PropertySource(value = "classpath:dataSource.properties")
public class ComboPooledDataSourceConfig implements DataSourceConfiguration, EnvironmentAware

{
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }

    @Bean(name = "dataSource")
    public DataSource loadDataSource()
    {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        dataSource.setJdbcUrl(environment.getProperty("jdbc.url"));
        try
        {
            dataSource.setDriverClass(environment.getProperty("jdbc.driver"));
        }
        catch (PropertyVetoException e)
        {
            e.printStackTrace();
        }
        dataSource.setInitialPoolSize(
            Integer.valueOf(environment.getProperty("jdbc.initialPoolSize")));
        dataSource.setMaxPoolSize(Integer.valueOf(environment.getProperty("jdbc.maxPoolSize")));
        dataSource.setMinPoolSize(Integer.valueOf(environment.getProperty("jdbc.minPoolSize")));
        dataSource.setMaxStatements(
            Integer.valueOf(environment.getProperty("jdbc.maxStatements")));
        dataSource.setMaxStatementsPerConnection(
            Integer.valueOf(environment.getProperty("jdbc.maxStatementsPerConnection")));
        dataSource.setAcquireIncrement(
            Integer.valueOf(environment.getProperty("jdbc.acquireIncrement")));
        dataSource.setAcquireRetryAttempts(
            Integer.valueOf(environment.getProperty("jdbc.acquireRetryAttempts")));
        dataSource.setAutoCommitOnClose(
            Boolean.valueOf(environment.getProperty("jdbc.autoCommitOnClose")));
        dataSource.setAcquireRetryDelay(
            Integer.valueOf(environment.getProperty("jdbc.acquireRetryDelay")));
        dataSource.setMaxIdleTime(Integer.valueOf(environment.getProperty("jdbc.maxIdleTime")));
        dataSource.setIdleConnectionTestPeriod(
            Integer.valueOf(environment.getProperty("jdbc.idleConnectionTestPeriod")));
        dataSource.setBreakAfterAcquireFailure(
            Boolean.valueOf(environment.getProperty("jdbc.breakAfterAcquireFailure")));
        return dataSource;
    }
}

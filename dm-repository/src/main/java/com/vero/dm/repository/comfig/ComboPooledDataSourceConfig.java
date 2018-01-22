package com.vero.dm.repository.comfig;


import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.mchange.v2.c3p0.ComboPooledDataSource;


@Configuration
@PropertySource(value = "classpath:datasource.properties")
public class ComboPooledDataSourceConfig implements EnvironmentAware

{
    private Environment environment;

    public void setEnvironment(Environment environment)
    {
        this.environment = environment;
    }

    @Bean(name = "dataSource")
    public DataSource loadDataSource()
    {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(environment.getProperty("dm.db.username"));
        dataSource.setPassword(environment.getProperty("dm.db.password"));
        dataSource.setJdbcUrl(environment.getProperty("dm.db.url"));
        try
        {
            dataSource.setDriverClass(environment.getProperty("dm.db.driver"));
        }
        catch (PropertyVetoException e)
        {
            e.printStackTrace();
        }
        dataSource.setInitialPoolSize(
            Integer.valueOf(environment.getProperty("dm.db.initialPoolSize")));
        dataSource.setMaxPoolSize(Integer.valueOf(environment.getProperty("dm.db.maxPoolSize")));
        dataSource.setMinPoolSize(Integer.valueOf(environment.getProperty("dm.db.minPoolSize")));
        dataSource.setMaxStatements(
            Integer.valueOf(environment.getProperty("dm.db.maxStatements")));
        dataSource.setMaxStatementsPerConnection(
            Integer.valueOf(environment.getProperty("dm.db.maxStatementsPerConnection")));
        dataSource.setAcquireIncrement(
            Integer.valueOf(environment.getProperty("dm.db.acquireIncrement")));
        dataSource.setAcquireRetryAttempts(
            Integer.valueOf(environment.getProperty("dm.db.acquireRetryAttempts")));
        dataSource.setAutoCommitOnClose(
            Boolean.valueOf(environment.getProperty("dm.db.autoCommitOnClose")));
        dataSource.setAcquireRetryDelay(
            Integer.valueOf(environment.getProperty("dm.db.acquireRetryDelay")));
        dataSource.setMaxIdleTime(Integer.valueOf(environment.getProperty("dm.db.maxIdleTime")));
        dataSource.setBreakAfterAcquireFailure(
            Boolean.valueOf(environment.getProperty("dm.db.breakAfterAcquireFailure")));
        return dataSource;
    }
}

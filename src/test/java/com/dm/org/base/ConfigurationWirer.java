package com.dm.org.base;


import com.dm.org.webconfig.DataMiningPlatformWebAppInitializer;
import com.dm.org.webconfig.cache.EhCacheConfiguration;
import com.dm.org.webconfig.dataSource.impl.ComboPooledDataSourceConfig;
import com.dm.org.webconfig.hibernate.HibernateConfiguration;
import com.dm.org.webconfig.security.ShiroSecurityConfiguration;
import com.dm.org.webconfig.springMvc.SpringMvcConfiguration;
import com.dm.org.webconfig.transaction.HibernateTransactionConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;


/**
 * Controller 层集成环境测试基类
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:36 2017/7/22.
 * @since data-mining-platform
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(name = "parent", classes = {ShiroSecurityConfiguration.class,
        HibernateTransactionConfig.class, HibernateConfiguration.class, EhCacheConfiguration.class,
        ComboPooledDataSourceConfig.class}),
    @ContextConfiguration(name = "child", classes = SpringMvcConfiguration.class)})
public class ConfigurationWirer
{
    protected WebApplicationContext webApplicationContext;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext)
    {
        this.webApplicationContext = webApplicationContext;
    }
}

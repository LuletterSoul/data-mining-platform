package com.dm.org.base;


import com.dm.org.webconfig.DataMiningPlatformWebAppInitializer;
import com.dm.org.webconfig.cache.EhCacheConfiguration;
import com.dm.org.webconfig.dataSource.impl.ComboPooledDataSourceConfig;
import com.dm.org.webconfig.hibernate.HibernateConfiguration;
import com.dm.org.webconfig.security.ShiroSecurityConfiguration;
import com.dm.org.webconfig.springMvc.SpringMvcConfiguration;
import com.dm.org.webconfig.transaction.HibernateTransactionConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:36 2017/7/22.
 * @since data-minning-platform
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@ContextConfiguration(classes = {DataMiningPlatformWebAppInitializer.class,
//    ShiroSecurityConfiguration.class, HibernateTransactionConfig.class,
//    HibernateConfiguration.class, SpringMvcConfiguration.class, EhCacheConfiguration.class,
//    ComboPooledDataSourceConfig.class})
@ContextHierarchy({
@ContextConfiguration(name = "parent",
classes = { ShiroSecurityConfiguration.class,
            HibernateTransactionConfig.class,
            HibernateConfiguration.class,
            EhCacheConfiguration.class,
            ComboPooledDataSourceConfig.class
        }),
@ContextConfiguration(name = "child",classes = SpringMvcConfiguration.class)
})
public class ConfigurationWirer
{

}

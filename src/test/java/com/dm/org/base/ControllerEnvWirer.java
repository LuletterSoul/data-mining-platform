package com.dm.org.base;

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
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", classes = {
                HibernateTransactionConfig.class, HibernateConfiguration.class,
                ComboPooledDataSourceConfig.class}),
        @ContextConfiguration(name = "child", classes = SpringMvcConfiguration.class)})
public class ControllerEnvWirer {
    protected WebApplicationContext webApplicationContext;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }
}

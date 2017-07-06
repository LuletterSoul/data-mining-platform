package com.dm.org.base;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author ����� qq313700046@icloud.com .
 * @date created in  20:26 2017/6/29.
 * @description
 * @modified by:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = "classpath:spring-context.xml")
public class BaseServiceInitializer
{

    protected WebApplicationContext webApplicationContext;

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext)
    {
        this.webApplicationContext = webApplicationContext;
    }
}

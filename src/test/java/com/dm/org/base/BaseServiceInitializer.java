package com.dm.org.base;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qq313700046@icloud.com .
 * @date created in  20:26 2017/6/29.
 * @description
 * @modified by:
 */

public class BaseServiceInitializer extends ConfigurationWirer
{
    protected WebApplicationContext webApplicationContext;
    protected Map<String, Object> updateMapping = new HashMap<String, Object>();
    protected Map<String, Object> conditonMapping = new HashMap<String, Object>();

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext)
    {
        this.webApplicationContext = webApplicationContext;
    }
}

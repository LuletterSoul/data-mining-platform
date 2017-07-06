package com.dm.org.base;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:50 2017/7/5.
 * @description
 * @modified by:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class BaseDaoInitializer
{

    @Before
    public void before()
            throws Exception
    {

    }

    @After
    public void after()
            throws Exception
    {

    }

}

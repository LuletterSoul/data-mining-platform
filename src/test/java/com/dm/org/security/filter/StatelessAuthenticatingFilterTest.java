package com.dm.org.security.filter;


import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.http.HttpRequest;


/**
 * StatelessAuthenticatingFilter Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * ���� 15, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class StatelessAuthenticatingFilterTest
{
    StatelessAuthenticatingFilter filter = new StatelessAuthenticatingFilter();



    @Before
    public void before()
        throws Exception
    {}

    @After
    public void after()
        throws Exception
    {}

    /**
     * Method: onAccessDenied(ServletRequest request, ServletResponse response)
     */
    @Test
    public void testOnAccessDenied()
        throws Exception
    {

        // TODO: Test goes here...
    }

}

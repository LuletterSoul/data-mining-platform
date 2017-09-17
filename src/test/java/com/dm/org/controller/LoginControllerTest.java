package com.dm.org.controller;


import com.dm.org.base.ConfigurationWirer;
import com.dm.org.base.ControllerTestingInitializer;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * LoginController Tester.
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 * 
 *        <pre>
 * July 22, 2017
 *        </pre>
 * 
 * @version 1.0
 */
public class LoginControllerTest extends ConfigurationWirer
{
    private MockMvc loginControllerMcvMock;

    @Before
    public void before()
        throws Exception

    {
        MockitoAnnotations.initMocks(this);
        loginControllerMcvMock = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void after()
        throws Exception
    {}

    /**
     * Method: generateTimeOutSalt()
     */
    @Test
    public void testGetSalt()
        throws Exception
    {
        loginControllerMcvMock.perform(get("/doLogin/random_salt")).andDo(print());
    }

    /**
     * Method: login()
     */
    @Test
    public void testLogin()
                throws Exception
    {
        loginControllerMcvMock.perform(post("/login")).andDo(print());
    }

}

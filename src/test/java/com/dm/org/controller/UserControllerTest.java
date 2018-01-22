package com.dm.org.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dm.org.model.User;
import com.dm.org.utils.DateStyle;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dm.org.base.ConfigurationWirer;
import com.vero.dm.security.constants.Constants;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:11 2017/9/15.
 * @since data-mining-platform
 */

public class UserControllerTest extends ConfigurationWirer
{

    private MockMvc mockMvc;

    @Before
    public void setUp()
        throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /*
     * Test client send get time out token request.And result expect request accepted by server.
     */
    @Test
    public void getToken()
        throws Exception
    {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
        String timestamp = format.format(now);
        String username = "zhang";
        mockMvc.perform(post("/user/token").accept(MediaType.APPLICATION_JSON_UTF8).header(
            Constants.HEADER_TIMESTAMP, timestamp).param(Constants.PARAM_USERNAME,
                username)).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print());
    }
}
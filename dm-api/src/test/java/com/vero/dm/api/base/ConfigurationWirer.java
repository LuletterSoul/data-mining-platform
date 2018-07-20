package com.vero.dm.api.base;

import com.vero.dm.util.date.ConcurrencyDateFormatter;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vero.dm.api.entry.ApiLoader;
/**
 * Controller 层集成环境测试基类
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:41 2018/7/19.
 * @since data-mining-platform
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiLoader.class)
@AutoConfigureMockMvc
//@SpringApplicationConfiguration(classes = MockServletContext.class)//这个测试单个controller，不建议使用
@WebAppConfiguration
public class ConfigurationWirer
{

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected ObjectMapper objectMapper = new ObjectMapper();


    @Before
    public void setupMockMvc() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new ConcurrencyDateFormatter());

    }

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext)
    {
        this.webApplicationContext = webApplicationContext;
    }
}

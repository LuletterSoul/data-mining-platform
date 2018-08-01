package com.vero.dm.web.base;

import com.vero.dm.web.entry.DataMiningPlatformWebApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vero.dm.api.entry.ApiLoader;
import com.vero.dm.util.date.ConcurrencyDateFormatter;

/**
 * Controller 层集成环境测试基类
 * 注意{@Transactionnal}
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:41 2018/7/19.
 * @since data-mining-platform
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataMiningPlatformWebApplication.class)
@AutoConfigureMockMvc
//@SpringApplicationConfiguration(classes = MockServletContext.class)//这个测试单个controller，不建议使用
@WebAppConfiguration
@ActiveProfiles(profiles = {"dev"})
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
         // 反序列化的时候如果多了其他属性,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false);
        objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    }

    @Autowired
    public void setWebApplicationContext(WebApplicationContext webApplicationContext)
    {
        this.webApplicationContext = webApplicationContext;
    }
}

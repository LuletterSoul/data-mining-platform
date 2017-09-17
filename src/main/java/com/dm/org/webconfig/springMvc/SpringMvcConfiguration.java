package com.dm.org.webconfig.springMvc;


import com.dm.org.controller.ControllerScanningMarker;
import com.dm.org.utils.DateStyle;
import com.dm.org.utils.UtilClassScanningMarker;
import com.dm.org.webconfig.security.ShiroSecurityConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.hibernate.type.DateType;
import org.springframework.context.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 12:51 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Import(ShiroSecurityConfiguration.class)
@ComponentScan(basePackageClasses = {ControllerScanningMarker.class, UtilClassScanningMarker.class})
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter
{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addRedirectViewController("/", "/login");
        registry.addRedirectViewController("/index", "/static/index.html");
//        registry.addRedirectViewController("/login", "/static/login/login.html");
        registry.addRedirectViewController("/manager/data_set", "/static/manager/data_sets.html");
        registry.addRedirectViewController("/manager/student", "/static/manager/students.html");
        registry.addRedirectViewController("/manager/data_set", "/static/manager/data_sets.html");
    }

    @Bean
    public ViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.add(jsonConverter());
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        Jackson2ObjectMapperFactoryBean mapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        mapperFactoryBean.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapperFactoryBean.setDateFormat(new SimpleDateFormat(DateStyle.YYYY_MM_DD_HH_MM.getValue()));
        mapperFactoryBean.afterPropertiesSet();
        return mapperFactoryBean.getObject();
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonConverter()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaType = new ArrayList<MediaType>();
        supportedMediaType.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaType.add(MediaType.TEXT_PLAIN);
        supportedMediaType.add(MediaType.TEXT_HTML);
        converter.setSupportedMediaTypes(supportedMediaType);
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor
    authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager)
    {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

//    @Bean
//    public SimpleMappingExceptionResolver simpleMappingExceptionResolver()
//    {
//        return new SimpleMappingExceptionResolver();
//    }
}

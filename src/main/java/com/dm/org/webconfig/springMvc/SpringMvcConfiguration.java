package com.dm.org.webconfig.springMvc;


import com.dm.org.controller.ControllerScanningMarker;
import com.dm.org.security.credentials.StatelessCredentialsMatcher;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.impl.StatelessCredentialsServiceImpl;
import com.dm.org.utils.DateStyle;
import com.dm.org.utils.UtilClassScanningMarker;
import com.dm.org.webconfig.security.ShiroSecurityConfiguration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.hibernate.type.DateType;
import org.springframework.context.annotation.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
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
//@Import(ShiroSecurityConfiguration.class)
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

    @Bean
    public ViewResolver internalResourceViewResolver()
    {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

//    @Bean
//    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
//        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
//        List<HandlerMethodArgumentResolver> argumentResolvers = new LinkedList<HandlerMethodArgumentResolver>();
//        argumentResolvers.add(pageableHandlerMethodArgumentResolver());
//        adapter.setCustomArgumentResolvers(argumentResolvers);
//        return adapter;
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(pageableHandlerMethodArgumentResolver());
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

//    @Bean
//    public StatelessCredentialsService statelessCredentialsService()
//    {
//        StatelessCredentialsServiceImpl service = new StatelessCredentialsServiceImpl();
//        DefaultPasswordService defaultPasswordService = (DefaultPasswordService)service;
//        HashService hashService = defaultPasswordService.getHashService();
//        ((DefaultHashService)hashService).setGeneratePublicSalt(false);
//        return service;
//    }
//
    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }

}

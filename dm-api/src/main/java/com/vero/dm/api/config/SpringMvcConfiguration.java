package com.vero.dm.api.config;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vero.dm.api.converters.DateConverter;
import com.vero.dm.util.date.ConcurrencyDateFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vero.dm.api.converters.UniversalEnumConverterFactory;
import com.vero.dm.util.date.DateStyle;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 12:51 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter
{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new UniversalEnumConverterFactory());
        registry.addConverter(new DateConverter());
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(
            "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
            "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        super.addInterceptors(registry);
        // registry.addInterceptor(originalAccessHandler());
    }

    // @Bean
    // public ViewResolver internalResourceViewResolver()
    // {
    // InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    // resolver.setPrefix("/WEB-INF/jsp/");
    // resolver.setSuffix(".jsp");
    // resolver.setExposeContextBeansAsAttributes(true);
    // return resolver;
    // }

//    @Bean
//    public MultipartResolver multipartResolver()
//    {
//        return new CustomMultipartResolver();
//    }

    // @Bean
    // public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    // RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
    // List<HandlerMethodArgumentResolver> argumentResolvers = new
    // LinkedList<HandlerMethodArgumentResolver>();
    // argumentResolvers.add(pageableHandlerMethodArgumentResolver());
    // adapter.setCustomArgumentResolvers(argumentResolvers);
    // return adapter;
    // }

    // @Bean
    // public HandlerInterceptor originalAccessHandler()
    // {
    // return new AccessProcessInterceptor();
    // }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
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
        mapperFactoryBean.setDateFormat(new ConcurrencyDateFormatter());
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

    // @Bean
    // public AuthorizationAttributeSourceAdvisor
    // authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager)
    // {
    // AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
    // aasa.setSecurityManager(securityManager);
    // return aasa;
    // }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver()
    {
        return new PageableHandlerMethodArgumentResolver();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }


}

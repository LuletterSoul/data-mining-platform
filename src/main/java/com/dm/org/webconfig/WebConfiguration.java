//package com.dm.org.webconfig;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
///**
// * @author ÁõÏéµÂ qq313700046@icloud.com .
// * @date created in  22:40 2017/6/28.
// * @description
// * @modified by:
// */
//@Configuration
//@EnableWebMvc
//@ComponentScan("com.dm.org")
//public class WebConfiguration extends WebMvcConfigurerAdapter
//{
//    public ViewResolver viewResolver()
//    {
//        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
//        resourceViewResolver.setPrefix("/WEB-INF/views/");
//        resourceViewResolver.setSuffix(".jsp");
//        resourceViewResolver.setExposeContextBeansAsAttributes(true);
//        return resourceViewResolver;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
//    {
//        configurer.enable();}
//
//}
//

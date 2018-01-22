package com.vero.dm.web.aop;


import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 23:18 2017/7/17.
 * @description
 * @modified by:
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackageClasses = {AspectScanningMarker.class})
public class AopConfiguration
{

    /** Pointcut for execution of methods on {@link Service} annotation */
    @Pointcut("execution(* @org.springframework.stereotype.Service com.dm.org.service..*(..))")
    public void serviceAnnotation()
    {}

    /** Pointcut for execution of methods on {@link Repository} annotation */
    @Pointcut("execution(public * @org.springframework.stereotype.Repository com.dm.org.dao.impl..*(..))")
    public void repositoryAnnotation()
    {}

    @Pointcut("serviceAnnotation() || repositoryAnnotation()")
    public void performanceMonitor()
    {}

    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor()
    {
        CustomizableTraceInterceptor customizableTraceInterceptor = new CustomizableTraceInterceptor();
        customizableTraceInterceptor.setUseDynamicLogger(true);
        customizableTraceInterceptor.setEnterMessage("Entering $[methodName]($[arguments])");
        customizableTraceInterceptor.setExitMessage(
            "Leaving  $[methodName](), returned $[returnValue]");
        return customizableTraceInterceptor;
    }

    @Bean
    public Advisor performanceMonitorAdvisor()
    {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.vero.dm.web.aop.AopConfiguration.performanceMonitor()");
        return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
    }

}

package com.dm.org.webconfig.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  0:10 2017/7/18.
 * @description
 * @modified by:
 */
@Aspect
public class WebServiceAspect
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
}

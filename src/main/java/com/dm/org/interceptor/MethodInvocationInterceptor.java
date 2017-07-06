package com.dm.org.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 22:09 2017/7/5.
 * @description
 * @modified by:
 */

@Component(value = "methodInvocationInterceptor")
public class MethodInvocationInterceptor implements MethodInterceptor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(
        MethodInvocationInterceptor.class);

    public Object invoke(MethodInvocation invocation)
        throws Throwable
    {
        Object result = null;
        try
        {
            result = invocation.proceed();
            // get current invoked method name
            String methodName = invocation.getThis().toString() + "|"
                                + invocation.getMethod().getName();
            LOGGER.info("methodName={}", methodName);
            // get method parameters
            Object[] parameters = invocation.getArguments();

            for (int i = 0; i < parameters.length; i++ )
            {
                if (parameters[i] == null)
                {
                    continue;
                }
                LOGGER.info("\targ[{}] -> {}", i, parameters[i]);
            }
            // get method return value
            LOGGER.info("\treturn : {} - {} ", methodName, result);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
        return result;
    }
}

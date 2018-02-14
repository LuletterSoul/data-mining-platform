package com.vero.dm.security.filter;


import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.vero.dm.exception.ExceptionHandler;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:41 2018/2/15.
 * @since data-mining-platform
 */
@Slf4j
public class AuthenticationExceptionFilter extends AdviceFilter
{

    @Autowired
    @Qualifier("logoutExceptionHandler")
    private ExceptionHandler exceptionHandler;

    @Override
    protected void executeChain(ServletRequest request, ServletResponse response,
                                FilterChain chain)
    {
        try
        {
            chain.doFilter(request, response);
        }
        catch (Throwable throwable)
        {
            exceptionHandler.dispatchInternalExceptions(request, response, throwable);
        }
    }
}

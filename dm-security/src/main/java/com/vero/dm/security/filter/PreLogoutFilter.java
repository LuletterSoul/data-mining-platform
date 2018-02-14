package com.vero.dm.security.filter;


import static org.apache.shiro.web.util.WebUtils.getPathWithinApplication;
import static org.apache.shiro.web.util.WebUtils.toHttp;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vero.dm.exception.LogoutExceptionHandler;
import com.vero.dm.exception.auth.LogoutAccessException;
import com.vero.dm.exception.constract.HeaderLostException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.TokenManager;

import lombok.extern.slf4j.Slf4j;


/**
 * 单独的进行Rest风格下的系统登出API的过滤
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.1 created in 23:41 2018/2/14.
 * @since data-mining-platform
 */
@Slf4j
public class PreLogoutFilter extends AdviceFilter
{

    private String logoutApi;

    private final String DEFAULT_LOGOUT_API = "/tokens";

    @Autowired
    @Qualifier("logoutExceptionHandler")
    private LogoutExceptionHandler exceptionHandler;

    @Autowired
    private TokenManager tokenManager;

    public void setLogoutApi(String logoutApi)
    {
        this.logoutApi = logoutApi;
    }

    public String getLogoutApi()
    {
        return logoutApi;
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response,
                                 FilterChain chain)
        throws ServletException,
        IOException
    {
        try
        {
            HttpServletRequest httpServletRequest = toHttp(request);
            preLogout(httpServletRequest);
            chain.doFilter(request, response);
        }
        catch (Throwable e)
        {
            exceptionHandler.dispatchInternalExceptions(request, response, e);
        }
    }

    private void preLogout(HttpServletRequest httpServletRequest)
    {
        if (isDeleteHttpRequest(httpServletRequest))
        {
            String path = getPathWithinApplication(httpServletRequest);
            String logoutApi = getLogoutApi();
            pathMatchLogout(httpServletRequest, path, logoutApi);
        }
    }

    private void pathMatchLogout(HttpServletRequest httpServletRequest, String path,
                                 String logoutApi)
    {
        if (logoutApi == null)
        {
            logoutApi = DEFAULT_LOGOUT_API;
        }
        if (path.equals(logoutApi))
        {
            validateLogoutAccessToken(httpServletRequest);
        }
    }

    private void validateLogoutAccessToken(HttpServletRequest httpServletRequest)
    {
        String accessToken = httpServletRequest.getHeader(Constants.ACCESS_TOKEN_HEADER);
        if (accessToken == null)
        {
            String message = "Access Token Lost.";
            throw new HeaderLostException(message, ExceptionCode.HeaderLost);
        }
        log.info("Receive a Log Out Request form [{}]", accessToken);
        if (!tokenManager.isAccessTokenRegistered(accessToken))
        {
            throw new LogoutAccessException("Access Token Not Register.Not-Callable Log out Post.",
                ExceptionCode.LogoutTokenNotExist);
        }
    }

    private boolean isDeleteHttpRequest(HttpServletRequest httpServletRequest)
    {
        return httpServletRequest.getMethod().toUpperCase().equals("DELETE");
    }
}

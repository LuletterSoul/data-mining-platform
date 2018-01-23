package com.vero.dm.security.filter;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public class AllowOriginFilter extends AdviceFilter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AllowOriginFilter.class);

    public final static String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    public final static String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    public final static String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    public final static String DEFAULT_ALLOW_HEADERS = "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-timestamp,Username,X-ApiKey";

    public final static String DEFAULT_ALLOW_METHODS = "PUT,POST,GET,DELETE,OPTIONS";

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response)
        throws Exception
    {
        HttpServletRequest httpRequest = assertHttpRequest(request);
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String origin = httpRequest.getHeader("Origin");
        if (origin == null)
        {
            return true;
        }
        configureResponseAllowHeaders(httpResponse, origin);
        return !isHttpOptionRequest(httpRequest, httpResponse);
    }

    private boolean isHttpOptionRequest(HttpServletRequest httpRequest,
                                             HttpServletResponse httpResponse)
{
    String method = httpRequest.getMethod();
    if (method.equals("OPTIONS"))
    {
        httpResponse.setStatus(200);
        return true;
    }
    return false;
}

    private void configureResponseAllowHeaders(HttpServletResponse httpResponse, String origin)
    {
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, DEFAULT_ALLOW_HEADERS);
        httpResponse.setHeader(ACCESS_CONTROL_ALLOW_METHODS, DEFAULT_ALLOW_METHODS);
    }

    private HttpServletRequest assertHttpRequest(ServletRequest request)
        throws ServletException
    {
        HttpServletRequest httpRequest;
        if (request instanceof HttpServletRequest)
        {
            httpRequest = (HttpServletRequest)request;
        }
        else
        {
            LOGGER.info("System deny request:server request should match HTTP protocol only.");
            throw new ServletException(
                "Server request must be HTTP only.Check your request validity and send it again.");
        }
        return httpRequest;
    }
}

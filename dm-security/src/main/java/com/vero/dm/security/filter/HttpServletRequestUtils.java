package com.vero.dm.security.filter;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:42 2018/2/11.
 * @since data-mining-platform
 */

@Slf4j
public class HttpServletRequestUtils
{
    public static HttpServletRequest assertHttpRequest(ServletRequest request)
        throws ServletException
    {
        HttpServletRequest httpRequest;
        if (request instanceof HttpServletRequest)
        {
            httpRequest = (HttpServletRequest)request;
        }
        else
        {
            log.info("System deny request:server request should match HTTP protocol only.");
            throw new ServletException(
                "Server request must be HTTP only.Check your request validity and send it again.");
        }
        return httpRequest;
    }
}

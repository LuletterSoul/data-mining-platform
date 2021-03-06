package com.vero.dm.security.filter;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.TokenGenerator;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.security.credentials.UserProfileAccessor;
import com.vero.dm.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Slf4j
public class AllowOriginFilter extends AdviceFilter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AllowOriginFilter.class);

    public final static String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    public final static String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    public final static String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

    public final static String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

    public final static String DISPOSABLE_HEADER = "X-Access-Token";

    public final static String DEFAULT_ALLOW_HEADERS = "Content-Type,Content-Length, Authorization, Accept,X-Access-Token,X-Timestamp,X-Disposable-Token,X-Username,X-Client-Digest,X-Apply-Credential,X-Pre-Token";

    public final static String DEFAULT_ALLOW_METHODS = "PUT,POST,GET,DELETE,OPTIONS";

    public final static String DEFAULT_EXPOSE_HEADER = "X-Disposable-Token,X-Suggested-Filename";

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

//    @Override
//    protected void postHandle(ServletRequest request, ServletResponse response)
//        throws Exception
//    {
//        HttpServletRequest httpRequest = assertHttpRequest(request);
//        HttpServletResponse httpResponse = (HttpServletResponse)response;
//        String accessToken = httpRequest.getHeader(Constants.ACCESS_TOKEN_HEADER);
//        if (accessToken != null)
//        {
//            if (tokenManager.isAccessTokenRegistered(accessToken))
//            {
//                String username = profileAccessor.fetchProfile(accessToken).getUsername();
//                String privateSalt = userService.fetchPrivateSalt(username);
//                String disposableToken = tokenGenerator.generateExpiredToken(accessToken,
//                    privateSalt);
//                tokenManager.signToken(accessToken, disposableToken);
//                httpResponse.setHeader(Constants.DISPOSABLE_TOKEN_HEADER, disposableToken);
//                if (log.isDebugEnabled())
//                {
//                    log.debug("Update disposable token form [{}].", username);
//                }
//            }
//        }
//    }

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
        httpResponse.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, DEFAULT_EXPOSE_HEADER);
        httpResponse.setHeader(Constants.DISPOSABLE_TOKEN_HEADER, "");
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

package com.dm.org.security.filter;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dm.org.exceptions.error.ErrorInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dm.org.security.constants.Constants;
import com.dm.org.security.realm.StatelessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 12:04 2017/7/17.
 * @description
 * @modified by:
 */
public class StatelessAuthenticatingFilter extends AccessControlFilter
{


    private static final Logger LOGGER = LoggerFactory.getLogger(
        StatelessAuthenticatingFilter.class);
    private ObjectMapper objectMapper;

    public StatelessAuthenticatingFilter() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue)
        throws Exception
    {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws Exception
    {
        HttpServletRequest httpRequest = null;
        try
        {

            httpRequest = assertHttpRequest(request);
            // 获取请求发送的时间戳
            String timestamp = httpRequest.getHeader(Constants.HEADER_TIMESTAMP);
            // 客户端生成的消息摘要
            String clientDigest = httpRequest.getParameter(Constants.PARAM_DIGEST);
            // 客户端传入的用户身份
            String username = httpRequest.getParameter(Constants.PARAM_USERNAME);

            assertNegotiationContentNotNull(clientDigest, username, timestamp);
            // 客户端请求的参数列表
            StatelessToken token = generateAuthenticationToken(request, clientDigest, username);
            LOGGER.info("System generate authentication token: {}", token);
            try
            {
                // 委托给Realm进行登录
                getSubject(request, response).login(token);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                LOGGER.info("Authentication Fail:{}", e.getMessage());
                onLoginFail(response,e); // 6、登录失败
                return false;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private StatelessToken generateAuthenticationToken(ServletRequest request, String clientDigest,
                                                       String username)
    {
        Map<String, String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        params.remove(Constants.PARAM_DIGEST);
        // 生成无状态Token
        return new StatelessToken(username, params, clientDigest);
    }

    private void assertNegotiationContentNotNull(String clientDigest, String username,
                                                 String timestamp)
        throws ServletException
    {
        if (clientDigest == null || username == null || timestamp == null)
        {
            throw new ServletException(
                "User token shouldn't be empty.Make sure your account info has been linked with request.");
        }
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

    // 登录失败时默认返回401状态码
    private void onLoginFail(ServletResponse response,Throwable ex)
        throws IOException
    {
        String message = "Authentication Failed.Please check your username or password  is correct or not.";
        ErrorInfo errorInfo = new ErrorInfo(ex, message, HttpStatus.UNAUTHORIZED);
        String jsonMessage = objectMapper.writeValueAsString(errorInfo);
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write(jsonMessage);
    }
}
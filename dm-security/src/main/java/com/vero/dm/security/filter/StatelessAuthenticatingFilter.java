package com.vero.dm.security.filter;


import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vero.dm.exception.error.ErrorInfo;
import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.realm.StatelessToken;


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
            if (httpRequest.getMethod().equals("OPTIONS"))
            {
                return true;
            }
            // 获取请求发送的时间戳
            String timestamp = httpRequest.getHeader(Constants.HEADER_TIMESTAMP);
            // 客户端生成的消息摘要
            String clientDigest = httpRequest.getParameter(Constants.PARAM_DIGEST);
            // 客户端传入的用户身份
            String username = httpRequest.getParameter(Constants.PARAM_USERNAME);
            String apiKey = httpRequest.getHeader(Constants.API_KEY);
            try {
                assertNegotiationContentNotNull(clientDigest, username, timestamp,apiKey);
            } catch (ServletException e) {
                LOGGER.error(e.getMessage());
                onAccessFailed(response, e);
                e.printStackTrace();
                return false;
            }
            // 客户端请求的参数列表
            StatelessToken token = generateAuthenticationToken(request, clientDigest, username,apiKey);
            LOGGER.info("System generate authentication token: {}", token);
            try
            {
                // 委托给Realm进行登录
                getSubject(request, response).login(token);
                LOGGER.info("{}: authenticate successfully.", username);
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
                                                       String username,String apiKey)
    {
        Map<String, String[]> params = new LinkedHashMap<String, String[]>(request.getParameterMap());
        params.remove(Constants.PARAM_DIGEST);
        // 生成无状态Token
        return new StatelessToken(username,apiKey, params, clientDigest);
    }

    private void assertNegotiationContentNotNull(String clientDigest, String username,
                                                 String timestamp,String apiKey)
        throws ServletException
    {
        if (clientDigest == null || username == null || timestamp == null ||apiKey == null)
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
    private void onLoginFail(ServletResponse response,Throwable ex) throws IOException {
        String message = "Authentication Failed.Please check your username or password is correct or not.";
        ErrorInfo errorInfo = new ErrorInfo(ex, message, HttpStatus.UNAUTHORIZED);
        String jsonMessage = objectMapper.writeValueAsString(errorInfo);
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write(jsonMessage);
    }

    private void onAccessFailed(ServletResponse response, Throwable ex) throws IOException {
        String message = "Access Failed.Request don't match negotiation content.";
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        ErrorInfo errorInfo = new ErrorInfo(ex, message, HttpStatus.BAD_REQUEST);
        String jsonMessage = objectMapper.writeValueAsString(errorInfo);
        httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        httpResponse.getWriter().write(jsonMessage);
    }
}
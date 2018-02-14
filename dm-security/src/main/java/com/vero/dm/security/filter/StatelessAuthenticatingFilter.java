package com.vero.dm.security.filter;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import com.vero.dm.exception.DefaultExceptionHandler;
import com.vero.dm.exception.ExceptionHandler;
import com.vero.dm.exception.auth.InternalAuthenticationException;
import com.vero.dm.exception.constract.HeaderLostException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.DisposableTokenWriter;
import com.vero.dm.security.realm.StatelessToken;

import lombok.extern.slf4j.Slf4j;


/**
 * 无状态请求过滤器,该过滤器会检测请求头是否包含跟客户端协商好信息： 即用户名、时间戳、消息摘要、证书信息 对每个请求的鉴权/授权逻辑会交由
 * {@link com.vero.dm.security.realm.StatelessRealm}完成; 注意当前Filter在Shiro代理Filter的拦截路径中 尚未进入Spring
 * Mvc的体系，所以产生的异常不能够被 {@link org.springframework.web.client.RestClientException} 全局异常捕捉器捕捉到
 * 为了生成更友好、统一的信息则需要{@link DefaultExceptionHandler} 统一处理协商内容鉴定、身份认证过程抛出的异常;
 * 在每次API授权后,{@link #afterAuthentication(HttpServletResponse, String)} 会更新服务器端当前身份证书对应的一次性令牌
 * 并将该一次性令牌写入response header 中去,这样有效减少了客户端频繁申请一次性令牌的请求,
 * 但这种会存在并发访问问题:一对一的关系确定了一次性令牌只针对“下一趟”请求,一趟{@link HttpServletRequest}结束后,
 * 对应的{@link HttpServletResponse}将最新的一次性token提交给客户端, 然而假设客户端在异步发出多个请求时,使用了同一个全局一次性Token(来自服务器签发),
 * 服务器针对每个请求更新了服务器端的“下一趟”一次性Token, 这时就出现了客户端与服务器端的一次性Token不同步的问题。
 * 解决方案见{@link com.vero.dm.security.credentials.DisposableTokenMaintainer}
 * 值得注意的是在{@link HttpServletResponse #setHeader}必须在response commit之前写入,否则在commit之后该操作会无效
 * 
 * @see com.vero.dm.security.credentials.DisposableTokenMaintainer
 * @see com.vero.dm.security.credentials.DisposableTokenWriter
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 12:04 2017/7/17.
 * @description
 * @modified by:
 */
@Slf4j
public class StatelessAuthenticatingFilter extends AccessControlFilter
{

    @Autowired
    @Qualifier("defaultExceptionHandler")
    private ExceptionHandler exceptionHandler;

    @Autowired
    private DisposableTokenWriter disposableTokenWriter;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                      Object mappedValue)
        throws Exception
    {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
        throws Exception
    {
        try
        {
            return !doRestApiAccessAuthentication(request, response, buildStatelessToken(request));
        }
        catch (Throwable e)
        {
            exceptionHandler.dispatchInternalExceptions(request, response, e);
            return false;
        }
    }

    private StatelessToken buildStatelessToken(ServletRequest request)
        throws ServletException
    {
        HttpServletRequest httpRequest = HttpServletRequestUtils.assertHttpRequest(request);
        // 历史证书
        String preToken = httpRequest.getHeader(Constants.ACCESS_BY_PRE_TOKEN);
        // 获取请求发送的时间戳
        String timestamp = httpRequest.getHeader(Constants.TIMESTAMP_HEADER);
        // 客户端传入的认证信息
        String accessToken = httpRequest.getHeader(Constants.ACCESS_TOKEN_HEADER);
        // 客户端生成的消息摘要
        String clientDigest = httpRequest.getHeader(Constants.CLIENT_DIGEST_HEADER);
        // 客户端传入的用户身份
        String username = httpRequest.getHeader(Constants.USERNAME_HEADER);
        Boolean tokenAvailable = isAccessTokenAvailable(clientDigest, username, timestamp,
            accessToken, preToken);
        // 客户端请求的参数列表
        return generateStatelessToken(request, clientDigest, username, accessToken,
            tokenAvailable);
    }

    private boolean doRestApiAccessAuthentication(ServletRequest request, ServletResponse response,
                                                  StatelessToken token)
    {
        try
        {
            // 委托给Realm进行登录
            getSubject(request, response).login(token);
            // 授权后置处理
            afterAuthentication((HttpServletResponse)response, token.getAccessToken());
        }
        catch (AuthenticationException e)
        {
            return transferException(e);
        }
        return false;
    }

    private boolean transferException(AuthenticationException e)
    {
        InternalAuthenticationException ex;
        if (e instanceof InternalAuthenticationException)
        {
            ex = (InternalAuthenticationException)e;
        }
        else
        {
            String message = "Authentication Failed.";
            ex = new InternalAuthenticationException(message, ExceptionCode.AuthenticationError);
        }
        throw ex;
    }

    protected void afterAuthentication(HttpServletResponse httpServletResponse, String accessToken)
    {
        disposableTokenWriter.write(httpServletResponse, accessToken);
    }

    private StatelessToken generateStatelessToken(ServletRequest request, String clientDigest,
                                                  String username, String accessToken,
                                                  Boolean tokenAvailable)
    {
        Map<String, String[]> params = new LinkedHashMap<>(request.getParameterMap());
        params.remove(Constants.CLIENT_DIGEST_HEADER);
        // 生成无状态Token
        return new StatelessToken(username, accessToken, params, clientDigest, tokenAvailable);
    }

    private boolean isAccessTokenAvailable(String clientDigest, String username, String timestamp,
                                           String accessToken, String preToken)
    {

        Boolean isAccessByPreToken = StringUtils.isEmpty(preToken);
        Boolean isNull = StringUtils.isEmpty(clientDigest) || StringUtils.isEmpty(username)
                         || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(accessToken);
        if (isNull)
        {
            if (!StringUtils.isEmpty(accessToken) && !isAccessByPreToken)
            {
                return true;
            }
            String message = "Some critical credentials required.Make sure your credentials has been linked with request.";
            throw new HeaderLostException(message, ExceptionCode.HeaderLost);
        }
        return false;
    }
}
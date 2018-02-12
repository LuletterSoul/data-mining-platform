//package com.vero.dm.security.filter;
//
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.springframework.util.StringUtils;
//
//import com.vero.dm.exception.constract.HeaderLostException;
//import com.vero.dm.exception.error.ExceptionCode;
//import com.vero.dm.security.constants.Constants;
//import com.vero.dm.security.credentials.TokenExpiredChecker;
//import com.vero.dm.exception.PreAuthExceptionHandler;
//
//
///**
// * 检验客户端传入令牌的合法性
// *
// * @author XiangDe Liu qq313700046@icloud.com .
// * @version 1.5 created in 13:35 2018/2/11.
// * @since data-mining-platform
// */
//
//public class PreAccessTokenAuthFilter extends AccessControlFilter
//{
//    private PreAuthExceptionHandler preAuthExceptionHandler;
//
//    private TokenExpiredChecker expiredChecker;
//
//    public PreAccessTokenAuthFilter(PreAuthExceptionHandler preAuthExceptionHandler,
//                                    TokenExpiredChecker expiredChecker)
//    {
//        this.preAuthExceptionHandler = preAuthExceptionHandler;
//        this.expiredChecker = expiredChecker;
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
//                                      Object mappedValue)
//        throws Exception
//    {
//        HttpServletRequest httpRequest = null;
//        try
//        {
//            httpRequest = HttpServletRequestUtils.assertHttpRequest(request);
//            // 客户端传入的认证信息
//            String accessToken = httpRequest.getHeader(Constants.ACCESS_TOKEN_HEADER);
//            StringUtils.isEmpty(clientDigest) || StringUtils.isEmpty(username)
//                    || StringUtils.isEmpty(timestamp) ||
//            if (StringUtils.isEmpty(accessToken))
//            {
//                String message = "Lost access token request header.";
//                throw new HeaderLostException(message, ExceptionCode.HeaderLost);
//            }
//            if (expiredChecker.isTokenExpired(accessToken)) {
//                // 委托给Realm进行登录
//                getSubject(request, response).login();
//                return true;
//            }
//        }
//        catch (Throwable e)
//        {
//            preAuthExceptionHandler.dispatchInternalExceptions(request, response, e);
//        }
//        return false;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
//        throws Exception
//    {
//        return false;
//    }
//}

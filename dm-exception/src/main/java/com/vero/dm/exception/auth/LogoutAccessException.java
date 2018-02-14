package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 *
 * 提交登出请求时,系统发现未注册的访问令牌时抛出此异常
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:22 2018/2/15.
 * @since data-mining-platform
 */

public class LogoutAccessException extends AccessTokenNotExistException
{
    private static final long serialVersionUID = -4799987754042656248L;

    public LogoutAccessException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

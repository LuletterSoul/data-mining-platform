package com.vero.dm.exception.auth;

import com.vero.dm.exception.error.ExceptionCode;

/**
 * 多点登录异常
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:28 2018/2/10.
 * @since data-mining-platform
 */

public class MultipleEndpointsAccessExceptionInternal extends InternalAuthenticationException
{
    public MultipleEndpointsAccessExceptionInternal(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

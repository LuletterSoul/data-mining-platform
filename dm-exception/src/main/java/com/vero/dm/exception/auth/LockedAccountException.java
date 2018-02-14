package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:25 2018/2/12.
 * @since data-mining-platform
 */

public class LockedAccountException extends InternalAuthenticationException
{
    public LockedAccountException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

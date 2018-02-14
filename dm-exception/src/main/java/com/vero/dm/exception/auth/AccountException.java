package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:10 2018/2/10.
 * @since data-mining-platform
 */

public class AccountException extends InternalAuthenticationException
{
    private static final long serialVersionUID = 2886390409931359181L;

    public AccountException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

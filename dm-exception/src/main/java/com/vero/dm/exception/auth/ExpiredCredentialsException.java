package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * 证书过期抛出此异常
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:08 2018/2/10.
 * @since data-mining-platform
 */

public class ExpiredCredentialsException extends CredentialsException
{
    private static final long serialVersionUID = 405365495877385894L;

    public ExpiredCredentialsException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

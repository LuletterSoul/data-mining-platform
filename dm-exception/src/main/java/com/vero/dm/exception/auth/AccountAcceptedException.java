package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:06 2018/8/3.
 * @since data-mining-platform
 */

public class AccountAcceptedException extends CredentialsException
{
    public AccountAcceptedException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

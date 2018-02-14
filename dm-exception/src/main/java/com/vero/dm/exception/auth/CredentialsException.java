package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:27 2018/2/12.
 * @since data-mining-platform
 */

public class CredentialsException extends InternalAuthenticationException
{
    private static final long serialVersionUID = 4672598367808721872L;

    public CredentialsException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

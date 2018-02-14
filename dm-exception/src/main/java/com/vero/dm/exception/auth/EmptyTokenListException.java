package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 9:51 2018/2/13.
 * @since data-mining-platform
 */

public class EmptyTokenListException extends CredentialsException
{
    public EmptyTokenListException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

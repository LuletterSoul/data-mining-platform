package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:40 2018/8/3.
 * @since data-mining-platform
 */

public class RegisterInValidException extends CredentialsException
{
    public RegisterInValidException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

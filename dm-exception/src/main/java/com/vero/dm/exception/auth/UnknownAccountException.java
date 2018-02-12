package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:09 2018/2/10.
 * @since data-mining-platform
 */

public class UnknownAccountException extends AccountException
{
    private static final long serialVersionUID = 1093924222572890705L;

    public UnknownAccountException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

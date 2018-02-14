package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:25 2018/2/12.
 * @since data-mining-platform
 */

public class ConcurrentAccessException extends AccountException
{
    private static final long serialVersionUID = 4570998481920634940L;

    public ConcurrentAccessException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

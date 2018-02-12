package com.vero.dm.exception.common;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:40 2018/2/12.
 * @since data-mining-platform
 */

public class ErrorCodeLostNotFoundException extends CommonException
{
    public ErrorCodeLostNotFoundException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

package com.vero.dm.exception.business;


import com.vero.dm.exception.PlatformBaseException;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * 一般的业务出错会抛出此异常
 *
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:20 2018/2/16.
 * @since data-mining-platform
 */

public class BusinessException extends PlatformBaseException
{
    private static final long serialVersionUID = -6464384008238537166L;

    public BusinessException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

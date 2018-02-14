package com.vero.dm.exception;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * 数据挖掘平台统一异常体系
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:50 2018/2/10.
 * @since data-mining-platform
 */

public class PlatformBaseException extends RuntimeException
{
    private static final long serialVersionUID = 7118879510599889612L;
    /**
     * 业务错误码
     */
    private ExceptionCode errorCode;

    public PlatformBaseException(ExceptionCode errorCode)
    {
        this.errorCode = errorCode;
    }

    public PlatformBaseException(String message, ExceptionCode errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public PlatformBaseException(String message, Throwable cause, ExceptionCode errorCode)
    {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public PlatformBaseException(Throwable cause, ExceptionCode errorCode)
    {
        super(cause);
        this.errorCode = errorCode;
    }

    public PlatformBaseException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace, ExceptionCode errorCode)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode() {
        return errorCode;
    }
}

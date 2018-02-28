package com.vero.dm.exception.common;


import com.vero.dm.exception.PlatformBaseException;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:31 2018/2/27.
 * @since data-mining-platform
 */

public class DataDuplicatedException extends PlatformBaseException
{
    private static final long serialVersionUID = 5499038750115495740L;

    public DataDuplicatedException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

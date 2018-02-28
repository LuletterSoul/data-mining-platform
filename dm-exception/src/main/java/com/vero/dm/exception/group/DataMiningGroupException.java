package com.vero.dm.exception.group;


import com.vero.dm.exception.PlatformBaseException;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:18 2018/2/27.
 * @since data-mining-platform
 */

public class DataMiningGroupException extends PlatformBaseException
{
    public DataMiningGroupException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

package com.vero.dm.exception.group;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:16 2018/7/21.
 * @since data-mining-platform
 */

public class InvalidGroupingConfigException extends DataMiningGroupException
{
    private static final long serialVersionUID = -6317325723268258766L;

    public InvalidGroupingConfigException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

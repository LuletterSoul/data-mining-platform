package com.vero.dm.exception.group;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:20 2018/3/5.
 * @since data-mining-platform
 */

public class PreviewGroupsNotFoundException extends DataMiningGroupException
{
    public PreviewGroupsNotFoundException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

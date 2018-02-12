package com.vero.dm.exception.constract;


import com.vero.dm.exception.PlatformBaseException;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * 服务器与客户端的契约异常
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:36 2018/2/10.
 * @since data-mining-platform
 */

public class ContractException extends PlatformBaseException
{
    private static final long serialVersionUID = 374101573673974630L;

    public ContractException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

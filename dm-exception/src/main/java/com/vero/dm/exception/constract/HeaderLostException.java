package com.vero.dm.exception.constract;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * 当客户端的请求头缺失抛出此异常
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:37 2018/2/10.
 * @since data-mining-platform
 */

public class HeaderLostException extends ContractException
{
    private static final long serialVersionUID = 4584974867786312025L;

    public HeaderLostException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

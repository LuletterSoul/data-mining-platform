package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * 提交的证书不存在时抛出此异常
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:11 2018/2/10.
 * @since data-mining-platform
 */

public class AccessTokenNotExistException extends CredentialsException
{
    private static final long serialVersionUID = 1485901383795621977L;

    public AccessTokenNotExistException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

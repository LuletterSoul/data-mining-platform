package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * 账号、密码不正确抛出此异常
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:07 2018/2/10.
 * @since data-mining-platform
 */

public class IncorrectCredentialsException extends AccountException
{
    private static final long serialVersionUID = 104453529582436731L;

    public IncorrectCredentialsException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

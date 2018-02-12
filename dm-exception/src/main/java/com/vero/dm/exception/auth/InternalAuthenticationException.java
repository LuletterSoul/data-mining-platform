package com.vero.dm.exception.auth;


import lombok.Getter;
import org.apache.shiro.authc.AuthenticationException;

import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:06 2018/2/10.
 * @since data-mining-platform
 */

@Getter
public class InternalAuthenticationException extends AuthenticationException
{
    private static final long serialVersionUID = 6967770134007772823L;

    private ExceptionCode errorCode;

    public InternalAuthenticationException(String message, ExceptionCode errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }
}

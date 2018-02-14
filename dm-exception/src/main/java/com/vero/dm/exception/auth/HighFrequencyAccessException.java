package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:30 2018/2/14.
 * @since data-mining-platform
 */

public class HighFrequencyAccessException extends CredentialsException
{
    private static final long serialVersionUID = 6076652458198266290L;

    public HighFrequencyAccessException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

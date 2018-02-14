package com.vero.dm.exception.auth;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:02 2018/2/13.
 * @since data-mining-platform
 */

public class CandidateTokenSizeException extends CredentialsException
{
    public CandidateTokenSizeException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

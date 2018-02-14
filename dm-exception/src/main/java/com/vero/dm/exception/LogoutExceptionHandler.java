package com.vero.dm.exception;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.vero.dm.exception.auth.LogoutAccessException;
import com.vero.dm.exception.error.ExceptionCode;
import org.springframework.stereotype.Component;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 2:13 2018/2/15.
 * @since data-mining-platform
 */
@Component
public class LogoutExceptionHandler extends DefaultExceptionHandler
{
    @Override
    protected boolean handleSpecialException(HttpServletRequest request,
                                             HttpServletResponse response, ExceptionCode code,
                                             Throwable exception)
    {
        if (exception instanceof LogoutAccessException)
        {
            handPreAuthenticationInternalException(exception, code, HttpStatus.BAD_REQUEST,
                response, HttpStatus.BAD_REQUEST);
            return true;
        }
        return false;
    }
}

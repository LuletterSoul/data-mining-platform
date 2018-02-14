package com.vero.dm.exception;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:42 2018/2/11.
 * @since data-mining-platform
 */

public interface ExceptionHandler
{

    void dispatchInternalExceptions(ServletRequest request, ServletResponse response,
                                    Throwable exception);

    void handPreAuthenticationInternalException(Throwable ex,
                                                ExceptionCode exceptionCode,
                                                HttpStatus status,
                                                HttpServletResponse response,
                                                HttpStatus responseCode);
}

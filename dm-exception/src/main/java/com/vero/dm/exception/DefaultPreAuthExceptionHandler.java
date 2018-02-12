package com.vero.dm.exception;


import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vero.dm.exception.auth.*;
import com.vero.dm.exception.common.ErrorCodeLostNotFoundException;
import com.vero.dm.exception.constract.HeaderLostException;
import com.vero.dm.exception.error.ErrorInfo;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:45 2018/2/11.
 * @since data-mining-platform
 */

@Component
public class DefaultPreAuthExceptionHandler implements PreAuthExceptionHandler
{
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public void dispatchInternalExceptions(ServletRequest request, ServletResponse response,
                                           Throwable exception)
    {
        HttpServletResponse httpServletResponse = toHttpResponse(response);
        ExceptionCode exceptionCode = getExceptionCode(exception);
        if (exception instanceof ErrorCodeLostNotFoundException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode, HttpStatus.NOT_FOUND,
                httpServletResponse, HttpServletResponse.SC_OK);
        }
        else if (exception instanceof HeaderLostException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode,
                HttpStatus.BAD_REQUEST, httpServletResponse, HttpServletResponse.SC_OK);
        }

        else if (exception instanceof ExpiredCredentialsException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode, HttpStatus.FORBIDDEN,
                httpServletResponse, HttpServletResponse.SC_OK);
        }
        else if (exception instanceof UnknownAccountException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode, HttpStatus.FORBIDDEN,
                httpServletResponse, HttpServletResponse.SC_OK);
        }
        else if (exception instanceof IncorrectCredentialsException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode, HttpStatus.FORBIDDEN,
                httpServletResponse, HttpServletResponse.SC_OK);
        }
        else if (exception instanceof AccessTokenNotExistException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode,
                HttpStatus.UNAUTHORIZED, httpServletResponse, HttpServletResponse.SC_OK);
        }
        else if (exception instanceof ConcurrentAccessException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode, HttpStatus.LOCKED,
                httpServletResponse, HttpServletResponse.SC_OK);
        }
        else if (exception instanceof InternalAuthenticationException)
        {
            handPreAuthenticationInternalException(exception, exceptionCode,
                HttpStatus.UNAUTHORIZED, httpServletResponse, HttpServletResponse.SC_OK);
        }
    }

    private ExceptionCode getExceptionCode(Throwable exception)
    {
        if (exception instanceof PlatformBaseException)
        {
            return ((PlatformBaseException)exception).getErrorCode();
        }
        else if (exception instanceof InternalAuthenticationException)
        {
            return ((InternalAuthenticationException)exception).getErrorCode();
        }
        throw new ErrorCodeLostNotFoundException("Error info not found.",
            ExceptionCode.ErrorCodeLost);
    }

    private HttpServletResponse toHttpResponse(ServletResponse response)
    {
        if (response instanceof HttpServletResponse)
        {
            return (HttpServletResponse)response;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void handPreAuthenticationInternalException(Throwable ex, ExceptionCode exceptionCode,
                                                       HttpStatus status,
                                                       HttpServletResponse response,
                                                       int responseCode)
    {
        ErrorInfo errorInfo = new ErrorInfo(ex, exceptionCode.getCode(), exceptionCode.getTip(),
            status);
        String jsonMessage = null;
        try
        {
            jsonMessage = objectMapper.writeValueAsString(errorInfo);
            response.setStatus(responseCode);
            response.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
            try
            {
                OutputStream stream = response.getOutputStream();
                stream.write(jsonMessage.getBytes("UTF-8"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

    }
}

package com.dm.org.exceptions;


import com.dm.org.exceptions.error.ErrorInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:14 2017/7/30.
 * @since data-mining-platform
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status, WebRequest request)
    {
        return new ResponseEntity<Object>(
            new ErrorInfo(ex, (body != null) ? body.toString() : null, status), headers, status);
    }

    // API

    // 400

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request)
    {
        return handleExceptionInternal(ex, "The provided request body is not readable!", headers,
            BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request)
    {
        return handleExceptionInternal(ex, "The request parameters were not valid!", headers,
            BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers, HttpStatus status,
                                                        WebRequest request)
    {
        return handleExceptionInternal(ex, "The request parameters were not valid!", headers,
            BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class,
        IllegalArgumentException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex,
                                                    final WebRequest request)
    {
        return handleExceptionInternal(ex, "The request parameters were not valid!",
            new HttpHeaders(), BAD_REQUEST, request);
    }

    // 403

    // 404

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex,
                                                    final WebRequest request)
    {
        return handleExceptionInternal(ex, "Required / requested resource not found!",
            new HttpHeaders(), NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex,
                                                   final WebRequest request)
    {
        return handleExceptionInternal(ex, "The resource already exists!", new HttpHeaders(),
            CONFLICT, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex,
                                                   final WebRequest request)
    {
        return handleExceptionInternal(ex, "The resource is used in another model!",
            new HttpHeaders(), CONFLICT, request);
    }

    // 412

    // 500

    @ExceptionHandler({NullPointerException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex,
                                                 final WebRequest request)
    {
        return handleExceptionInternal(ex,
            "An internal error happened during the request! Please try again or contact an administrator.",
            new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({UnknownAccountException.class})
    public ResponseEntity<Object> handleUnknownAccountException(final UnknownAccountException ex,
                                                                final WebRequest request)
    {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), NOT_FOUND, request);
    }


}
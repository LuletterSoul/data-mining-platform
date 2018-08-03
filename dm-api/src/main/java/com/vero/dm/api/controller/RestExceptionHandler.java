package com.vero.dm.api.controller;


import static org.springframework.http.HttpStatus.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import com.vero.dm.exception.file.*;
import com.vero.dm.exception.group.InvalidGroupingConfigException;
import com.vero.dm.exception.group.PreviewGroupsNotFoundException;
import com.vero.dm.exception.group.StudentNotFoundException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.hibernate.LazyInitializationException;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vero.dm.exception.auth.*;
import com.vero.dm.exception.business.StudentIdDuplicatedException;
import com.vero.dm.exception.constract.HeaderLostException;
import com.vero.dm.exception.error.ErrorInfo;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.group.SpecificStudentNotFoundException;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:14 2017/7/30.
 * @since data-mining-platform
 */

@RestControllerAdvice
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

    /**
     * 处理业务异常
     *
     * @param ex
     *            异常
     * @param exceptionCode
     *            业务码
     * @param headers
     *            响应体
     * @param status
     *            http响应码
     * @param request
     *            本次请求
     * @return 业务异常信息
     */
    protected ResponseEntity<Object> handBusinessExceptionInternal(Exception ex,
                                                                   ExceptionCode exceptionCode,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   HttpStatus responseStatus,
                                                                   final WebRequest request)
    {
        return new ResponseEntity<>(
            new ErrorInfo(ex, exceptionCode.getCode(), exceptionCode.getTip(), responseStatus),
            headers, status);
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

    @ExceptionHandler(NoResultException.class)
    protected ResponseEntity<Object> handleResultNotFound(final NoResultException ex,
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

    // @ExceptionHandler({UnknownAccountException.class})
    // public ResponseEntity<Object> handleUnknownAccountException(final UnknownAccountException
    // ex,
    // final WebRequest request)
    // {
    // return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), NOT_FOUND, request);
    // }

    @ExceptionHandler(value = {UnsupportedTokenException.class})
    public ResponseEntity<Object> handleUnsupportedTokenException(final UnsupportedTokenException ex,
                                                                  final WebRequest request)
    {
        return handleExceptionInternal(ex,
            "Server can't resolve corresponding negotiation content with client.Please make sure request has include correct params.",
            new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * @param ex
     *            证书过期
     * @param request
     * @return
     */
    @ExceptionHandler(value = {ExpiredCredentialsException.class})
    public ResponseEntity<Object> handleHeaderLostException(final ExpiredCredentialsException ex,
                                                            final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN, request);
    }

    /**
     * @param ex
     *            授权失败,账号或密码错误
     * @param request
     * @return
     */
    @ExceptionHandler(value = {InternalAuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(final InternalAuthenticationException ex,
                                                                final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * @param ex
     *            未知账号
     * @param request
     * @return
     */
    @ExceptionHandler(value = {UnknownAccountException.class})
    public ResponseEntity<Object> handleUnknownAccountException(final UnknownAccountException ex,
                                                                final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN, request);
    }

    /**
     * @param ex
     *            证书不存在
     * @param request
     * @return
     */
    @ExceptionHandler(value = {AccessTokenNotExistException.class})
    public ResponseEntity<Object> handleAccessTokenNotExistException(final AccessTokenNotExistException ex,
                                                                     final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN, request);
    }

    /**
     * @param ex
     *            凭证不正确(密码不正确)
     * @param request
     * @return
     */
    @ExceptionHandler(value = {IncorrectCredentialsException.class})
    public ResponseEntity<Object> handleCredentialsInCorrectException(final IncorrectCredentialsException ex,
                                                                      final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * @param ex
     *            请求头缺失
     * @param request
     * @return
     */
    @ExceptionHandler(value = {HeaderLostException.class})
    public ResponseEntity<Object> handleHeaderLostException(final HeaderLostException ex,
                                                            final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ExcelModuleInValidException.class})
    public ResponseEntity<Object> handleExcelModuleInValidException(final ExcelModuleInValidException ex,
                                                                    final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ExcelModuleAnnotationNotFoundException.class})
    public ResponseEntity<Object> handleExcelModuleAnnotationNotFound(final ExcelModuleAnnotationNotFoundException ex,
                                                                      final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.ACCEPTED, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({UnsupportedFileTypeException.class})
    public ResponseEntity<Object> handleUnsupportedFileType(final UnsupportedFileTypeException ex,
                                                            final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({StudentIdDuplicatedException.class})
    public ResponseEntity<Object> handleStudentIdDuplicatedException(final StudentIdDuplicatedException ex,
                                                                     final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.CONFLICT, HttpStatus.CONFLICT, request);
    }


    @ExceptionHandler({StudentNotFoundException.class,SpecificStudentNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(final StudentNotFoundException ex,
                                                                       final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
                HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler({PreviewGroupsNotFoundException.class})
    public ResponseEntity<Object> handlePreviewGroupsNotFoundException(final PreviewGroupsNotFoundException ex,
                                                        final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
                HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({SetZipException.class})
    public ResponseEntity<Object> handleSetZipException(final SetZipException ex,
                                                        final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
            HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({InvalidGroupingConfigException.class})
    public ResponseEntity<Object> handleInvalidGroupingConfigException(final InvalidGroupingConfigException ex,
                                                        final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ex.getErrorCode(), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({LazyInitializationException.class})
    public ResponseEntity<Object> handleLazyInitializationException(final LazyInitializationException ex,
                                                                       final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ExceptionCode.LazyInitializationError, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({DuplicatedResultHandleException.class})
    public ResponseEntity<Object> handleDuplicatedResultHandleException(final DuplicatedResultHandleException ex,
                                                                    final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ExceptionCode.LazyInitializationError, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({RegisterInValidException.class})
    public ResponseEntity<Object> handleRegisterInValidException(final RegisterInValidException ex,
                                                                        final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ExceptionCode.RegisterInvalidException, new HttpHeaders(),
                HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DuplicatedUsernameException.class})
    public ResponseEntity<Object> handleDuplicatedUsernameException(final DuplicatedUsernameException ex,
                                                                 final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ExceptionCode.DuplicatedUsername, new HttpHeaders(),
                HttpStatus.CONFLICT, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({AccountAcceptedException.class})
    public ResponseEntity<Object> handleAccountAcceptedException(final AccountAcceptedException ex,
                                                                    final WebRequest request)
    {
        return handBusinessExceptionInternal(ex, ExceptionCode.AccountAccepted, new HttpHeaders(),
                HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN, request);
    }




}
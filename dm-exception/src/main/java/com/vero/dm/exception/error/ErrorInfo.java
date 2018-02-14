package com.vero.dm.exception.error;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.vero.dm.exception.util.ExceptionUtil;


/**
 * 封装服务器产生的业务错误 为客户端提供友好的错误提示信息
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:28 2017/7/30.
 * @since data-mining-platform
 */

public class ErrorInfo
{
    /**
     * 原生异常错误信息
     */
    private String error;

    /**
     * 提示信息
     */
    private String tip;

    /**
     * Http状态码
     */
    private int status;

    /**
     * 业务代码
     */
    private Integer errorCode;

    /**
     * 错误产生日期
     */
    private String date;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public ErrorInfo(Throwable throwable, String tip, HttpStatus status)
    {
        this.error = ExceptionUtil.getRootMessage(throwable);
        this.tip = tip;
        this.date = dateFormat.format(new Date());
        this.status = status.value();
    }

    public ErrorInfo(Throwable throwable, Integer errorCode, String tip, HttpStatus status)
    {
        this.error = ExceptionUtil.getRootMessage(throwable);
        this.tip = tip;
        this.status = status.value();
        this.errorCode = errorCode;
        this.date = dateFormat.format(new Date());
    }

    @Override
    public String toString()
    {
        return "ErrorInfo{" + "error='" + error + '\'' + ", tip='" + tip + '\'' + ", status="
               + status + ", errorCode=" + errorCode + ", date='" + date + '\'' + '}';
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getTip()
    {
        return tip;
    }

    public void setTip(String tip)
    {
        this.tip = tip;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Integer getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode)
    {
        this.errorCode = errorCode;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public static DateFormat getDateFormat()
    {
        return dateFormat;
    }
}

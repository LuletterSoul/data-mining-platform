package com.dm.org.exception;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  0:06 2017/7/7.
 * @description
 * @modified by:
 */
public class DataSetAttributeNoFoundException extends DataObjectNotFoundException
{
    public DataSetAttributeNoFoundException()
    {

    }

    public DataSetAttributeNoFoundException(String message) {
        super(message);
    }

    public DataSetAttributeNoFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

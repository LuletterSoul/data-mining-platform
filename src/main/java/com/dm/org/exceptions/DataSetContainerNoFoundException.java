package com.dm.org.exceptions;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  0:03 2017/7/7.
 * @description
 * @modified by:
 */
public class DataSetContainerNoFoundException extends DataObjectNotFoundException
{
    public DataSetContainerNoFoundException()
    {

    }

    public DataSetContainerNoFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DataSetContainerNoFoundException(String message) {
        super(message);
    }
}

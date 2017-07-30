package com.dm.org.exceptions;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:45 2017/7/6.
 * @description
 * @modified by:
 */
public class DataSetCollectionNoFoundException extends DataObjectNotFoundException
{
    public DataSetCollectionNoFoundException()
    {

    }

    public DataSetCollectionNoFoundException(String message)
    {
        super(message);
    }

    public DataSetCollectionNoFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

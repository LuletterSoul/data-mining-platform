package com.vero.dm.exception;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  1:15 2017/7/7.
 * @description
 * @modified by:
 */
public class DataAccessObjectException extends Exception
{

    public DataAccessObjectException()
    {

    }

    public DataAccessObjectException(String message)
    {
        super(message);
    }

    public DataAccessObjectException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DataAccessObjectException(Throwable cause) {
        super(cause);
    }
}

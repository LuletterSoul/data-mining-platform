package com.vero.dm.exception;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  16:08 2017/7/7.
 * @description
 * @modified by:
 */
public class DataObjectNotFoundException extends DataAccessObjectException
{
    public DataObjectNotFoundException()
    {
    }

    public DataObjectNotFoundException(String message) {
        super(message);
    }

    public DataObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataObjectNotFoundException(Throwable cause)
    {
        super(cause);
    }
}

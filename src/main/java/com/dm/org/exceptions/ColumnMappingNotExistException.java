package com.dm.org.exceptions;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  16:27 2017/7/7.
 * @description
 * @modified by:
 */
public class ColumnMappingNotExistException extends DataAccessObjectException
{
    public ColumnMappingNotExistException()
    {
    }

    public ColumnMappingNotExistException(String message) {
        super(message);
    }

    public ColumnMappingNotExistException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

package com.vero.dm.exception;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:22 2017/7/8.
 * @description
 * @modified by:
 */
public class ConstrainMappingNotNullException extends DataAccessObjectException {
    public ConstrainMappingNotNullException() {
    }

    public ConstrainMappingNotNullException(String message) {
        super(message);
    }

    public ConstrainMappingNotNullException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

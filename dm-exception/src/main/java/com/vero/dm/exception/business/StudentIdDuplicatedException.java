package com.vero.dm.exception.business;


import com.vero.dm.exception.common.DataDuplicatedException;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:21 2018/2/16.
 * @since data-mining-platform
 */

public class StudentIdDuplicatedException extends DataDuplicatedException
{
    private static final long serialVersionUID = -1206496777994801196L;

    public StudentIdDuplicatedException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

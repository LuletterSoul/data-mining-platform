package com.vero.dm.exception.file;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 9:48 2018/7/28.
 * @since data-mining-platform
 */

public class DuplicatedResultHandleException extends FileOperationException
{
    public DuplicatedResultHandleException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

package com.vero.dm.exception.file;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:56 2018/2/17.
 * @since data-mining-platform
 */

public class DataSetDeleteException extends FileOperationException
{
    public DataSetDeleteException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

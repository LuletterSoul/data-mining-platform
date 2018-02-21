package com.vero.dm.exception.file;


import com.vero.dm.exception.PlatformBaseException;
import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:19 2018/2/15.
 * @since data-mining-platform
 */

public class FileOperationException extends PlatformBaseException
{
    private static final long serialVersionUID = -1255284055245530248L;

    public FileOperationException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

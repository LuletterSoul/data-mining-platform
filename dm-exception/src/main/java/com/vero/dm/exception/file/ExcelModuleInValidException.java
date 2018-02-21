package com.vero.dm.exception.file;


import com.vero.dm.exception.error.ExceptionCode;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:20 2018/2/15.
 * @since data-mining-platform
 */

public class ExcelModuleInValidException extends FileOperationException
{
    private static final long serialVersionUID = 6864355322713727151L;

    public ExcelModuleInValidException(String message, ExceptionCode errorCode)
    {
        super(message, errorCode);
    }
}

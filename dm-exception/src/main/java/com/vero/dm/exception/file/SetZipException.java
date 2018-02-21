package com.vero.dm.exception.file;

import com.vero.dm.exception.error.ExceptionCode;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  0:46 2018/2/19.
 * @since data-mining-platform
 */

public class SetZipException extends FileOperationException {
    public SetZipException(String message, ExceptionCode errorCode) {
        super(message, errorCode);
    }
}

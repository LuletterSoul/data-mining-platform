package com.vero.dm.exception.file;

import com.vero.dm.exception.error.ExceptionCode;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  17:30 2018/2/15.
 * @since data-mining-platform
 */

public class ExcelModuleAnnotationNotFound extends FileOperationException {
    public ExcelModuleAnnotationNotFound(String message, ExceptionCode errorCode) {
        super(message, errorCode);
    }
}

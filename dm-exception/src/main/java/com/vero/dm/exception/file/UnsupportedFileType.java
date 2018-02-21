package com.vero.dm.exception.file;

import com.vero.dm.exception.error.ExceptionCode;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:03 2018/2/16.
 * @since data-mining-platform
 */

public class UnsupportedFileType extends FileOperationException {

    public UnsupportedFileType(String message, ExceptionCode errorCode) {
        super(message, errorCode);
    }
}

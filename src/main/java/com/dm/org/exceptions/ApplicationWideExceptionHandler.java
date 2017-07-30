package com.dm.org.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:59 2017/7/6.
 * @description
 * @modified by:
 */
@ControllerAdvice
public class ApplicationWideExceptionHandler
{
    @ExceptionHandler(DataSetCollectionNoFoundException.class)
    public String handleDataSetCollectionNotFoundException()
    {
        return null;
    }

    @ExceptionHandler(DataSetContainerNoFoundException.class)
    public String handleDataSetContainerNoFoundException()
    {
        return null;
    }

    @ExceptionHandler(DataSetAttributeNoFoundException.class)
    public String handleDataSetAttributeNoFoundException()
    {
        return null;
    }


}

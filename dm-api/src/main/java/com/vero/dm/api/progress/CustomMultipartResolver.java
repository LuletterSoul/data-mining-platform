package com.vero.dm.api.progress;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:45 2018/2/2.
 * @since data-mining-platform
 */

@Slf4j
public class CustomMultipartResolver extends CommonsMultipartResolver
{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request)
        throws MultipartException
    {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        loadUploadProgressListener(request, fileUpload);
        try
        {
            List<FileItem> fileItems = ((ServletFileUpload)fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        }
        catch (FileUploadBase.SizeLimitExceededException ex)
        {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        }
        catch (FileUploadBase.FileSizeLimitExceededException ex)
        {
            throw new MaxUploadSizeExceededException(fileUpload.getFileSizeMax(), ex);
        }
        catch (FileUploadException ex)
        {
            throw new MultipartException("Failed to parse multipart servlet request", ex);
        }
    }

    private void loadUploadProgressListener(HttpServletRequest request, FileUpload fileUpload)
    {
        // 客户端的传入的查询参数,由此参数获取对应的进度记录
        String progressQueryKey = validateQueryKey(request);
        // 无查询参数，即无获取上传参数的需求,不装载监听器
        if (progressQueryKey == null)
        {
            return;
        }
        fileUpload.setProgressListener(
            new UploadProgressListener(progressQueryKey, redisTemplate));// 将文件上传进度监听器加入到
        // fileUpload 中
    }

    private String validateQueryKey(HttpServletRequest request)
    {
        String progressQueryKey = request.getParameter(UploadProgressListener.PROC_QUERY_KEY);
        if (progressQueryKey == null)
        {
            logger.error(
                "Server could not provide asynchronous progress callback.Because progress query key is Null.");
        }
        return progressQueryKey;
    }
}

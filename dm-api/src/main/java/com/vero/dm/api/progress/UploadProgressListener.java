package com.vero.dm.api.progress;


import org.apache.commons.fileupload.ProgressListener;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 19:46 2018/2/2.
 * @since data-mining-platform
 */
@Slf4j
@Getter
public class UploadProgressListener implements ProgressListener
{
    public static final String PROC_ENTITY_HASH_KEY = "progress_hash_key";

    public static final String PROC_QUERY_KEY = "proc_query_key";

    private String progressQueryKey;

    private RedisTemplate<String, Object> redisCache;

    private ProgressEntity progressEntity;

    public UploadProgressListener(String progressQueryKey,
                                  RedisTemplate<String, Object> redisCache)
    {
        this.progressQueryKey = progressQueryKey;
        this.redisCache = redisCache;
        initProgressRecord(progressQueryKey, redisCache);
    }

    private void initProgressRecord(String progressQueryKey,
                                    RedisTemplate<String, Object> redisCache)
    {
        // 确保每一个文件上传请求有一个进度记录的实体
        progressEntity = new ProgressEntity(0, 1, 0);
        redisCache.opsForHash().put(PROC_ENTITY_HASH_KEY, progressQueryKey, progressEntity);
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems)
    {
        log.debug("Request [{}] upload progress------------------->[{}]%", progressQueryKey,
            (double)pBytesRead / pContentLength * 100);
        redisCache.opsForHash().put(PROC_ENTITY_HASH_KEY, progressQueryKey,
            new ProgressEntity(pBytesRead, pContentLength, pItems));
    }
}

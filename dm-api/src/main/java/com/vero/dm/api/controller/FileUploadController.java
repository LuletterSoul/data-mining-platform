package com.vero.dm.api.controller;


import static com.vero.dm.api.progress.UploadProgressListener.PROC_ENTITY_HASH_KEY;
import static com.vero.dm.api.progress.UploadProgressListener.PROC_QUERY_KEY;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.api.progress.ProgressEntity;
import com.vero.dm.service.constant.ResourcePath;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:15 2018/2/2.
 * @since data-mining-platform
 */

@Slf4j
@RestController
@RequestMapping(value = ResourcePath.FILE_PATH)
public class FileUploadController
{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping(value = "/progress/" + "{" + PROC_QUERY_KEY + "}")
    public ResponseEntity<ProgressEntity> getProgressRecord(@PathVariable(PROC_QUERY_KEY) String progressQueryKey)
    {
        ProgressEntity entity = (ProgressEntity)redisTemplate.opsForHash().get(
            PROC_ENTITY_HASH_KEY, progressQueryKey);
        if (entity == null)
        {
            log.error("Bad progress query key.Could not find corresponding value from redis.");
            throw new EntityNotFoundException("找不到对应的进度记录");
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}

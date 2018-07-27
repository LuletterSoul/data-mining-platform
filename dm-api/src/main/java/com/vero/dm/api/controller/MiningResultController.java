package com.vero.dm.api.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.model.MiningResult;
import com.vero.dm.model.ResultRecord;
import com.vero.dm.service.MiningResultService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiOperation;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:29 2018/7/24.
 * @since data-mining-platform
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.RESULT_PATH)
public class MiningResultController
{

    private MiningResultService resultService;

    @Autowired
    public void setResultService(MiningResultService resultService)
    {
        this.resultService = resultService;
    }

    @ApiOperation("创建数据挖掘结果")
    @PostMapping
    public ResponseEntity<MiningResult> createResult(@RequestBody MiningResult result)
    {
        return new ResponseEntity<>(resultService.saveResult(result), HttpStatus.CREATED);
    }


    @ApiOperation("上传数据挖掘结果")
    @PostMapping(value = "/{resultId}")
    public ResponseEntity<ResultRecord> uploadResult(@RequestPart MultipartFile resultFile,
                                                     @PathVariable("resultId") Integer resultId)
    {
        return new ResponseEntity<>(resultService.uploadResult(resultId, resultFile),
            HttpStatus.CREATED);
    }

    @ApiOperation("下载数据挖掘结果")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void downloadResult(@RequestBody List<Integer> recordIds, HttpServletResponse response)
    {
        resultService.downloadResults(recordIds, response);
    }

}

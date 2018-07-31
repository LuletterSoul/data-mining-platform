package com.vero.dm.api.controller;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.vero.dm.model.enums.ResultState;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @ApiOperation("获取数据挖掘结果")
    @GetMapping
    public ResponseEntity<Page<MiningResult>> getResults(@PageableDefault(size = 15, sort = {
            "resultId"}, direction = Sort.Direction.DESC) Pageable pageable,
                                            @ApiParam(value = "任务Id")  @RequestParam(value = "taskId",required = false) String taskId,
                                            @ApiParam(value = "阶段标识") @RequestParam(value = "stageId",required = false) Integer stageId,
                                            @ApiParam(value = "记录状态") @RequestParam(value = "state", required = false, defaultValue = "")ResultState state,
                                            @ApiParam(value = "抓取全部") @RequestParam(value = "all", required = false, defaultValue = "false") boolean all,
                                            @ApiParam(value = "提交者的用户ID") @RequestParam(value = "submitterId", required = false) String submitterId)
    {
        return new ResponseEntity<>(resultService.findResults(taskId, stageId, pageable, submitterId, state, all), HttpStatus.OK);
    }


    @ApiOperation("上传数据挖掘结果")
    @PostMapping(value = "/{resultId}/records",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultRecord> uploadResult(@RequestPart MultipartFile resultFile,
                                                     @PathVariable("resultId") Integer resultId)
    {
        return new ResponseEntity<>(resultService.uploadResult(resultId, resultFile),
            HttpStatus.CREATED);
    }

    @ApiOperation("下载数据挖掘结果")
    @PostMapping(value = "/records",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void downloadResult(@RequestBody List<Integer> recordIds, HttpServletResponse response)
    {
        resultService.downloadResults(recordIds, response);
    }

}

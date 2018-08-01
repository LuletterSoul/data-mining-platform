package com.vero.dm.api.controller;


import com.vero.dm.repository.dto.MiningResultDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.MiningResult;
import com.vero.dm.model.MiningTaskStage;
import com.vero.dm.model.enums.ResultState;
import com.vero.dm.service.MiningResultService;
import com.vero.dm.service.MiningTaskStageService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:34 2018/7/24.
 * @since data-mining-platform
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION
                        + ResourcePath.STAGE_PATH)
public class StageController
{

    private MiningResultService resultService;

    private MiningTaskStageService stageService;

    @Autowired
    public void setResultService(MiningResultService resultService)
    {
        this.resultService = resultService;
    }

    @Autowired
    public void setStageService(MiningTaskStageService stageService)
    {
        this.stageService = stageService;
    }

    @ApiOperation("创建一个任务阶段")
    @PostMapping
    public ResponseEntity<MiningTaskStage> createStage(@RequestBody MiningTaskStage stage)
    {
        return new ResponseEntity<>(stageService.saveStage(stage), HttpStatus.CREATED);
    }

    @ApiOperation("获取一个任务阶段的数据挖掘结果")
    @GetMapping(value = "/{stageId}/results")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "按某属性排序", dataType = "Integer", paramType = "query", defaultValue = "resultId"),
            @ApiImplicitParam(name = "direction", value = "排序方式", dataType = "String", paramType = "query", defaultValue = "DESC"),})
    public ResponseEntity<Page<MiningResultDto>> getResults(@ApiParam(value = "阶段标识") @PathVariable("stageId") Integer stageId,
                                                            @ApiParam(value = "任务标识") @RequestParam(value = "taskId",required = false,defaultValue = "") String taskId,
                                                            @ApiParam(value = "提交者") @RequestParam(value = "submitterIds", required = false,defaultValue = "") List<String> submitterIds,
                                                            @ApiParam(value = "结果当前的状态") @RequestParam(value = "state", required = false,defaultValue = "") ResultState state,
                                                            @ApiParam(value = "抓取全部") @RequestParam(value = "all", required = false, defaultValue = "false") boolean all,
                                                            @PageableDefault(size = 8, sort = {
                                                             "resultId"}, direction = Sort.Direction.DESC) Pageable pageable)
    {
        return new ResponseEntity<>(
            resultService.findResults(taskId, stageId, pageable, submitterIds, state, all), HttpStatus.OK);
    }


}

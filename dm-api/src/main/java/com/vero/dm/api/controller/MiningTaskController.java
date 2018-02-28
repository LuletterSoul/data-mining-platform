package com.vero.dm.api.controller;


import java.util.Date;
import java.util.List;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.MiningTaskStatus;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.service.MiningTaskService;
import com.vero.dm.service.constant.ResourcePath;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.TASK_PATH)
public class MiningTaskController
{
    private MiningTaskService miningTaskService;

    @Autowired
    public void setMiningTaskService(MiningTaskService miningTaskService)
    {
        this.miningTaskService = miningTaskService;
    }

    @ApiOperation("根据ID获取数据挖掘任务的信息")
    @GetMapping(value = "/{taskId}")
    public ResponseEntity<DataMiningTask> get(@PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<>(miningTaskService.findById(taskId), HttpStatus.OK);
    }

    @ApiOperation("根据ID删除一个数据挖掘任务")
    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<DataMiningTask> deleteById(@PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<>(miningTaskService.deleteByTaskId(taskId),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation("分页获取任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sort", value = "按某属性排序", dataType = "String", paramType = "query", defaultValue = "taskId"),
            @ApiImplicitParam(name = "direction", value = "排序方式", dataType = "String", paramType = "query", defaultValue = "DESC"),})
    @GetMapping
    public Page<DataMiningTask> getList(@PageableDefault(size = 15, sort = {
            "builtTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                        @ApiParam(value = "任务名称") @RequestParam(value = "taskName",required = false,defaultValue = "") String taskName,
                                        @ApiParam(value = "计划开始时间")
                                            @RequestParam(value = "plannedBeginDate",required = false,defaultValue = "")
                                             Date plannedBeginDate,
                                        @ApiParam(value = "计划结束时间")
                                            @RequestParam(value = "plannedEndDate",required = false,defaultValue = "")
                                            Date plannedEndDate,
                                        @ApiParam(value = "建立时间区间起点")
                                            @RequestParam(value = "builtTimeBegin",required = false,defaultValue = "")
                                            Date builtTimeBegin,
                                        @ApiParam(value = "建立时间区间结点")
                                            @RequestParam(value = "builtTimeEnd",required = false,defaultValue = "")
                                            Date builtTimeEnd,
                                        @ApiParam(value = "任务状态")@RequestParam(value = "taskStatus") MiningTaskStatus taskStatus) {
        return miningTaskService.fetchTaskList(taskName,plannedBeginDate,plannedEndDate,builtTimeBegin,builtTimeEnd , taskStatus, pageable);
    }

    @ApiOperation("更新数据挖掘任务的信息")
    @PutMapping
    public ResponseEntity<DataMiningTask> update(@RequestBody MiningTaskDto miningTaskDto)
    {
        return new ResponseEntity<>(miningTaskService.saveOrUpdateMiningTask(miningTaskDto),
            HttpStatus.OK);
    }

    @ApiOperation("创建一个数据挖掘任务")
    @PostMapping
    public ResponseEntity<DataMiningTask> create(@RequestBody MiningTaskDto miningTaskDto)
    {
        return new ResponseEntity<>(miningTaskService.saveOrUpdateMiningTask(miningTaskDto),
            HttpStatus.CREATED);
    }

    @ApiOperation("获取执行此任务分组")
    @GetMapping(value = "/{taskId}/groups")
    public ResponseEntity<List<DataMiningGroup>> groups(@PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<>(miningTaskService.fetchInvolvedGroups(taskId), HttpStatus.OK);
    }

    @ApiOperation("获取该任务分配的数据集")
    @GetMapping(value = "/{taskId}/arranged_collections")
    public ResponseEntity<List<DataSetCollection>> collection(@PathVariable("taskId") String taskId) {
        return null;
    }

//    @ApiOperation("为分组分配一个数据挖掘任务")
////    @PostMapping(value = "/{taskId}/groups")
////    public ResponseEntity<DataMiningGroup> involveGroup(@PathVariable("taskId") String taskId,
////                                                        @RequestBody String groupId)
////    {
////        return new ResponseEntity<>(miningTaskService.involveGroup(taskId, groupId),
////            HttpStatus.CREATED);
////    }

    @ApiOperation("为多个分组批量分配任务")
    @RequestMapping(value = "/{taskId}/groups", method = RequestMethod.POST)
    public ResponseEntity<List<DataMiningGroup>> involveGroups(@PathVariable("taskId") String taskId,
                                                               @RequestBody List<String> groupIds)
    {
        return new ResponseEntity<>(miningTaskService.involveGroups(taskId, groupIds),
            HttpStatus.CREATED);
    }

    @ApiOperation("批量移除任务")
    @RequestMapping(value = "/{taskId}/groups", method = RequestMethod.DELETE)
    public ResponseEntity<List<DataMiningGroup>> removeGroups(@PathVariable("taskId") String taskId,
                                                              @RequestBody List<String> groupIds)
    {
        return new ResponseEntity<>(miningTaskService.removeInvolvedGroups(taskId, groupIds),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation("配置算法")
    @PostMapping(value = "/{taskId}/algorithms")
    public ResponseEntity<List<Algorithm>> configAlgorithms(@PathVariable("taskId") String taskId,
                                                            @RequestBody List<Integer> algorithmIds)
    {
        return new ResponseEntity<>(miningTaskService.configureAlgorithms(taskId, algorithmIds),
            HttpStatus.CREATED);
    }

    @ApiOperation("分配数据集")
    @PostMapping(value = "/{taskId}/collections")
    public ResponseEntity<List<DataSetCollection>> arrangeMiningSets(@PathVariable("taskId") String taskId,
                                                                     @RequestBody List<String> collectionIds)
    {
        return new ResponseEntity<>(miningTaskService.configureMiningSets(taskId, collectionIds),
            HttpStatus.CREATED);
    }

    @ApiOperation("获取与此任务相关联的分组")
    @GetMapping(value = "/{taskId}/collections")
    public ResponseEntity<List<DataSetCollection>> getInvolvedSets(@PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<>(miningTaskService.fetchRefCollections(taskId), HttpStatus.OK);
    }

    @ApiOperation("取消所有数据集与该任务的关联")
    @DeleteMapping(value = "/{taskId}/collections")
    public ResponseEntity<List<DataSetCollection>> deleteAllRelContainers(@PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<>(miningTaskService.removeAllMiningSets(taskId),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation("获取该任务配置的算法")
    @GetMapping(value = "/{taskId}/algorithms")
    public ResponseEntity<List<Algorithm>> fetchConfiguredAlgorithms(@PathVariable("taskId") String taskId)
    {
        return new ResponseEntity<>(miningTaskService.fetchConfiguredAlgorithms(taskId), HttpStatus.OK);
    }

}

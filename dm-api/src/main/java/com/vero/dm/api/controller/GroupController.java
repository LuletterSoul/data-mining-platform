package com.vero.dm.api.controller;


import java.util.List;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.service.GroupService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.GROUP_PATH)
public class GroupController
{
    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @ApiOperation("分页查询分组列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10"),
        @ApiImplicitParam(name = "sort", value = "按某属性排序", dataType = "String", paramType = "query", defaultValue = "groupId"),
        @ApiImplicitParam(name = "direction", value = "排序方式", dataType = "String", paramType = "query", defaultValue = "DESC")})
    @GetMapping
    public ResponseEntity<Page<DataMiningGroup>> getPageable(@PageableDefault(size = 15, sort = {
        "groupId"}, direction = Sort.Direction.DESC) Pageable pageable)
    {
        return new ResponseEntity<>(groupService.fetchPageableGroups(pageable), HttpStatus.OK);
    }

    @ApiOperation("获取系统帮助用户分组的信息")
    @PostMapping(value = "/dividing_groups")
    public ResponseEntity<DividingGroupInfo> previewDefaultGroupings(@RequestBody GroupingConfigParams params)
    {
        return new ResponseEntity<>(groupService.initDefaultGroupingStrategy(params),
            HttpStatus.OK);
    }

    @ApiOperation("创建系统先前分组,由用户确认")
    @PostMapping(value = "/dividing_groups/{queryKey}")
    public ResponseEntity<List<DataMiningGroup>> createSystemDefaultGroups(@ApiParam(value = "系统响应的查询Key")@PathVariable("queryKey") String queryKey)
    {
        return new ResponseEntity<>(groupService.sureDividingGroupRequest(queryKey), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{groupId}")
    public DataMiningGroup get(@PathVariable("groupId") String groupId)
    {
        return groupService.fetchGroupDetails(groupId);
    }

    @ApiOperation("创建一个分组")
    @PostMapping
    public ResponseEntity<DataMiningGroup> create(@RequestBody DataMiningGroupDto group)
    {
        return new ResponseEntity<>(groupService.createGroup(group), HttpStatus.CREATED);
    }

    @ApiOperation("更新分组信息")
    @PutMapping
    public DataMiningGroup update(@RequestBody DataMiningGroup miningGroup)
    {
        groupService.update(miningGroup);
        return miningGroup;
    }

    @ApiOperation("删除一个分组")
    @DeleteMapping(value = "/{groupId}")
    public ResponseEntity<DataMiningGroup> delete(@PathVariable("groupId") String groupId)
    {
        return new ResponseEntity<>(groupService.deleteMiningGroupById(groupId),
            HttpStatus.NO_CONTENT);
    }

    // @RequestMapping(value = "/{groupId}/leader", method = RequestMethod.PATCH)
    // public StudentDto setGroupLeader(@RequestBody String studentId,
    // @PathVariable("groupId") String groupId)
    // {
    // return groupService.setGroupLeader(studentId, groupId);
    // }

    @ApiOperation("更新团队的组长")
    @PutMapping(value = "/{groupId}/leader")
    public StudentDto updateLeader(@PathVariable("groupId") String groupId,
                                   @RequestBody String studentId)
    {
        return groupService.updateLeader(studentId, groupId);
    }

    @ApiOperation("获取分组成员")
    @GetMapping(value = "/{groupId}/members")
    public ResponseEntity<List<StudentDto>> getGroupMembers(@PathVariable("groupId") String groupId)
    {
        return new ResponseEntity<>(groupService.fetchGroupMembers(groupId), HttpStatus.OK);
    }

    @ApiOperation("更新分组成员(包括增加、删除)")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10"),
        @ApiImplicitParam(name = "studentIds", value = "组员标识列表,size为零表示撤销所有组成员", dataType = "List", paramType = "body", required = true, defaultValue = "groupId")})
    @PutMapping(value = "/{groupId}/members")
    public ResponseEntity<List<StudentDto>> configureMembers(@PathVariable("groupId") String groupId,
                                                             @RequestBody List<String> studentIds)
    {
        return new ResponseEntity<>(groupService.configureGroupMembers(groupId, studentIds),
            HttpStatus.CREATED);
    }
}

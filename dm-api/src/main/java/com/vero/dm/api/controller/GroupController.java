package com.vero.dm.api.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.Student;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.repository.dto.*;
import com.vero.dm.service.GroupService;
import com.vero.dm.service.StudentService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.GROUP_PATH)
public class GroupController
{
    private GroupService groupService;

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @Autowired
    public void setGroupService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @ApiOperation("获取空闲的学生列表")
    @GetMapping(value = "/leisure_students")
    @Cacheable(cacheNames = "studentPageableCache")
    public ResponseEntity<List<Student>> leisureStudents(@PageableDefault(size = 10, sort = {
        "studentId"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                         @ApiParam("行政班") @RequestParam(name = "className", required = false, defaultValue = "") String className,
                                                         @ApiParam("专业") @RequestParam(value = "profession", required = false, defaultValue = "") String profession,
                                                         @ApiParam("年级") @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
                                                         @ApiParam("学号前缀模糊查询") @RequestParam(value = "studentId", required = false, defaultValue = "") String studentIdPrefix,
                                                         @ApiParam("姓名") @RequestParam(value = "studentName", required = false, defaultValue = "") String studentName,
                                                         @ApiParam("开始日期") @RequestParam(value = "beginDate", required = false) Date beginDate,
                                                         @ApiParam("结束日期") @RequestParam(value = "endDate", required = false) Date endDate)
    {
        return new ResponseEntity<>(studentService.getAllLeisureStudents(pageable, className,
            profession, grade, studentIdPrefix, studentName, beginDate, endDate), HttpStatus.OK);
    }

    @ApiOperation("分页查询分组列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "query", defaultValue = "10"),
        @ApiImplicitParam(name = "sort", value = "按某属性排序", dataType = "String", paramType = "query", defaultValue = "groupId"),
        @ApiImplicitParam(name = "direction", value = "排序方式", dataType = "String", paramType = "query", defaultValue = "DESC")})
    @GetMapping
    public ResponseEntity<Page<DataMiningGroup>> getPageable(@PageableDefault(size = 20, sort = {
        "builtTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                             @ApiParam("队伍名称") @RequestParam(value = "groupName", required = false, defaultValue = "") String groupName,
                                                             @ApiParam("建立时间的区间起点") @RequestParam(value = "beginDate", required = false, defaultValue = "") Date beginDate,
                                                             @ApiParam("建立时间区间的终点") @RequestParam(value = "endDate", required = false, defaultValue = "") Date endDate,
                                                             @ApiParam("队长学号") @RequestParam(value = "leaderStudentId", required = false, defaultValue = "") String leaderStudentId,
                                                             @ApiParam("任务状态") @RequestParam(value = "taskStatus", required = false, defaultValue = "") MiningTaskStatus taskStatus,
                                                             @ApiParam("抓取全部") @RequestParam(value = "fetch", required = false, defaultValue = "false") Boolean fetch)
    {
        return new ResponseEntity<>(groupService.fetchPageableGroups(pageable, groupName,
            beginDate, endDate, leaderStudentId, taskStatus, fetch), HttpStatus.OK);
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
    public ResponseEntity<List<DataMiningGroup>> createSystemDefaultGroups(@ApiParam(value = "系统响应的查询Key") @PathVariable("queryKey") String queryKey)
    {
        return new ResponseEntity<>(groupService.sureDividingGroupRequest(queryKey),
            HttpStatus.CREATED);
    }

    @GetMapping(value = "/{groupId}")
    public DataMiningGroup get(@PathVariable("groupId") String groupId)
    {
        return groupService.fetchGroupDetails(groupId);
    }

    @ApiOperation("获取所有分组的名称")
    @GetMapping(value = "/group_names")
    public ResponseEntity<List<String>> groupNames()
    {
        return new ResponseEntity<>(groupService.fetchGroupNames(), HttpStatus.OK);
    }

    @ApiOperation("获取所有分组的队长信息")
    @GetMapping(value = "/group_leaders")
    public ResponseEntity<List<Student>> leaders()
    {
        return new ResponseEntity<>(groupService.fetchGroupLeaders(), HttpStatus.OK);
    }

    @ApiOperation("创建一个分组")
    @PostMapping
    public ResponseEntity<DataMiningGroup> create(@RequestBody DataMiningGroupDto group)
    {
        return new ResponseEntity<>(groupService.createGroup(group), HttpStatus.CREATED);
    }

    @ApiOperation("更新分组信息")
    @PutMapping
    public ResponseEntity<DataMiningGroup> update(@RequestBody GroupDto groupDto)
    {
        return new ResponseEntity<>(groupService.updateGroup(groupDto), HttpStatus.OK);
    }

    @ApiOperation("删除一个分组")
    @DeleteMapping(value = "/{groupId}")
    public ResponseEntity<DataMiningGroup> delete(@PathVariable("groupId") String groupId)
    {
        return new ResponseEntity<>(groupService.deleteMiningGroupById(groupId),
            HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除分组")
    @DeleteMapping
    public ResponseEntity<List<DataMiningGroup>> delete(@RequestBody List<String> groupIds)
    {
        return new ResponseEntity<>(groupService.deleteGroupBatch(groupIds), HttpStatus.OK);
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

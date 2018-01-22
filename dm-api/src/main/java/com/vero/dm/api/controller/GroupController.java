package com.vero.dm.api.controller;


import java.util.List;

import com.vero.dm.model.DataMiningTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.repository.dto.StudentDTO;
import com.vero.dm.repository.dto.TaskConfigParams;
import com.vero.dm.service.GroupService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@RestController
@RequestMapping(value = "/groups")
public class GroupController
{
    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService)
    {
        this.groupService = groupService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<DataMiningGroup> getPageable(@PageableDefault Pageable pageable)
    {
        return groupService.get(pageable);
    }

    @RequestMapping(value = "/grouping", method = RequestMethod.GET)
    public List<DataMiningGroup> previewDefaultGroupings(TaskConfigParams params)
    {
        return groupService.initDefaultGroupingStrategy(params);
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
    public DataMiningGroup get(@PathVariable("groupId") String groupId)
    {
        return groupService.fetchGroupDetails(groupId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DataMiningGroup> create(@RequestBody DataMiningGroup group)
    {
        groupService.save(group);
        return new ResponseEntity<DataMiningGroup>(group, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataMiningGroup update(@RequestBody DataMiningGroup miningGroup)
    {
        groupService.update(miningGroup);
        return miningGroup;
    }

    @RequestMapping(value = "/{groupId}", method = RequestMethod.DELETE)
    public ResponseEntity<DataMiningGroup> delete(@PathVariable("groupId") String groupId)
    {
        return new ResponseEntity<DataMiningGroup>(groupService.deleteMiningGroupById(groupId),
            HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{groupId}/leader", method = RequestMethod.PATCH)
    public StudentDTO setGroupLeader(@RequestBody String studentId,
                                     @PathVariable("groupId") String groupId)
    {
        return groupService.setGroupLeader(studentId, groupId);
    }

    @RequestMapping(value = "/{groupId}/leader", method = RequestMethod.DELETE)
    public StudentDTO rescindLeader(@PathVariable("groupId") String groupId,
                                    @RequestBody String studentId)
    {
        return groupService.rescindGroupLeader(studentId, groupId);
    }

    @RequestMapping(value = "/{groupId}/groupName", method = RequestMethod.PATCH)
    public String setOrUpdateGroupName(@PathVariable("groupId") String groupId,
                                       @RequestBody String groupName)
    {
        return null;
    }

    @RequestMapping(value = "/{groupId}/groupMembers", method = RequestMethod.GET)
    public List<StudentDTO> getGroupMembers(@PathVariable("groupId") String groupId)
    {
        return groupService.fetchGroupMembers(groupId);
    }

    @RequestMapping(value = "/{groupId}/groupMembers", method = RequestMethod.POST)
    public ResponseEntity<StudentDTO> addGroupMember(@PathVariable("groupId") String groupId,
                                                     @RequestBody String studentId)
    {
        return new ResponseEntity<StudentDTO>(groupService.addGroupMember(groupId, studentId),
            HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{groupId}/groupMembers/addMembersWithArray", method = RequestMethod.POST)
    public ResponseEntity<List<StudentDTO>> addGroupMembers(@PathVariable("groupId") String groupId,
                                                            @RequestBody List<String> studentIds)
    {
        return new ResponseEntity<List<StudentDTO>>(
            groupService.addGroupMembers(groupId, studentIds), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{groupId}/groupMembers/deleteWithArray", method = RequestMethod.DELETE)
    public ResponseEntity<List<StudentDTO>> removeGroupMembers(@PathVariable("groupId") String groupId,
                                                               @RequestBody List<String> studentIds)
    {
        return new ResponseEntity<List<StudentDTO>>(
            groupService.removeGroupMembers(groupId, studentIds), HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{groupId}/groupMembers", method = RequestMethod.DELETE)
    public ResponseEntity<List<StudentDTO>> removeAllGroupMembers(@PathVariable("groupId") String groupId)
    {
        return new ResponseEntity<List<StudentDTO>>(groupService.removeAllGroupMembers(groupId),
            HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{groupId}/groupMembers/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDTO> removeGroupMember(@PathVariable("groupId") String groupId,
                                                        @PathVariable("studentId") String studentId)
    {
        return new ResponseEntity<StudentDTO>(groupService.removeGroupMember(groupId, studentId),
            HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{groupId}/task", method = RequestMethod.GET)
    public DataMiningTask getInvolvedTask(@PathVariable("groupId") String groupId)
    {
        return null;
    }
}

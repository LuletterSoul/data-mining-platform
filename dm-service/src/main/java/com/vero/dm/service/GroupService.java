package com.vero.dm.service;


import java.util.Date;
import java.util.List;

import com.vero.dm.model.enums.StatusObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import com.vero.dm.repository.dto.StudentDto;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface GroupService extends BaseService<DataMiningGroup, String>
{
    DataMiningGroup createGroup(DataMiningGroupDto groupDto);

    DataMiningGroupDto updateGroup(DataMiningGroupDto groupDto);

    void setPreviewGroupCache(String queryKey, List<DataMiningGroup> groups);


    Page<DataMiningGroupDto> fetchPageableGroups(Pageable pageable, String groupName, Date beginDate,
                                                 Date endDate, String leaderStudentId,
                                                 MiningTaskStatus taskStatus, Boolean fetch);

    List<Student> fetchStudentWithoutGroup(Date begin, Date end);

    DividingGroupInfo getDividingGroupInfo(GroupingConfigParams params);

    List<DataMiningGroupDto> sureDividingGroupRequest(String queryKey);

    DataMiningGroup fetchGroupDetails(String groupId);

    List<DataMiningGroup> fetchGroupDetails(List<String> groupId);

    DataMiningGroup deleteMiningGroupById(String groupId);

    List<DataMiningGroup> deleteGroupBatch(List<String> groupIds);

    StudentDto updateLeader(String studentId, String groupId);

    List<StudentDto> fetchGroupMembers(String groupId);

    List<StudentDto> configureGroupMembers(String groupId, List<String> studentIds);

    List<String> fetchGroupNames();

    List<Student> fetchGroupLeaders();

    DataMiningTask arrangeTask(String groupId, String taskId);

    MiningTaskStatus updateGroupStatus(String groupId, MiningTaskStatus newStatus);

    List<StatusObject> fetchStatusOptions();
}

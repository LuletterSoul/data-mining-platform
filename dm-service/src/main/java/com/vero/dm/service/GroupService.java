package com.vero.dm.service;


import java.util.Date;
import java.util.List;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.repository.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface GroupService extends BaseService<DataMiningGroup, String>
{
    DataMiningGroup createGroup(DataMiningGroupDto groupDto);

    DataMiningGroup updateGroup(GroupDto groupDto);

    Page<DataMiningGroup> fetchPageableGroups(Pageable pageable,String groupName,
                                              Date beginDate,
                                              Date endDate,
                                              String leaderStudentId,
                                              MiningTaskStatus taskStatus);

    List<Student> fetchStudentWithoutGroup(Date begin, Date end);

    DividingGroupInfo initDefaultGroupingStrategy(GroupingConfigParams params);

    List<DataMiningGroup> sureDividingGroupRequest(String queryKey);

    DataMiningGroup fetchGroupDetails(String groupId);

    List<DataMiningGroup> fetchGroupDetails(List<String> groupId);

    DataMiningGroup deleteMiningGroupById(String groupId);

    List<DataMiningGroup> deleteGroupBatch(List<String> groupIds);

    StudentDto updateLeader(String studentId, String groupId);

    List<StudentDto> fetchGroupMembers(String groupId);

    List<StudentDto> configureGroupMembers(String groupId, List<String> studentIds);

    List<String> fetchGroupNames();

    List<Student> fetchGroupLeaders();

    DataMiningTask arrangeTask(String groupId,String taskId);

    List<MiningTaskStatus> fetchStatusOptions();
}

package com.vero.dm.service;


import java.util.List;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.repository.dto.StudentDTO;
import com.vero.dm.repository.dto.TaskConfigParams;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface GroupService extends BaseService<DataMiningGroup, String>
{

    List<DataMiningGroup> initDefaultGroupingStrategy(TaskConfigParams params);

    DataMiningGroup fetchGroupDetails(String groupId);

    DataMiningGroup deleteMiningGroupById(String groupId);

    StudentDTO setGroupLeader(String studentId, String groupId);

    StudentDTO rescindGroupLeader(String studentId, String groupId);

    List<StudentDTO> fetchGroupMembers(String groupId);

    StudentDTO addGroupMember(String groupId, String studentId);

    List<StudentDTO> addGroupMembers(String groupId, List<String> studentIds);

    StudentDTO removeGroupMember(String groupId, String studentId);

    List<StudentDTO> removeGroupMembers(String groupId, List<String> studentIds);

    List<StudentDTO> removeAllGroupMembers(String groupId);

    DataMiningTask getInvolvedTask(String taskId);

}

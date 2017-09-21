package com.dm.org.service;

import com.dm.org.dto.StudentDTO;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public interface GroupService extends BaseService<DataMiningGroup,String> {

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

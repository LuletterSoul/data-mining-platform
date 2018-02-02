package com.vero.dm.service;


import java.util.List;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.repository.dto.GroupingConfigParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface GroupService extends BaseService<DataMiningGroup, String>
{
    DataMiningGroup createGroup(DataMiningGroupDto groupDto);

    Page<DataMiningGroup> fetchPageableGroups(Pageable pageable);

    DividingGroupInfo initDefaultGroupingStrategy(GroupingConfigParams params);

    List<DataMiningGroup> sureDividingGroupRequest(String queryKey);

    DataMiningGroup fetchGroupDetails(String groupId);

    DataMiningGroup deleteMiningGroupById(String groupId);

    StudentDto updateLeader(String studentId, String groupId);

    List<StudentDto> fetchGroupMembers(String groupId);

    List<StudentDto> configureGroupMembers(String groupId, List<String> studentIds);
}

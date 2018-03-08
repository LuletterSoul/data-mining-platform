package com.vero.dm.service;


import java.util.Date;
import java.util.List;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.TaskProgressStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.repository.dto.MiningTaskDto;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface MiningTaskService extends BaseService<DataMiningTask, String>
{

    DataMiningTask saveOrUpdateMiningTask(MiningTaskDto miningTaskDto);

    DataMiningTask deleteByTaskId(String taskId);

    Page<DataMiningTask> fetchTaskList(boolean fetch, String taskName, Date plannedBeginDate, Date plannedEndDate, Date builtTimeBegin, Date builtTimeEnd, Pageable pageable);

    List<DataMiningGroup> fetchInvolvedGroups(String taskId);

    List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds);

    DataMiningGroup involveGroup(String taskId, String groupId);

    List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds);

    List<Algorithm> fetchConfiguredAlgorithms(String taskId);

    List<Algorithm> configureAlgorithms(String taskId, List<Integer> algorithmIds);

    List<DataSetCollection> configureMiningSets(String taskId, List<String> collectionIds);

    List<DataSetCollection> fetchRefCollections(String taskId);

    List<DataSetCollection> removeAllMiningSets(String taskId);

    List<TaskProgressStatus> fetchProgressStatus();
}

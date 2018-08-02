package com.vero.dm.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.StatusObject;
import com.vero.dm.model.enums.TaskProgressStatus;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.TaskStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.repository.dto.MiningTaskDto;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface MiningTaskService extends BaseService<DataMiningTask, String>
{

    List<DataMiningTask> findAllTasks();

    List<DataMiningTask> findByTaskIds(List<String> taskIds);

    List<String> findAllTaskNames();

    TaskStatistics findStatistics(String taskId);

    DataMiningTask saveOrUpdateMiningTask(MiningTaskDto miningTaskDto);

    DataMiningTask deleteByTaskId(String taskId);

    List<DataMiningTask> deleteBatchTask(List<String> taskIds);

    Page<MiningTaskDto> fetchTaskList(boolean fetch, String taskName, Date plannedBeginDate, Date plannedEndDate, Date builtTimeBegin, Date builtTimeEnd, String studentId, Pageable pageable, TaskProgressStatus progressStatus, Integer lowBound, Integer upperBound);

    Map<String, List<DataMiningGroupDto>> fetchInvolvedGroups(List<String> taskIds);

    List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds);

    List<MiningTaskStage> fetchRefStages(String taskId);

    DataMiningGroup involveGroup(String taskId, String groupId);

    List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds);

    List<Algorithm> fetchConfiguredAlgorithms(String taskId);

    List<Algorithm> configureAlgorithms(String taskId, List<Integer> algorithmIds);

    List<DataSetCollection> configureMiningSets(String taskId, List<String> collectionIds);

    List<DataSetCollection> fetchRefCollections(String taskId);

    List<DataSetCollection> removeAllMiningSets(String taskId);

    List<StatusObject> fetchProgressStatus();

    Integer[] minAndMaxGroupNum();
}

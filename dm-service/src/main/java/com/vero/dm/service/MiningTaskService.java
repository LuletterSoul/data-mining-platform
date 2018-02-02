package com.vero.dm.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.Algorithm;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.DataSetCollection;
import com.vero.dm.repository.dto.MiningTaskDto;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface MiningTaskService extends BaseService<DataMiningTask, String>
{

    DataMiningTask saveOrUpdateMiningTask(MiningTaskDto miningTaskDto);

    DataMiningTask deleteByTaskId(String taskId);

    Page<DataMiningTask> fetchTaskList(Pageable pageable);

    List<DataMiningGroup> fetchInvolvedGroups(String taskId);

    List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds);

    DataMiningGroup involveGroup(String taskId, String groupId);

    List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds);

    List<Algorithm> fetchConfiguredAlgorithms(String taskId);

    List<Algorithm> configureAlgorithms(String taskId, List<Integer> algorithmIds);

    List<DataSetCollection> configureMiningSets(String taskId, List<String> collectionIds);

    List<DataSetCollection> fetchRefCollections(String taskId);

    List<DataSetCollection> removeAllMiningSets(String taskId);
}

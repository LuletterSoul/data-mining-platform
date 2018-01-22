package com.vero.dm.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.Algorithm;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.DataSetCollection;
import com.vero.dm.repository.dto.MiningTaskDTO;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

public interface MiningTaskService extends BaseService<DataMiningTask, String>
{

    DataMiningTask saveMiningTask(MiningTaskDTO miningTaskDTO);

    DataMiningTask deleteByTaskId(String taskId);

    Page<DataMiningTask> fetchTaskList(Pageable pageable);

    List<DataMiningGroup> fetchInvolvedGroups(String taskId);

    DataMiningGroup getInvolvedGroup(String taskId, String groupId);

    DataMiningGroup removeInvolvedGroup(String taskId, String groupId);

    List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds);

    List<DataMiningGroup> getInvolvedGroups(String taskId);

    DataMiningGroup involveGroup(String taskId, String groupId);

    List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds);

    Algorithm getAlgorithm(String taskId, String algorithmId);

    List<Algorithm> fetchConfiguredAlgorithms(String taskId);

    Algorithm configureAlgorithm(String taskId, String algorithmId);

    List<Algorithm> configureAlgorithms(String taskId, List<String> algorithmIds);

    Algorithm removeAlgorithm(String taskId, String algorithmId);

    List<Algorithm> removeAlgorithms(String taskId, List<String> algorithmIds);

    List<Algorithm> removeAllAlgorithms(String taskId);

    DataSetCollection arrangeMiningSet(String taskId, String collectionId);

    List<DataSetCollection> arrangeMiningSets(String taskId, List<String> collectionIds);

    List<DataSetCollection> fetchRefCollections(String taskId);

    DataSetCollection removeMiningSet(String taskId, String collectionId);

    List<DataSetCollection> removeMiningSets(String taskId, List<String> collectionIds);

    List<DataSetCollection> removeAllMiningSets(String taskId);
}

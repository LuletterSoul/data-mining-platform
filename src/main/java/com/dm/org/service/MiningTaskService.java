package com.dm.org.service;

import com.dm.org.model.Algorithm;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import com.dm.org.model.DataSetContainer;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public interface MiningTaskService extends BaseService<DataMiningTask,String>{

    DataMiningTask deleteByTaskId(String taskId);

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

    DataSetContainer arrangeMiningSet(String taskId, String containerId);

    List<DataSetContainer> arrangeMiningSets(String taskId, List<String> containerIds);

    List<DataSetContainer> fetchRefContainers(String taskId);

    DataSetContainer removeMiningSet(String taskId, String containerId);

    List<DataSetContainer> removeMiningSets(String taskId, List<String> containerIds);

    List<DataSetContainer> removeAllMiningSets(String taskId);
}

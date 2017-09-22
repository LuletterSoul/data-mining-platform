package com.dm.org.service.impl;

import com.dm.org.model.Algorithm;
import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.MiningTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Service
public class MiningTaskServiceImpl extends AbstractBaseServiceImpl<DataMiningTask, String> implements MiningTaskService {

    @Override
    public DataMiningTask deleteByTaskId(String taskId) {
        DataMiningTask task = this.findById(taskId);
        this.deleteById(taskId);
        return task;
    }

    @Override
    public List<DataMiningGroup> fetchInvolvedGroups(String taskId) {
        return miningTaskDao.fetchInvolvedGroups(taskId);
    }

    @Override
    public DataMiningGroup getInvolvedGroup(String taskId, String groupId) {
        DataMiningGroup group = groupDao.fetchGroup(groupId);
        miningTaskDao.findById(taskId).getGroups().add(group);
        return group;
    }

    @Override
    public DataMiningGroup removeInvolvedGroup(String taskId, String groupId) {
        DataMiningGroup group = groupDao.findById(groupId);
        miningTaskDao.findById(taskId).getGroups().remove(group);
        return group;
    }


    @Override
    public List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds) {
        List<DataMiningGroup> groups = miningTaskDao.fetchPartInvolvedGroups(taskId, groupIds);
        miningTaskDao.removeInvolvedGroups(taskId, groupIds);
        return groups;
    }

    @Override
    public List<DataMiningGroup> getInvolvedGroups(String taskId) {
        return miningTaskDao.fetchInvolvedGroups(taskId);
    }

    @Override
    public DataMiningGroup involveGroup(String taskId, String groupId) {
        DataMiningGroup group = groupDao.fetchGroup(groupId);
        DataMiningTask miningTask = miningTaskDao.findById(taskId);
        group.setDataMiningTask(miningTask);
        miningTask.getGroups().add(group);
        return group;
    }

    @Override
    public List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds) {
        List<DataMiningGroup> groups = groupDao.fetchGroups(groupIds);
        DataMiningTask miningTask = miningTaskDao.findById(taskId);
        for (DataMiningGroup group :
                groups) {
            group.setDataMiningTask(miningTask);
        }
        return groups;
    }

    @Override
    public Algorithm getAlgorithm(String taskId, String algorithmId) {
        return miningTaskDao.getConfiguredAlgorithm(taskId, algorithmId);
    }

    @Override
    public List<Algorithm> fetchConfiguredAlgorithms(String taskId) {
        return miningTaskDao.getConfiguredAlgorithms(taskId);
    }

    @Override
    public Algorithm configureAlgorithm(String taskId, String algorithmId) {
        Algorithm algorithm = algorithmDao.fetchAlgorithm(algorithmId);
        miningTaskDao.findById(taskId).getAlgorithms().add(algorithm);
        return algorithm;
    }

    @Override
    public List<Algorithm> configureAlgorithms(String taskId, List<String> algorithmIds) {
        List<Algorithm> algorithms = algorithmDao.fetchAlgorithms(algorithmIds);
        miningTaskDao.findById(taskId).getAlgorithms().addAll(algorithms);
        return algorithms;
    }


    @Override
    public Algorithm removeAlgorithm(String taskId, String algorithmId) {
        Algorithm algorithm = algorithmDao.fetchAlgorithm(algorithmId);
        this.findById(taskId).getAlgorithms().remove(algorithm);
        return algorithm;
    }

    @Override
    public List<Algorithm> removeAlgorithms(String taskId, List<String> algorithmIds) {
        List<Algorithm> algorithms = algorithmDao.fetchAlgorithms(algorithmIds);
        miningTaskDao.removePartInvolvedAlgorithms(taskId,algorithmIds);
        return algorithms;
    }

    @Override
    public List<Algorithm> removeAllAlgorithms(String taskId) {
        List<Algorithm> algorithms = this.fetchConfiguredAlgorithms(taskId);
        miningTaskDao.removeAllInvolvedAlgorithm(taskId);
        return algorithms;
    }


    @Override
    public DataSetContainer arrangeMiningSet(String taskId, String containerId) {
        DataSetContainer container = containerDao.findById(containerId);
        miningTaskDao.findById(taskId).getDataSetContainers().add(container);
        return container;
    }

    @Override
    public List<DataSetContainer> arrangeMiningSets(String taskId, List<String> containerIds) {
        List<DataSetContainer> containers = containerDao.fetchContainers(containerIds);
        this.findById(taskId).getDataSetContainers().addAll(containers);
        return containers;
    }

    @Override
    public List<DataSetContainer> fetchRefContainers(String taskId) {
        return miningTaskDao.fetchRefContainers(taskId);
    }

    @Override
    public DataSetContainer removeMiningSet(String taskId, String containerId) {
        DataSetContainer container = containerDao.findById(containerId);
        this.findById(taskId).getDataSetContainers().remove(container);
        return container;
    }

    @Override
    public List<DataSetContainer> removeMiningSets(String taskId, List<String> containerIds) {
        List<DataSetContainer> containers = miningTaskDao.fetchPartRefSets(taskId, containerIds);
        this.findById(taskId).getDataSetContainers().removeAll(containers);
        return containers;
    }

    @Override
    public List<DataSetContainer> removeAllMiningSets(String taskId) {
        List<DataSetContainer> containers = this.fetchRefContainers(taskId);
        miningTaskDao.removeAllRefSet(taskId);
        return containers;
    }
}

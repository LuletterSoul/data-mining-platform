package com.dm.org.service.impl;

import com.dm.org.dto.MiningTaskDTO;
import com.dm.org.model.*;
import com.dm.org.service.MiningTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public DataMiningTask saveMiningTask(MiningTaskDTO miningTaskDTO) {
        DataMiningTask task = new DataMiningTask();
        BeanUtils.copyProperties(task, miningTaskDTO);
        List<DataSetCollection> collections = this.collectionDao.getCollectionByIds(miningTaskDTO.getCollectionIds());
        this.miningTaskDao.save(task);
        return task;
    }

    @Override
    public DataMiningTask deleteByTaskId(String taskId) {
        DataMiningTask task = this.findById(taskId);
        this.deleteById(taskId);
        return task;
    }

    @Override
    public Page<DataMiningTask> fetchTaskList(Pageable pageable) {
        int totalElements = miningTaskDao.countAll();
        return new PageImpl<DataMiningTask>(miningTaskDao.get(pageable), pageable, totalElements);
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
    public DataSetCollection arrangeMiningSet(String taskId, String collectionId) {
        DataSetCollection collection = collectionDao.findById(collectionId);
        miningTaskDao.findById(taskId).getCollections().add(collection);
        return collection;
    }

    @Override
    public List<DataSetCollection> arrangeMiningSets(String taskId, List<String> collectionIds) {
        List<DataSetCollection> collections = collectionDao.getCollectionByIds(collectionIds);
        this.findById(taskId).getCollections().addAll(collections);
        return collections;
    }

    @Override
    public List<DataSetCollection> fetchRefCollections(String taskId) {
        return miningTaskDao.fetchRefCollections(taskId);
    }

    @Override
    public DataSetCollection removeMiningSet(String taskId, String collectionId) {
        DataSetCollection collection = collectionDao.findById(collectionId);
        this.findById(taskId).getCollections().remove(collection);
        return collection;
    }

    @Override
    public List<DataSetCollection> removeMiningSets(String taskId, List<String> collectionIds) {
        List<DataSetCollection> collections = miningTaskDao.fetchPartRefSets(taskId, collectionIds);
        this.findById(taskId).getCollections().removeAll(collections);
        return collections;
    }

    @Override
    public List<DataSetCollection> removeAllMiningSets(String taskId) {
        List<DataSetCollection> collections = this.fetchRefCollections(taskId);
        miningTaskDao.removeAllRefSet(taskId);
        return collections;
    }
}

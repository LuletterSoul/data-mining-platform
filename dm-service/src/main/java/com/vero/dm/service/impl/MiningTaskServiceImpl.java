package com.vero.dm.service.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.Algorithm;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.DataSetCollection;
import com.vero.dm.repository.dto.MiningTaskDTO;
import com.vero.dm.service.MiningTaskService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Service
public class MiningTaskServiceImpl extends AbstractBaseServiceImpl<DataMiningTask, String> implements MiningTaskService
{

    @Override
    public DataMiningTask saveMiningTask(MiningTaskDTO miningTaskDTO)
    {
        // DataMiningTask task = new DataMiningTask();
        // BeanUtils.copyProperties(miningTaskDTO,task);
        // List<DataSetCollection> collections =
        // this.collectionDao.findCollectionsByIds(miningTaskDTO.getCollectionIds());
        // List<Algorithm> algorithms =
        // this.algorithmDao.fetchAlgorithms(miningTaskDTO.getAlgorithmIds());
        // task.setAlgorithms(new LinkedHashSet<Algorithm>(algorithms));
        // task.setArrangedCollections(new LinkedHashSet<DataSetCollection>(collections));
        // this.miningTaskDao.save(task);
        // return task;
        return null;
    }

    @Override
    public DataMiningTask deleteByTaskId(String taskId)
    {
        // DataMiningTask task = this.findById(taskId);
        // this.deleteById(taskId);
        // return task;
        return null;
    }

    @Override
    public Page<DataMiningTask> fetchTaskList(Pageable pageable)
    {
        // int totalElements = miningTaskDao.countAll();
        // return new PageImpl<DataMiningTask>(miningTaskDao.get(pageable), pageable,
        // totalElements);
        return null;
    }

    @Override
    public List<DataMiningGroup> fetchInvolvedGroups(String taskId)
    {
        // return miningTaskDao.fetchInvolvedGroups(taskId);
        return null;
    }

    @Override
    public DataMiningGroup getInvolvedGroup(String taskId, String groupId)
    {
        // DataMiningGroup group = groupDao.fetchGroup(groupId);
        // miningTaskDao.findById(taskId).getGroups().add(group);
        // return group;
        return null;
    }

    @Override
    public DataMiningGroup removeInvolvedGroup(String taskId, String groupId)
    {
        // DataMiningGroup group = groupDao.findById(groupId);
        // miningTaskDao.findById(taskId).getGroups().remove(group);
        // return group;
        return null;
    }

    @Override
    public List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds)
    {
        // List<DataMiningGroup> groups = miningTaskDao.fetchPartInvolvedGroups(taskId, groupIds);
        // miningTaskDao.removeInvolvedGroups(taskId, groupIds);
        // return groups;
        return null;
    }

    @Override
    public List<DataMiningGroup> getInvolvedGroups(String taskId)
    {
        // return miningTaskDao.fetchInvolvedGroups(taskId);
        return null;
    }

    @Override
    public DataMiningGroup involveGroup(String taskId, String groupId)
    {
        // DataMiningGroup group = groupDao.fetchGroup(groupId);
        // DataMiningTask miningTask = miningTaskDao.findById(taskId);
        // group.setDataMiningTask(miningTask);
        // miningTask.getGroups().add(group);
        // return group;
        return null;
    }

    @Override
    public List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds)
    {
        // List<DataMiningGroup> groups = groupDao.fetchGroups(groupIds);
        // DataMiningTask miningTask = miningTaskDao.findById(taskId);
        // for (DataMiningGroup group :
        // groups) {
        // group.setDataMiningTask(miningTask);
        // }
        // return groups;
        return null;
    }

    @Override
    public Algorithm getAlgorithm(String taskId, String algorithmId)
    {
        // return miningTaskDao.getConfiguredAlgorithm(taskId, algorithmId);
        return null;
    }

    @Override
    public List<Algorithm> fetchConfiguredAlgorithms(String taskId)
    {
        // return miningTaskDao.getConfiguredAlgorithms(taskId);
        return null;
    }

    @Override
    public Algorithm configureAlgorithm(String taskId, String algorithmId)
    {
        // Algorithm algorithm = algorithmDao.fetchAlgorithm(algorithmId);
        // miningTaskDao.findById(taskId).getAlgorithms().add(algorithm);
        // return algorithm;
        return null;
    }

    @Override
    public List<Algorithm> configureAlgorithms(String taskId, List<String> algorithmIds)
    {
        // List<Algorithm> algorithms = algorithmDao.fetchAlgorithms(algorithmIds);
        // miningTaskDao.findById(taskId).getAlgorithms().addAll(algorithms);
        // return algorithms;
        return null;
    }

    @Override
    public Algorithm removeAlgorithm(String taskId, String algorithmId)
    {
        // Algorithm algorithm = algorithmDao.fetchAlgorithm(algorithmId);
        // this.findById(taskId).getAlgorithms().remove(algorithm);
        // return algorithm;
        return null;
    }

    @Override
    public List<Algorithm> removeAlgorithms(String taskId, List<String> algorithmIds)
    {
        // List<Algorithm> algorithms = algorithmDao.fetchAlgorithms(algorithmIds);
        // miningTaskDao.removePartInvolvedAlgorithms(taskId,algorithmIds);
        // return algorithms;
        return null;
    }

    @Override
    public List<Algorithm> removeAllAlgorithms(String taskId)
    {
        // List<Algorithm> algorithms = this.fetchConfiguredAlgorithms(taskId);
        // miningTaskDao.removeAllInvolvedAlgorithm(taskId);
        // return algorithms;
        return null;
    }

    @Override
    public DataSetCollection arrangeMiningSet(String taskId, String collectionId)
    {
        // DataSetCollection collection = collectionDao.findById(collectionId);
        // miningTaskDao.findById(taskId).getArrangedCollections().add(collection);
        // return collection;
        return null;
    }

    @Override
    public List<DataSetCollection> arrangeMiningSets(String taskId, List<String> collectionIds)
    {
        // List<DataSetCollection> collections = collectionDao.findCollectionsByIds(collectionIds);
        // this.findById(taskId).getArrangedCollections().addAll(collections);
        // return collections;
        return null;
    }

    @Override
    public List<DataSetCollection> fetchRefCollections(String taskId)
    {
        // return miningTaskDao.fetchRefCollections(taskId);
        return null;
    }

    @Override
    public DataSetCollection removeMiningSet(String taskId, String collectionId)
    {
        // DataSetCollection collection = collectionDao.findById(collectionId);
        // this.findById(taskId).getArrangedCollections().remove(collection);
        // return collection;
        return null;
    }

    @Override
    public List<DataSetCollection> removeMiningSets(String taskId, List<String> collectionIds)
    {
        // List<DataSetCollection> collections = miningTaskDao.fetchPartRefSets(taskId,
        // collectionIds);
        // this.findById(taskId).getArrangedCollections().removeAll(collections);
        // return collections;
        return null;
    }

    @Override
    public List<DataSetCollection> removeAllMiningSets(String taskId)
    {
        // List<DataSetCollection> collections = this.fetchRefCollections(taskId);
        // miningTaskDao.removeAllRefSet(taskId);
        // return collections;
        return null;
    }
}

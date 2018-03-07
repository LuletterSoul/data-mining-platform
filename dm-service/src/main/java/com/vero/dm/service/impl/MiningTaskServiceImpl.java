package com.vero.dm.service.impl;


import static com.vero.dm.repository.specifications.TaskSpecifications.tasksSpec;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.Algorithm;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.DataSetCollection;
import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.service.MiningTaskService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Slf4j
@Service
public class MiningTaskServiceImpl extends AbstractBaseServiceImpl<DataMiningTask, String> implements MiningTaskService
{
    @Override
    public DataMiningTask findById(String id)
    {
        return taskJpaRepository.findOne(id);
    }

    @Override
    public DataMiningTask saveOrUpdateMiningTask(MiningTaskDto miningTaskDto)
    {
        DataMiningTask task = new DataMiningTask();
        BeanUtils.copyProperties(miningTaskDto, task);
        List<DataSetCollection> collections = collectionJpaRepository.findAll(
            miningTaskDto.getCollectionIds());
        List<Algorithm> algorithms = this.algorithmJpaRepository.findAll(
            miningTaskDto.getAlgorithmIds());
        task.setAlgorithms(new LinkedHashSet<>(algorithms));
        task.setArrangedCollections(new LinkedHashSet<>(collections));
        this.taskJpaRepository.save(task);
        return task;
    }

    @Override
    public DataMiningTask deleteByTaskId(String taskId)
    {
        DataMiningTask task = this.findById(taskId);
        task.setAlgorithms(null);
        task.setArrangedCollections(null);
        task.setGroups(null);
        taskJpaRepository.saveAndFlush(task);
        taskJpaRepository.delete(taskId);
        return task;
    }

    @Override
    public Page<DataMiningTask> fetchTaskList(boolean fetch, String taskName,
                                              Date plannedBeginDate, Date plannedEndDate,
                                              Date builtTimeBegin, Date builtTimeEnd,
                                              Pageable pageable)
    {
        if (fetch)
        {
            return new PageImpl<>(taskJpaRepository.findAll(tasksSpec(taskName, plannedBeginDate,
                plannedEndDate, builtTimeBegin, builtTimeEnd)));

        }
        else
        {
            return taskJpaRepository.findAll(tasksSpec(taskName, plannedBeginDate, plannedEndDate,
                builtTimeBegin, builtTimeEnd), pageable);
        }
    }

    @Override
    public List<DataMiningGroup> fetchInvolvedGroups(String taskId)
    {
        // return miningTaskDao.fetchInvolvedGroups(taskId);
        return groupJpaRepository.findByDataMiningTaskId(taskId);
    }

    @Override
    public List<DataMiningGroup> removeInvolvedGroups(String taskId, List<String> groupIds)
    {
        List<DataMiningGroup> groups = groupJpaRepository.findAll(groupIds);
        groups.forEach(g -> doArrangeTask(null, g));
        groupJpaRepository.save(groups);
        return groups;
    }

    @Override
    public DataMiningGroup involveGroup(String taskId, String groupId)
    {
        DataMiningGroup group = groupJpaRepository.findOne(groupId);
        DataMiningTask miningTask = taskJpaRepository.findOne(taskId);
        doArrangeTask(miningTask, group);
        groupJpaRepository.saveAndFlush(group);
        return group;
    }

    @Override
    public List<DataMiningGroup> involveGroups(String taskId, List<String> groupIds)
    {
        List<DataMiningGroup> groups = groupJpaRepository.findAll(groupIds);
        DataMiningTask miningTask = taskJpaRepository.findOne(taskId);
        groups.forEach(g -> doArrangeTask(miningTask, g));
        groupJpaRepository.save(groups);
        return groups;
    }

    private void doArrangeTask(DataMiningTask miningTask, DataMiningGroup g)
    {
        if (g.getDataMiningTask() == null)
        {
            g.setDataMiningTask(miningTask);
        }
        else
        {
            log.error(
                "Group [{}] has arranged a task [].Please revoke their relationship firstly.",
                g.getGroupName(), g.getDataMiningTask().getTaskName());
        }
    }

    @Override
    public List<Algorithm> fetchConfiguredAlgorithms(String taskId)
    {
        return new LinkedList<>(findById(taskId).getAlgorithms());
    }

    @Override
    public List<Algorithm> configureAlgorithms(String taskId, List<Integer> algorithmIds)
    {
        DataMiningTask task = findById(taskId);
        List<Algorithm> algorithms = algorithmJpaRepository.findAll(algorithmIds);
        if (algorithmIds == null || algorithmIds.isEmpty())
        {
            task.setAlgorithms(new LinkedHashSet<>());
            taskJpaRepository.save(task);
            return new LinkedList<>();
        }
        task.setAlgorithms(new LinkedHashSet<>(algorithms));
        taskJpaRepository.save(task);
        return algorithms;
    }

    @Override
    public List<DataSetCollection> configureMiningSets(String taskId, List<String> collectionIds)
    {
        List<DataSetCollection> collections = collectionJpaRepository.findAll(collectionIds);
        DataMiningTask task = findById(taskId);
        task.setArrangedCollections(new LinkedHashSet<>(collections));
        taskJpaRepository.save(task);
        return collections;
    }

    @Override
    public List<DataSetCollection> fetchRefCollections(String taskId)
    {
        return new LinkedList<>(findById(taskId).getArrangedCollections());
    }

    @Override
    public List<DataSetCollection> removeAllMiningSets(String taskId)
    {
        DataMiningTask task = findById(taskId);
        List<DataSetCollection> collections = fetchRefCollections(taskId);
        task.setArrangedCollections(null);
        taskJpaRepository.save(task);
        return collections;
    }
}

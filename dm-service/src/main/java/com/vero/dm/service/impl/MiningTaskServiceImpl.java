package com.vero.dm.service.impl;


import static com.vero.dm.repository.specifications.ResultSpecifications.resultsSpec;
import static com.vero.dm.repository.specifications.TaskSpecifications.tasksSpec;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.model.enums.ResultState;
import com.vero.dm.model.enums.StatusObject;
import com.vero.dm.model.enums.TaskProgressStatus;
import com.vero.dm.repository.dto.DataMiningGroupDto;
import com.vero.dm.repository.dto.MiningTaskDto;
import com.vero.dm.repository.dto.StudentDto;
import com.vero.dm.repository.dto.TaskStatistics;
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
    public List<DataMiningTask> findAllTasks()
    {
        return taskJpaRepository.findAll();
    }

    @Override
    public List<DataMiningTask> findByTaskIds(List<String> taskIds)
    {
        return taskJpaRepository.findAll(taskIds);
    }

    @Override
    public List<String> findAllTaskNames()
    {
        return taskJpaRepository.findAllTaskNames();
    }

    @Override
    public TaskStatistics findStatistics(String taskId)
    {
        DataMiningTask task = taskJpaRepository.findOne(taskId);
        Set<MiningTaskStage> stages = task.getStages();
        Iterator it = stages.iterator();
        Map<Integer, Integer> submittedStatistics = new LinkedHashMap<>();
        Map<Integer, Integer> noSubmittedStatistics = new LinkedHashMap<>();
        Map<Integer, Integer> downloadedStatistics = new LinkedHashMap<>();
        Map<Integer, Integer[]> stageToGroupSubmitted = new LinkedHashMap<>();
        Map<ResultState, List<Integer>> stateToGroup = new LinkedHashMap<>();
        for (int i = 0; i < ResultState.values().length; i++) {
            stateToGroup.put(ResultState.values()[i], new ArrayList<>());
        }
        List<StudentDto> absentStudents;
        while (it.hasNext())
        {
            MiningTaskStage stage = (MiningTaskStage)it.next();
            List<MiningResult> submittedResults = miningResultRepository.findAll(
                resultsSpec(taskId, stage.getStageId(), null, ResultState.submitted));
            stateToGroup.get(ResultState.submitted).add(submittedResults.size());
            submittedStatistics.put(stage.getStageId(), submittedResults.size());


            List<MiningResult> noSubmittedResults = miningResultRepository.findAll(
                resultsSpec(taskId, stage.getStageId(), null, ResultState.noResult));
            stateToGroup.get(ResultState.noResult).add(noSubmittedResults.size());
            noSubmittedStatistics.put(stage.getStageId(), noSubmittedResults.size());


            List<MiningResult> downloadedResults = miningResultRepository.findAll(
                resultsSpec(taskId, stage.getStageId(), null, ResultState.downloaded));
            stateToGroup.get(ResultState.downloaded).add(downloadedResults.size());
            downloadedStatistics.put(stage.getStageId(), downloadedResults.size());

            Integer[] sizes = new Integer[]{submittedResults.size(), noSubmittedResults.size(), downloadedResults.size()};
            stageToGroupSubmitted.put(stage.getStageId(), sizes);
        }
        List<Student> students = taskJpaRepository.findSpecializedStateStudents(taskId, ResultState.noResult);
        Set<Student> uniqueStus = new HashSet<>(students);
        absentStudents = StudentDto.build(new ArrayList<>(uniqueStus));
        return new TaskStatistics(submittedStatistics, noSubmittedStatistics, downloadedStatistics,
                absentStudents, stageToGroupSubmitted,stateToGroup);
    }

    @Override
    public DataMiningTask saveOrUpdateMiningTask(MiningTaskDto miningTaskDto)
    {
        DataMiningTask task = new DataMiningTask();
        BeanUtils.copyProperties(miningTaskDto, task);
        List<DataMiningGroup> groups = groupJpaRepository.findAll(
            miningTaskDto.getArrangeGroupIds());
        List<DataSetCollection> collections = collectionJpaRepository.findAll(
            miningTaskDto.getCollectionIds());
        List<Algorithm> algorithms = this.algorithmJpaRepository.findAll(
            miningTaskDto.getAlgorithmIds());
        List<MiningGrammar> grammars = this.miningGrammarRepository.findAll(
            miningTaskDto.getGrammarIds());
        groups.forEach(g -> {
            // 更新当前任务状态
            g.setDataMiningTask(task);
            g.setTaskStatus(MiningTaskStatus.newTask);
        });
        // 用户没有指定当前任务的执行分组,修改任务状态为“新创建”
        if (groups.isEmpty())
        {
            task.setProgressStatus(TaskProgressStatus.newCreate);
        }
        else
        {
            task.setProgressStatus(TaskProgressStatus.assigned);
        }
        task.setAlgorithms(new LinkedHashSet<>(algorithms));
        task.setGrammars(new LinkedHashSet<>(grammars));
        task.setArrangedCollections(new LinkedHashSet<>(collections));
        task.setGroups(new LinkedHashSet<>(groups));
        task.setBuiltTime(new Date());
        task.setPlannedStartTime(miningTaskDto.getPlannedTimeRange()[0]);
        task.setPlannedFinishTime(miningTaskDto.getPlannedTimeRange()[1]);
        this.taskJpaRepository.saveAndFlush(task);
        this.groupJpaRepository.save(groups);

        // 持久化任务阶段信息
        Set<MiningTaskStage> stages = miningTaskDto.getStages();
        stages.forEach(s -> {
            Date[] deadline = s.getDeadline();
            s.setTask(task);
            s.setBegin(deadline[0]);
            s.setEnd(deadline[1]);
        });
        this.taskStageJpaRepository.save(stages);
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
    public List<DataMiningTask> deleteBatchTask(List<String> taskIds)
    {
        List<DataMiningTask> tasks = findByTaskIds(taskIds);
        tasks.forEach(t -> {
            Set<DataMiningGroup> groups = t.getGroups();
            groups.forEach(g -> {
                // 修改当前任务状态
                g.setDataMiningTask(null);
                g.setTaskStatus(MiningTaskStatus.toBeAssigned);
            });
            groupJpaRepository.save(groups);
            t.setAlgorithms(null);
            t.setArrangedCollections(null);
            t.setGroups(null);
        });
        taskJpaRepository.delete(tasks);
        return tasks;
    }

    @Override
    public Page<MiningTaskDto> fetchTaskList(boolean fetch, String taskName, Date plannedBeginDate,
                                             Date plannedEndDate, Date builtTimeBegin,
                                             Date builtTimeEnd, String studentId,
                                             Pageable pageable, TaskProgressStatus progressStatus,
                                             Integer lowBound, Integer upperBound)
    {
        List<DataMiningTask> finalResult = new LinkedList<>();
        List<DataMiningTask> taskLimit = taskJpaRepository.findByLinkedGroupsBound(lowBound,
            upperBound);
        if (fetch)
        {
            List<DataMiningTask> tasks = taskJpaRepository.findAll(
                tasksSpec(taskName, plannedBeginDate, plannedEndDate, builtTimeBegin, builtTimeEnd,
                    progressStatus, studentId));
            taskLimit.forEach(c -> {
                if (tasks.contains(c))
                {
                    finalResult.add(c);
                }
            });
        }
        else
        {
            Page<DataMiningTask> tasks = taskJpaRepository.findAll(
                tasksSpec(taskName, plannedBeginDate, plannedEndDate, builtTimeBegin, builtTimeEnd,
                    progressStatus, studentId),
                pageable);
            List<DataMiningTask> content = tasks.getContent();
            content.forEach(c -> {
                if (taskLimit.contains(c))
                {
                    finalResult.add(c);
                }
            });
        }
        return new PageImpl<>(MiningTaskDto.build(finalResult));
    }

    @Override
    public Map<String, List<DataMiningGroupDto>> fetchInvolvedGroups(List<String> taskIds)
    {
        // return miningTaskDao.fetchInvolvedGroups(taskId);
        Map<String, List<DataMiningGroupDto>> groups = new HashMap<>();
        List<DataMiningTask> tasks = taskJpaRepository.findAll(taskIds);
        tasks.forEach(t -> {
            List<DataMiningGroup> miningGroups = groupJpaRepository.findByDataMiningTaskId(
                t.getTaskId());
            miningGroups.forEach(m -> m.getGroupMembers().size());
            groups.put(t.getTaskId(), DataMiningGroupDto.build(miningGroups));
        });
        return groups;
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
    public List<MiningTaskStage> fetchRefStages(String taskId)
    {
        return new LinkedList<>(taskJpaRepository.findOne(taskId).getStages());
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
    public List<DataSetCollection> configureMiningSets(String taskId, List<Integer> collectionIds)
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

    @Override
    public List<StatusObject> fetchProgressStatus()
    {
        return TaskProgressStatus.enum2Objects();
    }

    @Override
    public Integer[] minAndMaxGroupNum()
    {
        Integer[] max_min_pair = new Integer[2];
        if (findAllTasks().isEmpty())
        {
            max_min_pair[0] = 0;
            max_min_pair[1] = 0;
            return max_min_pair;
        }
        max_min_pair[0] = taskJpaRepository.findMaxGroupNum();
        max_min_pair[1] = taskJpaRepository.findMinGroupNum();
        return max_min_pair;
    }
}

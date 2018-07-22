package com.vero.dm.service.grouper;


import java.util.*;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import com.vero.dm.repository.dto.PreviewDividingGroupDto;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:10 2018/7/21.
 * @since data-mining-platform
 */

public abstract class AbstractGroupingStrategy implements GroupingStrategy
{

    private Integer gradient;

    protected Random random = new Random();

    // 分组剩下的学生
    protected List<Student> remainedStus = new ArrayList<>();

    // 剩下未被分配的任务
    protected List<String> remainedTasks = new ArrayList<>();

    public void setGradient(Integer gradient)
    {
        this.gradient = gradient;
    }

    @Override
    public DividingGroupInfo doStrategy(GroupingConfigParams params, List<Student> candidates,
                                        List<String> tasksIds)
    {
        // 算法预处理，主要用于清除策略的状态、
        preHandle();
        Integer lowerBound = params.getLowerBound();
        Integer upperBound = params.getUpperBound();
        Integer taskSize = params.getSpecifiedTasks().size();
        Integer candidateSize = candidates.size();
        preStrategyEvaluation(lowerBound, upperBound, taskSize, candidateSize);
        gradient = nextGradient(params.getLowerBound(), params.getUpperBound());
        // 将要被分配的任务;
        List<DataMiningGroup> previewDefaultGroups = new LinkedList<>();
        List<Student> perGroupStudents = new LinkedList<>();
        Iterator taskIt = tasksIds.iterator();
        Iterator stuIt = candidates.iterator();
        int i = 0;
        while (stuIt.hasNext() && taskIt.hasNext())
        {
            Student s = (Student)stuIt.next();
            perGroupStudents.add(s);
            i++ ;
            // 到达固定人数分一组;
            if (i == gradient)
            {
                String taskId = (String)taskIt.next();
                String builderId = params.getBuilderId();
                DataMiningGroup group = buildGroup(perGroupStudents,
                    RandomUtils.generateShortUuid(), builderId, taskId);
                // 生成预览分组情况，待管理员确认;
                previewDefaultGroups.add(group);
                perGroupStudents.clear();
                gradient = nextGradient(params.getLowerBound(), params.getUpperBound());
                i = 0;
            }
        }
        // 符合条件的学生比每组的人数少,直接作为一组
        if (!perGroupStudents.isEmpty() && perGroupStudents.size() < gradient)
        {
            DataMiningGroup group = buildGroup(perGroupStudents, RandomUtils.generateShortUuid(), params.getBuilderId(),
                params.getTaskId());
            previewDefaultGroups.add(group);
        }
        handleRemainedResources(taskIt, stuIt);
        String queryKey = handlePreview(previewDefaultGroups);
        return new DividingGroupInfo(queryKey, PreviewDividingGroupDto.build(previewDefaultGroups),
            previewDefaultGroups.get(0).getDataMiningTask(), remainedStus, remainedTasks);
    }

    // 保存未加入到分组的资源(学生、任务)
    private void handleRemainedResources(Iterator taskIt, Iterator stuIt)
    {
        while (taskIt.hasNext())
        {
            remainedTasks.add((String)taskIt.next());
        }
        while (stuIt.hasNext())
        {
            remainedStus.add((Student)stuIt.next());
        }
    }

    @Override
    public abstract Integer nextGradient(Integer lowBound, Integer upperBound);

    @Override
    public abstract void preStrategyEvaluation(Integer lowerBound, Integer upperBound,
                                               Integer taskSize, Integer candidateSize);

    protected abstract DataMiningGroup buildGroup(List<Student> perGroupStudents,
                                                  String arrangementId, String builderId,
                                                  String taskId);

    @Override
    public abstract String handlePreview(List<DataMiningGroup> previewDefaultGroups);

    public abstract void preHandle();
}

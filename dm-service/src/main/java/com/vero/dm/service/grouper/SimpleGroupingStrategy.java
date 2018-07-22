package com.vero.dm.service.grouper;


import java.util.List;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.Student;

import lombok.extern.slf4j.Slf4j;


/**
 * 按下界等分策略
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:24 2018/7/21.
 * @since data-mining-platform
 */

@Slf4j
public class SimpleGroupingStrategy extends AbstractGroupingStrategy
{

    private MiningGrouper grouper;

    public SimpleGroupingStrategy() {
    }

    public SimpleGroupingStrategy(MiningGrouper grouper)
    {
        this.grouper = grouper;
    }

    @Override
    public Integer nextGradient(Integer lowBound, Integer upperBound)
    {
        return (lowBound + upperBound)/2;
    }

    @Override
    public void preStrategyEvaluation(Integer lowerBound, Integer upperBound, Integer taskSize,
                                      Integer candidateSize)
    {

    }

    @Override
    protected DataMiningGroup buildGroup(List<Student> perGroupStudents, int i, String builderId,
                                         String taskId)
    {
        return grouper.buildGroup(builderId, taskId, i, perGroupStudents);
    }

    @Override
    public String handlePreview(List<DataMiningGroup> previewDefaultGroups)
    {
        return grouper.cachePreview(previewDefaultGroups);
    }

    @Override
    public void setGrouper(MiningGrouper grouper)
    {
        this.grouper = grouper;
    }
}

package com.vero.dm.service.grouper;


import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.Student;
import com.vero.dm.repository.dto.DividingGroupInfo;
import com.vero.dm.repository.dto.GroupingConfigParams;
import sun.nio.cs.ext.MacSymbol;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:53 2018/7/21.
 * @since data-mining-platform
 */

public interface GroupingStrategy
{
    /**
     * 分组策略
     * @param params 包含了分组算法的所需的所有参赛
     * @param candidates
     * @param tasksIds
     * @return 分组的预览信息
     */
    DividingGroupInfo doStrategy(GroupingConfigParams params, List<Student> candidates, List<String> tasksIds);

    /**
     * 下一组的每组人数
     * @param lowBound 每组最少人数
     * @param upperBound 每组最多人数
     * @return [lowBound,upperBound]中的一个数值
     */
    Integer nextGradient(Integer lowBound, Integer upperBound);


    void preStrategyEvaluation(Integer lowerBound, Integer upperBound, Integer taskSize,
                               Integer candidateSize);

    String handlePreview(List<DataMiningGroup> previewDefaultGroups);


    void setGrouper(MiningGrouper grouper);
}

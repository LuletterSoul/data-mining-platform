package com.vero.dm.service.grouper;


import java.util.Random;

import lombok.extern.slf4j.Slf4j;


/**
 * 随机分组策略
 *
 * 不确保每个任务都被分配到一个分组
 *
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:39 2018/7/21.
 * @since data-mining-platform
 */

@Slf4j
public class RandomGroupingStrategy extends SimpleGroupingStrategy
{

    protected MiningGrouper grouper;

    public RandomGroupingStrategy() {
    }

    public RandomGroupingStrategy(MiningGrouper grouper)
    {
        super(grouper);
    }

    @Override
    public Integer nextGradient(Integer lowBound, Integer upperBound)
    {
        if (upperBound < lowBound)
        {
            throw new IllegalArgumentException("Group low bound is illegal.");
        }
        return random.nextInt(upperBound - lowBound) + lowBound;
    }
}

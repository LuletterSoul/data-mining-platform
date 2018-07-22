package com.vero.dm.service.grouper;


import java.util.Random;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.group.InvalidGroupingConfigException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * 每组人数满足上下界的同时 保证学生全部被分组且任务被分配完 无法满足上下界的要求时，抛出异常
 * 
 * @author XiangDe Liu qq313700046@icloud.com
 * @version 1.5 created in 21:39 2018/7/21.
 * @since data-mining-platform
 */

@Getter
@Slf4j
public class RestrictGroupingStrategy extends SimpleGroupingStrategy
{
    private Integer[] scheme;

    private Integer pos = 0;

    public RestrictGroupingStrategy()
    {}

    public RestrictGroupingStrategy(MiningGrouper grouper)
    {
        super(grouper);
    }

    @Override
    public void preStrategyEvaluation(Integer lowerBound, Integer upperBound, Integer taskSize,
                                      Integer candidateSize)
    {
        int average = (int)Math.floor(candidateSize / taskSize);
        if (average < lowerBound || average > upperBound)
        {
            String message = "无法产生每组人数在上下界的范围内,且能被分配所有任务的分组,请调整分组人数的上下界";
            log.info(message);
            throw new InvalidGroupingConfigException(message, ExceptionCode.InvalidGroupingConfig);
        }
        generateRandomSeq(lowerBound, upperBound, taskSize, candidateSize, average);
    }

    public void generateRandomSeq(Integer lowerBound, Integer upperBound, Integer taskSize,
                                  Integer candidateSize, int average)
    {
        Random random = new Random();
        scheme = new Integer[taskSize];
        for (int i = 0; i < taskSize; i++ )
        {
            scheme[i] = average;
        }
        boolean re = false;
        int step;
        // 随机振荡打乱
        for (int i = 0; i < 10000; i++ )
        {
            int index = random.nextInt(taskSize);
            re = !re;
            if (re)
            {
                step = 1;
            }
            else
                step = -1;
            int cu = scheme[index] + step;
            if (!inside(lowerBound, upperBound, average, cu))
            {
                continue;
            }
            scheme[index] = cu;
        }
        int total = 0;
        for (Integer aScheme : scheme)
        {
            total += aScheme;
        }
        total = candidateSize - total;
        if (total < 0)
        {
            step = -1;
        }
        else if (total > 0)
        {
            step = 1;
        }
        else
            return;
        total = Math.abs(total);
        // 校正，将多余分配的，或者剩下未分配的重新随机打乱，确保最后总的人数为候选人数
        while (true)
        {
            int index = random.nextInt(taskSize);
            int cn = scheme[index] + step;
            if (inside(lowerBound, upperBound, average, cn))
            {
                scheme[index] = cn;
                --total;
            }
            if (total == 0) break;
        }
    }

    private boolean inside(Integer lowerBound, Integer upperBound, int average, int cu)
    {
        return cu >= lowerBound && cu <= upperBound && cu - average <= 3;
    }

    @Override
    public Integer nextGradient(Integer lowBound, Integer upperBound)
    {
        if (pos >= scheme.length)
        {
            return 0;
        }
        return scheme[pos++ ];
    }

    @Override
    public void preHandle()
    {
        pos = 0;
    }
}

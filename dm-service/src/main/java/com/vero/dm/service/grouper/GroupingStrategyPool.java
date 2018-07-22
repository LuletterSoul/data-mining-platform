package com.vero.dm.service.grouper;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.ClassUtils;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:06 2018/7/22.
 * @since data-mining-platform
 */

@Slf4j
public enum GroupingStrategyPool {
    SIMPLE(1, "简单分组策略", SimpleGroupingStrategy.class),
    RANDOM(2, "随机分组策略", RandomGroupingStrategy.class),
    RESTRICT(3, "严格分组策略", RestrictGroupingStrategy.class);

    private int value;

    private String description;

    private final Class<?> strategyClass;

    public static Map<Integer, GroupingStrategy> pool = new HashMap<>();


    GroupingStrategyPool(int value, String description, Class<?> strategyClass)
    {
        this.value = value;
        this.description = description;
        this.strategyClass = strategyClass;
    }

    public GroupingStrategy newInstance()
    {
        return (GroupingStrategy)ClassUtils.newInstance(this.strategyClass);
    }

    public static Map<Integer, GroupingStrategy> createInstancePool(MiningGrouper grouper)
    {
        Map<Integer, GroupingStrategy> strategies = new LinkedHashMap<>(values().length);
        for (GroupingStrategyPool defaultStrategy : values())
        {
            GroupingStrategy strategy = defaultStrategy.newInstance();
            if (grouper != null) {
                strategy.setGrouper(grouper);
            }
            else{
                log.error("分组策略池实例失败，分组器不能为空");
            }
            strategies.put(defaultStrategy.getValue(), strategy);
        }
        pool = strategies;
        return strategies;
    }

    public int getValue()
    {
        return this.value;
    }

    public String getDescription()
    {
        return this.description;
    }
}

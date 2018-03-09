package com.vero.dm.repository.dto;


import java.sql.Date;
import java.util.List;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
public class MiningTaskDto
{
    /**
     * 挖掘任务的Id
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 可被分配的任务编号
     */
    private String arrangementId;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 任务描述
     */
    private String taskDescription;

    /**
     * 建立时间
     */
    private Date builtTime;


    private Date[] plannedTimeRange;


    /**
     * 任务状态
     */
    private Integer statusValue;

    /**
     * 每个任务可被分给多个分组，内容相似
     */
    private List<String> arrangeGroupIds;

    /**
     * 每个分组可以被分配多个数据集
     */
    private List<String> collectionIds;

    /**
     * 每个数据任务可以采用多种算法
     */
    private List<Integer> algorithmIds;

}

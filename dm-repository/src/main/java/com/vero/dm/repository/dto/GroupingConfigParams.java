package com.vero.dm.repository.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分组参数
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:11 2017/10/13.
 * @since data-mining-platform
 */

@Data
public class GroupingConfigParams
{

    private Integer strategyId = 1;
    /**
     * 统一分配任务
     */
    private String taskId;

    /**
     * 开始日期，此日期是为了筛选这段期间没有被分配任务的学生algorithm_param
     */
    private Date beginDate;

    /**
     * 配合上面得到日期的范围
     */
    private Date endDate;

    /**
     * 分组梯度，每组多少人,默认每组12人
     */
    private Integer gradient =12;

    /**
     * 按照[lower,upper]生成随机生成一个梯度
     * 每组人数的下界
     */
    private  Integer lowerBound = 1;


    /**
     * 每组人数的上界
     * 默认为12
     */
    private Integer upperBound =12;

    /**
     * 忽略被分配的任务
     */
    private Boolean isIgnoreArrangedTask = false;

    /**
     * 指定要分组的学生
     */
    private List<String> specifiedDividingStudents = new ArrayList<>();


    /**
     * 指定要分发的任务
     */
    private List<String> specifiedTasks = new ArrayList<>();

    /**
     * 建立者
     */
    private String builderId;


    /**
     *
     * 一键分组的代号
     */
    private String buildingKey;
}

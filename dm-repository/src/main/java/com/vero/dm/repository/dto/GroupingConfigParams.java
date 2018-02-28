package com.vero.dm.repository.dto;


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


    /**
     * 统一分配任务
     */
//    @ApiParam(value = "给每个分组分配的任务",required = true)
    private String taskId;

    /**
     * 开始日期，此日期是为了筛选这段期间没有被分配任务的学生algorithm_param
     */
//    @ApiParam(value = "范围时间")
    private Date beginDate;

    /**
     * 配合上面得到日期的范围
     */
    private Date endDate;

    /**
     * 分组梯度，每组多少人,默认每组12人
     */
//    @ApiParam(value = "每组多少人")
    private Integer gradient =12;

    /**
     * 忽略被分配的任务
     */
//    @ApiParam(value = "忽略有任务在身的学生,继续分组")
    private Boolean isIgnoreArrangedTask = false;

    /**
     * 指定要分组的学生
     */
//    @ApiParam(value = "指定要分组的学生标识")
    private List<String> specifiedDividingStudents;

    /**
     * 建立者
     */
//    @ApiParam(value = "建队人")
    private String builderId;

    /**
     * 一键分组的代号
     */
    private String buildingKey;
}

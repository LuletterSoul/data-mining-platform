package com.vero.dm.repository.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 18:19 2018/1/31.
 * @since data-mining-platform
 */

@Data
public class DataMiningGroupDto
{

    /**
     * 数据库标识
     */
    private String groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 被分配的任务编号
     */
    private String arrangementId;

    /**
     * 被建立的时间
     */
    private Date builtTime;

    private List<String> memberIds;

    private String leaderId;

    private String builderId;

    private String taskId;

    public static final Integer DEFAULT_GRADIENT = 12;
}

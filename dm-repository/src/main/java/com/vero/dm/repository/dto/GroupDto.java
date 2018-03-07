package com.vero.dm.repository.dto;

import com.vero.dm.model.converter.MiningTaskStatusConverter;
import com.vero.dm.model.enums.MiningTaskStatus;
import com.vero.dm.model.enums.MiningTaskStatusWrapper;
import lombok.Data;

import javax.persistence.Convert;
import java.util.Date;
import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  22:35 2018/3/7.
 * @since data-mining-platform
 */

@Data
public class GroupDto {

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

    /**
     * 组员
     */
    private List<String> memberIds;

    /**
     * 组长
     */

    private String leaderId;




    /**
     * 每个分组只能持有一个发掘任务
     */
    private String taskId;

    /**
     * 任务状态
     */
    private int taskStatus;
}

package com.vero.dm.repository.dto;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.User;
import com.vero.dm.model.enums.MiningTaskStatus;

import lombok.Data;


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
     * 被建立的时间
     */
    private Date builtTime;

    /**
     * 任务状态
     */
    private int taskStatus;

    /**
     * 任务状态实体
     */
    private MiningTaskStatus miningTaskStatus;

    /**
     * 被分配的任务编号
     */
    private String arrangementId;

    /**
     * 组员
     */
    private List<String> memberIds;


//    private List<Student> groupMembers;

    /**
     * 组员数
     */
    private Integer memberSize;

    /**
     * 组长
     */
    private String leaderId;

    private Student groupLeader;

    /**
     * 每个分组只能持有一个发掘任务
     */

    private String taskId;

    private DataMiningTask dataMiningTask;

    private String builderId;

    public static final Integer DEFAULT_GRADIENT = 12;

    public DataMiningGroupDto()
    {}

    public DataMiningGroupDto(String groupId, String groupName, Date builtTime, int taskStatus,
                              MiningTaskStatus miningTaskStatus, String arrangementId,
                              List<String> memberIds, Integer memberSize,Student groupLeader, DataMiningTask dataMiningTask)
    {
        this.groupId = groupId;
        this.groupName = groupName;
        this.builtTime = builtTime;
        this.taskStatus = taskStatus;
        this.miningTaskStatus = miningTaskStatus;
        this.arrangementId = arrangementId;
        this.memberIds = memberIds;
        this.memberSize = memberSize;
        this.groupLeader = groupLeader;
        this.dataMiningTask = dataMiningTask;
        // this.builderId = builderId;
    }

    public static DataMiningGroupDto build(DataMiningGroup group)
    {
        return new DataMiningGroupDto(group.getGroupId(), group.getGroupName(),
            group.getBuiltTime(), group.getTaskStatus().getValue(), group.getTaskStatus(),
            group.getArrangementId(),
            group.getGroupMembers().stream().map(User::getUserId).collect(Collectors.toList()),
            group.getGroupMembers().size(), group.getGroupLeader(), group.getDataMiningTask());
    }

    public static List<DataMiningGroupDto> build(List<DataMiningGroup> groups)
    {
        return groups.stream().map(DataMiningGroupDto::build).collect(Collectors.toList());
    }

}

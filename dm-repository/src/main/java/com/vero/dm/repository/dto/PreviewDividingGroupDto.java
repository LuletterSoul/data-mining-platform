package com.vero.dm.repository.dto;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.Teacher;

import lombok.Data;


/**
 * 预览分组
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:35 2018/2/1.
 * @since data-mining-platform
 */

@Data
public class PreviewDividingGroupDto
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

    /**
     * 组员
     */
    private List<Student> groupMembers;

    /**
     * 组员数
     */
    private Integer memberSize;

    /**
     * 组长
     */
    private Student groupLeader;

    /**
     * 建立此分组的教师
     */
    private Teacher teacherBuilder;

    /**
     * 建立此分组的学生
     */
    private Student studentBuilder;

    /**
     * 每个分组只能持有一个发掘任务
     */
    private DataMiningTask dataMiningTask;

    public static PreviewDividingGroupDto build(DataMiningGroup group)
    {
        PreviewDividingGroupDto dividingGroupDto = new PreviewDividingGroupDto();
        dividingGroupDto.setArrangementId(group.getArrangementId());
        dividingGroupDto.setBuiltTime(group.getBuiltTime());
        dividingGroupDto.setGroupLeader(group.getGroupLeader());
        dividingGroupDto.setTeacherBuilder(group.getTeacherBuilder());
        dividingGroupDto.setStudentBuilder(group.getStudentBuilder());
        dividingGroupDto.setGroupMembers(new ArrayList<>(group.getGroupMembers()));
        dividingGroupDto.setDataMiningTask(group.getDataMiningTask());
        dividingGroupDto.setGroupId(group.getGroupId());
        dividingGroupDto.setGroupName(group.getGroupName());
        dividingGroupDto.setMemberSize(group.getGroupMembers().size());
        return dividingGroupDto;
    }

    public static List<PreviewDividingGroupDto> build(List<DataMiningGroup> groups)
    {
        List<PreviewDividingGroupDto> dividingGroupDtos = new ArrayList<>();
        groups.forEach(g -> dividingGroupDtos.add(build(g)));
        return dividingGroupDtos;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PreviewDividingGroupDto that = (PreviewDividingGroupDto)o;
        return Objects.equal(groupId, that.groupId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), groupId);
    }
}

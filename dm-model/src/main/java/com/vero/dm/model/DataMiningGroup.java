package com.vero.dm.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.vero.dm.model.converter.MiningTaskStatusConverter;
import com.vero.dm.model.enums.MiningTaskStatus;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

import lombok.Data;
import lombok.ToString;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * @since data-mining-platform
 */

@Data
@Entity
@Table(name = "data_mining_group")
@ToString(exclude = {"groupMembers","groupLeader"})
public class DataMiningGroup
{
    /**
     * 数据库标识
     */
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
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
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "group_student_rel", joinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId"), inverseJoinColumns = @JoinColumn(name = "memberId", referencedColumnName = "userId"))
    private Set<Student> groupMembers;

    /**
     * 组长
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupLeaderId", foreignKey = @ForeignKey(name = "LEADER_FK_ID"))
    private Student groupLeader;

    /**
     * 建立此分组的教师
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacherUserId", foreignKey = @ForeignKey(name = "T_BUILDER_FK_ID"))
    private Teacher teacherBuilder;

    /**
     * 建立此分组的学生
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentUserId", foreignKey = @ForeignKey(name = "S_BUILDER_FK_ID"))
    private Student studentBuilder; 

    /**
     * 每个分组只能持有一个发掘任务
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId", foreignKey = @ForeignKey(name = "TASK_FOREIGN_KEY"))
    private DataMiningTask dataMiningTask;

    /**
     * 任务状态
     */
    @Convert(converter = MiningTaskStatusConverter.class)
    private MiningTaskStatus taskStatus;

    /**
     * 分组提交过的所有发掘结果
     */
    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<MiningResult> results;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DataMiningGroup group = (DataMiningGroup)o;
        return Objects.equal(groupId, group.groupId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), groupId);
    }
}

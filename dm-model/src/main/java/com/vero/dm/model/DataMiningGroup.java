package com.vero.dm.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * @since data-mining-platform
 */

@Data
@Entity
@Table(name = "data_mining_group")
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupLeaderId", foreignKey = @ForeignKey(name = "LEADER_FK_ID"))
    private Student groupLeader;

    /**
     * 建立此分组的教师
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacherUserId", foreignKey = @ForeignKey(name = "BUILDER_FK_ID"))
    private Teacher groupBuilder;


    /**
     * 每个分组只能持有一个发掘任务
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId", foreignKey = @ForeignKey(name = "TASK_FOREIGN_KEY"))
    private DataMiningTask dataMiningTask;
}

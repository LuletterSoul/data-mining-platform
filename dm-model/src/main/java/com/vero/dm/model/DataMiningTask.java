package com.vero.dm.model;


import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.vero.dm.model.converter.TaskProgressStatusConverter;
import com.vero.dm.model.enums.TaskProgressStatus;

import lombok.Data;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 * @since data-mining-platform
 */

/**
 * 数据挖掘任务实体
 */
@Data
@Entity
@Table(name = "data_mining_task")
public class DataMiningTask
{
    /**
     * 挖掘任务的Id
     */
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String taskId;

    /**
     * 任务名称
     */
    @Column(unique = true)
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

    /**
     * 计划开始时间
     */
    private Date plannedStartTime;

    /**
     * 计划完成时间
     */
    private Date plannedFinishTime;

    /**
     * 实际开始时间
     */
    private Date actualStartTime;

    /**
     * 实际完成时间
     */
    private Date actualFinishTime;

    /**
     * 计划持续天数
     */
    @Transient
    private MiningTaskDuration plannedDuration;

    /**
     * 实际持续天数
     */
    @Transient
    private Integer actualDuration;

    @Convert(converter = TaskProgressStatusConverter.class)
    private TaskProgressStatus progressStatus;


    /**
     * 每个任务可被分给多个分组，内容相似
     */
    @JsonIgnore
    @OneToMany(mappedBy = "dataMiningTask",fetch = FetchType.LAZY)
    private Set<DataMiningGroup> groups;


    /**
     * 每个分组可以被分配多个数据集
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_data_set_rel", joinColumns = @JoinColumn(name = "taskId",
            referencedColumnName = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "collectionId",
            referencedColumnName = "collectionId"))
    private Set<DataSetCollection> arrangedCollections;

    /**
     * 每个数据任务可以采用多种算法
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_algorithm_rel",
            joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "algorithmId",
                    referencedColumnName = "algorithmId"))
    private Set<Algorithm> algorithms;


    /**
     * 每个数据任务可以采用多种算法
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_grammar_rel",
            joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "grammarId",
                    referencedColumnName = "grammarId"))
    private Set<MiningGrammar> grammars;

    /**
     * 每个任务可有多个阶段
     */
    @OneToMany(mappedBy = "task",fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @org.hibernate.annotations.OrderBy(clause = "orderId asc")
    private Set<MiningTaskStage> stages;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataMiningTask that = (DataMiningTask) o;

        return Objects.equal(this.taskId, that.taskId) &&
                Objects.equal(this.taskName, that.taskName) &&
                Objects.equal(this.arrangementId, that.arrangementId) &&
                Objects.equal(this.type, that.type) &&
                Objects.equal(this.taskDescription, that.taskDescription) &&
                Objects.equal(this.builtTime, that.builtTime) &&
                Objects.equal(this.plannedStartTime, that.plannedStartTime) &&
                Objects.equal(this.plannedFinishTime, that.plannedFinishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId, taskName, arrangementId, type, taskDescription, builtTime,
                plannedStartTime, plannedFinishTime);
    }

    @Override
    public String toString() {
        return "DataMiningTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", arrangementId='" + arrangementId + '\'' +
                ", type='" + type + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", builtTime=" + builtTime +
                ", plannedStartTime=" + plannedStartTime +
                ", plannedFinishTime=" + plannedFinishTime +
                '}';
    }
}

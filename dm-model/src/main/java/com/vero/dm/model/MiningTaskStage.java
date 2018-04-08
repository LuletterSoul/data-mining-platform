package com.vero.dm.model;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:34 2018/4/8.
 * @since data-mining-platform
 */

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Objects;

import lombok.Data;


/**
 * 挖掘任务的每个阶段 每个阶段小组都需要提交一个发掘结果 每个阶段由管理员动态添加
 */
@Entity
@Table(name = "task_stage", schema = "")
@Data
public class MiningTaskStage
{

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer stageId;

    /**
     * 第几阶段
     */
    private Integer orderId;

    /**
     * 每个阶段只对应一个任务
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "taskId", foreignKey = @ForeignKey(name = "TASK_FK"))
    private DataMiningTask task;

    /**
     * 每个阶段接收来自不同分组的发掘任务
     */
    @JsonIgnore
    @OneToMany(mappedBy = "stage")
    private Set<MiningResult> results;

    /**
     * 阶段备注
     */
    private String comment;

    /**
     * 期限
     */
    @Transient
    private Date[] deadline;


    private Date begin;

    private Date end;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MiningTaskStage that = (MiningTaskStage)o;
        return Objects.equal(stageId, that.stageId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), stageId);
    }
}

package com.vero.dm.model;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:34 2018/4/8.
 * @since data-mining-platform
 */

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * 挖掘任务的每个阶段
 * 每个阶段小组都需要提交一个发掘结果
 * 每个阶段由管理员动态添加
 */
@Entity
@Table(name = "task_stage",schema = "")
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
    @ManyToOne
    @JoinColumn(name = "taskId", foreignKey = @ForeignKey(name = "TASK_FK"))
    private DataMiningTask task;

    /**
     * 每个阶段接收来自不同分组的发掘任务
     */
    @OneToMany(mappedBy = "stage")
    private Set<MiningResult> results;


    /**
     * 阶段备注
     */
    private String comment;

}

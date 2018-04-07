package com.vero.dm.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:53 2018/3/29.
 * @since data-mining-platform
 */
@Entity
@Table(name = "mining_task_result",schema = "")
@Data
public class MiningResult
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer resultId;


    /**
     * 提交结果的分组
     */
    @ManyToOne
    @JoinColumn(name = "groupId", foreignKey = @ForeignKey(name = "GROUP_FK"))
    private DataMiningGroup group;


    /**
     * 结果对应的阶段
     */
    @ManyToOne
    @JoinColumn(name = "stageId", foreignKey = @ForeignKey(name = "STAGE_FK"))
    private MiningTaskStage stage;

    /**
     * 备注
     */
    private String comment;

    /**
     * 数据挖掘的分析结果的文件路径
     */
    private String path;
}

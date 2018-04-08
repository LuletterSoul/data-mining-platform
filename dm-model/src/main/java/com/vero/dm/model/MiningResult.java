package com.vero.dm.model;

import com.google.common.base.Objects;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
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
     * 一个发掘结果可能使用了多个算法
     */
    @ManyToMany
    @JoinTable(name = "result_algor_rel",
            joinColumns = @JoinColumn(name = "resultId"),
            inverseJoinColumns = @JoinColumn(name = "typeId"))
    private Set<AlgorithmType> algorithmTypes;

    /**
     * 备注
     */
    private String comment;


    /**
     * 提交时间
     */
    private Date submittedDate;

    /**
     * 数据挖掘的分析结果的文件路径
     */
    private String path;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MiningResult that = (MiningResult) o;
        return Objects.equal(resultId, that.resultId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), resultId);
    }
}

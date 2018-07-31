package com.vero.dm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.vero.dm.exception.auth.ExpiredCredentialsException;
import com.vero.dm.model.converter.ResultStateConverter;
import com.vero.dm.model.enums.ResultState;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:53 2018/3/29.
 * @since data-mining-platform
 */
@Entity
@Table(name = "result_info",schema = "")
@Data
public class MiningResult
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer resultId;


//    /**
//     * 提交结果的分组
//     */
//    @ManyToOne
//    @JoinColumn(name = "groupId", foreignKey = @ForeignKey(name = "GROUP_FK"))
//    private DataMiningGroup group;


    @Convert(converter = ResultStateConverter.class)
    private ResultState state;



    /**
     * 提交结果的学生
     */
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "submitterId", foreignKey = @ForeignKey(name = "SUBMITTER_FK"))
    private Student submitter;


    /**
     * 结果对应的阶段
     */
    @ManyToOne
    @JoinColumn(name = "stageId", foreignKey = @ForeignKey(name = "STAGE_FK"))
    private MiningTaskStage stage;



    /**
     * 一个发掘结果可能使用了多个算法
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "result_algor_rel",
            joinColumns = @JoinColumn(name = "resultId"),
            inverseJoinColumns = @JoinColumn(name = "typeId"))
    private Set<AlgorithmType> algorithmTypes;



    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @OrderBy(clause = "recordId desc")
    @JoinTable(name = "result_record_rel",
            joinColumns = @JoinColumn(name = "resultId"),
            inverseJoinColumns = @JoinColumn(name = "recordId"))
    private Set<ResultRecord> records;

//    /**
//     * 备注
//     */
//    private String comment;
//
//
//    /**
//     * 提交时间
//     */
//    private Date submittedDate;
//
//
//    /**
//     * 数据挖掘的分析结果的文件路径
//     */
//    private String path;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MiningResult that = (MiningResult) o;

        return Objects.equal(this.resultId, that.resultId) &&
                Objects.equal(this.submitter, that.submitter) &&
                Objects.equal(this.stage, that.stage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(resultId, submitter, stage);
    }
}

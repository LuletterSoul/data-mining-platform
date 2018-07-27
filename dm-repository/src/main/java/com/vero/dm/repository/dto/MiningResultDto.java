package com.vero.dm.repository.dto;

import com.vero.dm.model.AlgorithmType;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.Set;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  22:34 2018/7/24.
 * @since data-mining-platform
 */

public class MiningResultDto {


    private Integer resultId;

//    /**
//     * 提交结果的分组
//     */
//    @ManyToOne
//    @JoinColumn(name = "groupId", foreignKey = @ForeignKey(name = "GROUP_FK"))
//    private DataMiningGroup group;


    private String submitterId;



    private Integer stageId;


    /**
     * 一个发掘结果可能使用了多个算法
     */

    private Set<AlgorithmType> algorithmTypes;

    /**
     * 备注
     */
    private String comment;


    /**
     * 提交时间
     */
    private Date submittedDate;
}

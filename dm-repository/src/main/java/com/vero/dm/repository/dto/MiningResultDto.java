package com.vero.dm.repository.dto;

import com.vero.dm.model.AlgorithmType;
import com.vero.dm.model.MiningResult;
import com.vero.dm.model.MiningTaskStage;
import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.enums.ResultState;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  22:34 2018/7/24.
 * @since data-mining-platform
 */

@Data
public class MiningResultDto {

    private Integer resultId;

    private String submitterId;

    private String submitterName;

    private Integer stageId;

    private ResultState state;

    private MiningTaskStage stage;

    private Set<ResultRecord> records;
    /**
     * 一个发掘结果可能使用了多个算法
     */

    private Set<AlgorithmType> algorithmTypes;

    /**
     * 备注
     */
    private String comment;


    public static MiningResultDto build(MiningResult result) {
        MiningResultDto resultDto = new MiningResultDto();
        BeanUtils.copyProperties(result, resultDto);
        resultDto.setSubmitterId(result.getSubmitter().getStudentId());
        resultDto.setSubmitterName(result.getSubmitter().getStudentName());
        return resultDto;
    }

    public static List<MiningResultDto> build(List<MiningResult> results) {
        List<MiningResultDto> dtos = new ArrayList<>();
        results.forEach(r -> dtos.add(build(r)));
        return dtos;
    }

}

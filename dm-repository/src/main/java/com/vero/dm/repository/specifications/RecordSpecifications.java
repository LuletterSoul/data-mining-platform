package com.vero.dm.repository.specifications;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Predicate;

import com.vero.dm.model.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vero.dm.model.enums.ResultState;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:57 2018/7/25.
 * @since data-mining-platform
 */

public class RecordSpecifications
{
    public static Specification<ResultRecord> recordsSpec(Integer stageId, String submitterId,
                                                          ResultState state)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            MiningTaskStage stage = new MiningTaskStage();
            stage.setStageId(stageId);
            totalPredicates.add(cb.equal(root.get(MiningResult_.STAGE), stage));
            if (!StringUtils.isEmpty(submitterId))
            {
                Student student = new Student();
                student.setUserId(submitterId);
                Predicate taskNamePredicate = cb.equal(root.get(MiningResult_.SUBMITTER), student);
                totalPredicates.add(taskNamePredicate);
            }
            if (!Objects.isNull(state))
            {
                Predicate statePredicate = cb.equal(root.get(MiningResult_.STATE), state);
                totalPredicates.add(statePredicate);
            }
            query.where(cb.and(totalPredicates.toArray(new Predicate[totalPredicates.size()])));
            return query.getRestriction();
        };
    }


}

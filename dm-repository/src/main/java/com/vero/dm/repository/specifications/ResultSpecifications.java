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

public class ResultSpecifications
{
    public static Specification<MiningResult> resultsSpec(Integer stageId, String submitterId,
                                                          ResultState state)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            totalPredicates.add(cb.equal(root.get(MiningResult_.STAGE).get(MiningTaskStage_.STAGE_ID), stageId));
            if (!StringUtils.isEmpty(submitterId))
            {
                Predicate p1 = root.get(MiningResult_.SUBMITTER).get(Student_.USER_ID).in(
                    submitterId);
                totalPredicates.add(p1);
            }
            if (!Objects.isNull(state))
            {
                Predicate p2 = cb.equal(root.get(MiningResult_.state), state);
                totalPredicates.add(p2);
            }
            query.where(cb.and(SpecificationUtil.toPredicates(totalPredicates)));
            return query.getRestriction();
        };
    }
}

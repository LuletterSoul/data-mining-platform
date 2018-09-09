package com.vero.dm.repository.specifications;


import static com.vero.dm.repository.specifications.SpecificationUtil.toPredicates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.ResultState;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 10:57 2018/7/25.
 * @since data-mining-platform
 */

public class ResultSpecifications
{
    public static Specification<MiningResult>  resultsSpec(String taskId, Integer stageId,
                                                           List<String> submitterIds, ResultState state)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            Join<MiningResult,MiningTaskStage> rtJoin = root.join(MiningResult_.STAGE,
                    JoinType.LEFT);
            Join<MiningTaskStage, DataMiningTask> stJoin = rtJoin.join(MiningTaskStage_.TASK, JoinType.LEFT);
            if (!Objects.isNull(stageId))
            {
                totalPredicates.add(cb.equal(
                        rtJoin.get(MiningTaskStage_.STAGE_ID), stageId));
            }
            if (!StringUtils.isEmpty(taskId))
            {
                Predicate p = cb.equal(
                    stJoin.get(DataMiningTask_.TASK_ID), taskId);
                totalPredicates.add(p);
            }
            if (!Objects.isNull(submitterIds)&&!CollectionUtils.isEmpty(submitterIds))
            {
                Predicate p = root.get(MiningResult_.SUBMITTER).get(Student_.USER_ID).in(submitterIds);
                totalPredicates.add(p);
            }
            if (!Objects.isNull(state))
            {
                Predicate p = cb.equal(root.get(MiningResult_.state), state);
                totalPredicates.add(p);
            }
            query.where(cb.and(toPredicates(totalPredicates)));
            return query.getRestriction();
        };
    }
}

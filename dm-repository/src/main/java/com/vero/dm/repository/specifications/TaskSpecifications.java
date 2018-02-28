package com.vero.dm.repository.specifications;


import javax.persistence.criteria.Predicate;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.MiningTaskStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:18 2018/2/21.
 * @since data-mining-platform
 */

public class TaskSpecifications
{
    public static Specification<DataMiningTask> tasksSpec(String taskName,
                                                          Date plannedBeginDate,
                                                          Date plannedEndDate,
                                                          Date builtTimeBegin,
                                                          Date builtTimeEnd,
                                                          MiningTaskStatus taskStatus)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            Predicate taskNamePredicate = cb.equal(root.get(DataMiningTask_.TASK_NAME), taskName);
            Predicate beginDatePredicate = cb.greaterThanOrEqualTo(root.get(DataMiningTask_.PLANNED_START_TIME), plannedBeginDate);
            Predicate endDatePredicate = cb.lessThanOrEqualTo(root.get(DataMiningTask_.PLANNED_FINISH_TIME), plannedEndDate);
            Predicate builtTimeBeginPredicate = cb.greaterThanOrEqualTo(root.get(DataMiningTask_.BUILT_TIME), builtTimeBegin);
            Predicate builtTimeEndPredicate = cb.lessThanOrEqualTo(root.get(DataMiningTask_.BUILT_TIME), builtTimeEnd);
            Predicate statusPredicate = cb.equal(root.get(DataMiningTask_.STATUS), taskStatus);
            if (!StringUtils.isEmpty(taskName)) {
                totalPredicates.add(taskNamePredicate);
            }
            if (!ObjectUtils.isEmpty(plannedBeginDate)) {
                totalPredicates.add(beginDatePredicate);
            }
            if (!ObjectUtils.isEmpty(plannedEndDate)) {
                totalPredicates.add(endDatePredicate);
            }
            if (!ObjectUtils.isEmpty(builtTimeBegin)) {
                totalPredicates.add(builtTimeBeginPredicate);
            }
            if (!ObjectUtils.isEmpty(builtTimeEnd)) {
                totalPredicates.add(builtTimeEndPredicate);
            }
            if (!ObjectUtils.isEmpty(taskStatus)) {
                totalPredicates.add(statusPredicate);
            }
            query.where(cb.and(totalPredicates.toArray(new Predicate[totalPredicates.size()])));
            return query.getRestriction();
        };
    }
}

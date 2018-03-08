package com.vero.dm.repository.specifications;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.DataMiningTask_;
import com.vero.dm.model.enums.TaskProgressStatus;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:18 2018/2/21.
 * @since data-mining-platform
 */

public class TaskSpecifications
{
    public static Specification<DataMiningTask> tasksSpec(String taskName, Date plannedBeginDate,
                                                          Date plannedEndDate, Date builtTimeBegin,
                                                          Date builtTimeEnd,
                                                          TaskProgressStatus progressStatus)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            if (!StringUtils.isEmpty(taskName))
            {
                Predicate taskNamePredicate = cb.equal(root.get(DataMiningTask_.TASK_NAME), taskName);
                totalPredicates.add(taskNamePredicate);
            }
            if (!ObjectUtils.isEmpty(plannedBeginDate))
            {
                Predicate beginDatePredicate = cb.greaterThanOrEqualTo(
                        root.get(DataMiningTask_.PLANNED_START_TIME), plannedBeginDate);
                totalPredicates.add(beginDatePredicate);
            }
            if (!ObjectUtils.isEmpty(plannedEndDate))
            {
                Predicate endDatePredicate = cb.lessThanOrEqualTo(
                        root.get(DataMiningTask_.PLANNED_FINISH_TIME), plannedEndDate);
                totalPredicates.add(endDatePredicate);
            }
            if (!ObjectUtils.isEmpty(builtTimeBegin))
            {
                Predicate builtTimeBeginPredicate = cb.greaterThanOrEqualTo(
                        root.get(DataMiningTask_.BUILT_TIME), builtTimeBegin);
                totalPredicates.add(builtTimeBeginPredicate);
            }
            if (!ObjectUtils.isEmpty(builtTimeEnd))
            {
                Predicate builtTimeEndPredicate = cb.lessThanOrEqualTo(
                        root.get(DataMiningTask_.BUILT_TIME), builtTimeEnd);
                totalPredicates.add(builtTimeEndPredicate);
            }
            if (!ObjectUtils.isEmpty(progressStatus))
            {
                Predicate taskStatusPredicate = cb.equal(root.get(DataMiningTask_.PROGRESS_STATUS),
                        progressStatus);
                totalPredicates.add(taskStatusPredicate);
            }
            query.where(cb.and(totalPredicates.toArray(new Predicate[totalPredicates.size()])));
            return query.getRestriction();
        };
    }
}

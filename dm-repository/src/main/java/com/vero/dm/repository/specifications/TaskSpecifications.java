package com.vero.dm.repository.specifications;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.JoinColumn;
import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.model.*;
import com.vero.dm.model.enums.ResultState;
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
                                                          TaskProgressStatus progressStatus,
                                                          String studentId)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            if (!StringUtils.isEmpty(taskName))
            {
                Predicate taskNamePredicate = cb.equal(root.get(DataMiningTask_.TASK_NAME),
                    taskName);
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
            if (!StringUtils.isEmpty(studentId)) {
                Join<DataMiningTask, DataMiningGroup> tgJoin = root.join(DataMiningTask_.GROUPS,JoinType.LEFT);
                Join<DataMiningGroup, Student> gsJoin = tgJoin.join(DataMiningGroup_.GROUP_MEMBERS, JoinType.LEFT);
                Predicate p = cb.equal(gsJoin.get(Student_.studentId), studentId);
                totalPredicates.add(p);
            }
            if (!ObjectUtils.isEmpty(progressStatus))
            {
                Predicate taskStatusPredicate = cb.equal(root.get(DataMiningTask_.PROGRESS_STATUS),
                    progressStatus);
                totalPredicates.add(taskStatusPredicate);
            }
            query.where(cb.and(SpecificationUtil.toPredicates(totalPredicates)));
            return query.getRestriction();
        };
    }

//    public static Specification<ResultRecord> recordsSpec(String taskId,
//                                                          List<String> submitterIds,
//                                                          ResultState state, boolean all,
//                                                          Integer stageId, boolean newest)
//    {
//        return (root, query, cb) -> {
//            List<Predicate> totalPredicates = new ArrayList<>();
//            Join<ResultRecord, MiningResult> resultRecordJoin = root.join(
//                    ResultRecord_.RESULT, JoinType.LEFT);
//            Join<MiningResult,MiningTaskStage> stageResultJoin = resultRecordJoin.join(
//                    MiningResult_.STAGE, JoinType.LEFT);
//            Join<MiningTaskStage, DataMiningTask> taskStageJoin =stageResultJoin.join(MiningTaskStage_.TASK);
//            totalPredicates.add(cb.equal(taskStageJoin.get(DataMiningTask_.TASK_ID), taskId));
//            if (all)
//            {
//                return select(query, cb, totalPredicates);
//            }
//            if (!Objects.isNull(submitterIds) && !submitterIds.isEmpty())
//            {
//                Predicate p1 = stageResultJoin.get(MiningResult_.SUBMITTER).get(
//                    Student_.USER_ID).in(submitterIds);
//                totalPredicates.add(p1);
//            }
//            if (!Objects.isNull(state))
//            {
//                Predicate p2 = cb.equal(stageResultJoin.get(MiningResult_.STATE), state);
//                totalPredicates.add(p2);
//            }
//            if (!Objects.isNull(stageId))
//            {
//                Predicate p3 = cb.equal(stageResultJoin.get(MiningTaskStage_.STAGE_ID), stageId);
//                totalPredicates.add(p3);
//            }
//            return select(query, cb, totalPredicates);
//        };
//    }

    private static Predicate select(CriteriaQuery<?> query, CriteriaBuilder cb,
                                    List<Predicate> totalPredicates)
    {
        query.where(cb.and(SpecificationUtil.toPredicates(totalPredicates)));
        query.from(ResultRecord.class);
        return query.getRestriction();
    }
}

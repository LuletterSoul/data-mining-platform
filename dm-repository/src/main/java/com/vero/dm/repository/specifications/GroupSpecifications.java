package com.vero.dm.repository.specifications;

import com.vero.dm.model.DataMiningGroup;
import com.vero.dm.model.DataMiningGroup_;
import com.vero.dm.model.DataMiningTask_;
import com.vero.dm.model.Student_;
import com.vero.dm.model.enums.MiningTaskStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  11:48 2018/3/7.
 * @since data-mining-platform
 */

public class GroupSpecifications {
    public static Specification<DataMiningGroup> groupSpec(String groupName,
                                                           Date beginDate, Date endDate,
                                                           String leaderStudentId,
                                                           MiningTaskStatus taskStatus)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates = new ArrayList<>();
            Predicate groupNamePredicate = cb.equal(root.get(DataMiningGroup_.GROUP_NAME), groupName);
            Predicate builtTimeBeginPredicate = cb.greaterThanOrEqualTo(root.get(DataMiningGroup_.BUILT_TIME), beginDate);
            Predicate builtTimeEndPredicate = cb.lessThanOrEqualTo(root.get(DataMiningGroup_.BUILT_TIME), endDate);
            Predicate statusPredicate = cb.equal(root.get(DataMiningGroup_.TASK_STATUS), taskStatus);
            Predicate leaderPredicate = cb.equal(root.get(DataMiningGroup_.GROUP_LEADER).get(Student_.STUDENT_ID), leaderStudentId);
            if (!StringUtils.isEmpty(groupName)) {
                totalPredicates.add(groupNamePredicate);
            }
            if (!ObjectUtils.isEmpty(beginDate)) {
                totalPredicates.add(builtTimeBeginPredicate);
            }
            if (!ObjectUtils.isEmpty(endDate)) {
                totalPredicates.add(builtTimeEndPredicate);
            }
            if (!ObjectUtils.isEmpty(leaderStudentId)) {
                totalPredicates.add(leaderPredicate);
            }
            if (!ObjectUtils.isEmpty(taskStatus)) {
                totalPredicates.add(statusPredicate);
            }
            query.where(cb.and(totalPredicates.toArray(new Predicate[totalPredicates.size()])));
            return query.getRestriction();
        };
    }
}

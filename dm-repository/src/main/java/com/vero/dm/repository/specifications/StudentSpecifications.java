package com.vero.dm.repository.specifications;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.*;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.model.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:47 2018/2/8.
 * @since data-mining-platform
 */

public class StudentSpecifications
{
    public static Specification<Student> findStudentsWithParams(String className,
                                                                String profession, String grade,
                                                                String studentIdPrefix,
                                                                String studentName)
    {
        return (root, query, builder) -> buildStudentMultipleParamsPredicate(className, profession,
            grade, studentIdPrefix, studentName, root, query, builder);
    }

    private static Predicate buildStudentMultipleParamsPredicate(String className,
                                                                 String profession, String grade,
                                                                 String studentIdPrefix,
                                                                 String studentName,
                                                                 Root<Student> root,
                                                                 CriteriaQuery<?> query,
                                                                 CriteriaBuilder builder)
    {
        Predicate classNamePredicate = builder.equal(root.get(Student_.CLASS_NAME), className);
        Predicate professionPredicate = builder.equal(root.get(Student_.PROFESSION), profession);
        Predicate gradePredicate = builder.equal(root.get(Student_.GRADE), grade);
        Predicate studentIdsPrefixPredicate = builder.like(root.get(Student_.studentId),
            "%" + studentIdPrefix + "%");
        Predicate studentNamePredicate = builder.like(root.get(Student_.STUDENT_NAME),
            "%" + studentName + "%");
        List<Predicate> totalPredicates = new ArrayList<>();
        if (!StringUtils.isEmpty(className))
        {
            totalPredicates.add(classNamePredicate);
        }
        if (!StringUtils.isEmpty(profession))
        {
            totalPredicates.add(professionPredicate);
        }
        if (!StringUtils.isEmpty(grade))
        {
            totalPredicates.add(gradePredicate);
        }
        if (!StringUtils.isEmpty(studentIdPrefix))
        {
            totalPredicates.add(studentIdsPrefixPredicate);
        }
        if (!StringUtils.isEmpty(studentName))
        {
            totalPredicates.add(studentNamePredicate);
        }
        query.where(builder.and(totalPredicates.toArray(new Predicate[totalPredicates.size()])));
        return query.getRestriction();
    }

    public static Specification<Student> findStudentsWithoutGroup(String className,
                                                                  String profession, String grade,
                                                                  String studentIdPrefix,
                                                                  String studentName,
                                                                  Date beginDate, Date endDate)
    {
        return (root, query, cb) -> {
            Predicate andPredicate = buildStudentMultipleParamsPredicate(className, profession,
                grade, studentIdPrefix, studentName, root, query, cb);
            if (!ObjectUtils.isEmpty(beginDate) && !ObjectUtils.isEmpty(endDate))
            {
                Subquery<Student> subQuery = query.subquery(Student.class);
                Root<Student> subRoot = subQuery.from(Student.class);
                Join<DataMiningGroup, DataMiningTask> join = subRoot.join(Student_.miningGroups,
                    JoinType.LEFT).join(DataMiningGroup_.dataMiningTask, JoinType.LEFT);
                Predicate dateGq = cb.greaterThanOrEqualTo(
                    join.get(DataMiningTask_.PLANNED_START_TIME), beginDate);
                Predicate dateLq = cb.lessThanOrEqualTo(
                    join.get(DataMiningTask_.PLANNED_FINISH_TIME), endDate);
                subQuery.where(cb.and(dateGq, dateLq));
                Predicate notIn = cb.not(cb.exists(subQuery));
                query.where(andPredicate, notIn);
            }
            else{
                query.where(andPredicate);
            }
            return query.getRestriction();
        };

    }

}

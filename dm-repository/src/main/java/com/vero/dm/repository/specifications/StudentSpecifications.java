package com.vero.dm.repository.specifications;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.*;

import com.vero.dm.model.enums.MiningTaskStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.vero.dm.model.*;

import static com.vero.dm.repository.specifications.SpecificationUtil.*;


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
        List<Predicate> totalPredicates = buildStudentPredicates(className, profession, grade, studentIdPrefix, studentName, root, builder);
        query.where(builder.and(toPredicates(totalPredicates)));
        return query.getRestriction();
    }



    private static List<Predicate> buildStudentPredicates(String className, String profession, String grade, String studentIdPrefix, String studentName, Root<Student> root, CriteriaBuilder builder) {
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
        return totalPredicates;
    }

    /**
     * 获取规定时间段没有发掘任务在身的学生
     * @param className
     * @param profession
     * @param grade
     * @param studentIdPrefix
     * @param studentName
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Specification<Student> findLeisureStudents(String className,
                                                             String profession, String grade,
                                                             String studentIdPrefix,
                                                             String studentName,
                                                             Date beginDate, Date endDate)
    {
        return (root, query, cb) -> {
            List<Predicate> totalPredicates  = buildStudentPredicates(className, profession,
                grade, studentIdPrefix, studentName, root, cb);
            if (!ObjectUtils.isEmpty(beginDate) && !ObjectUtils.isEmpty(endDate))
            {
                Subquery<String> subQuery = query.subquery(String.class);
                Root<Student> subRoot = subQuery.from(Student.class);
                Join<Student, DataMiningGroup> groupJoin = subRoot.join(Student_.miningGroups, JoinType.LEFT);
                Join<DataMiningGroup, DataMiningTask> taskJoin = groupJoin.join(DataMiningGroup_.dataMiningTask, JoinType.LEFT);
                Predicate dateGq = cb.greaterThanOrEqualTo(
                        taskJoin.get(DataMiningTask_.PLANNED_START_TIME), beginDate);
                Predicate dateLq = cb.lessThanOrEqualTo(taskJoin.get(DataMiningTask_.PLANNED_FINISH_TIME), endDate);
                //已完成任务的学生也视为空闲
                Predicate taskStatus = cb.notEqual(groupJoin.get(DataMiningGroup_.TASK_STATUS), MiningTaskStatus.completed);
                subQuery.where(cb.and(dateGq, dateLq, taskStatus));
                //注意子查询不能脱离父根使用,否则抛别名匹配错误的异常
                subQuery.select(subRoot.get(Student_.studentId));
                Predicate notIn = cb.not(cb.in(root.get(Student_.studentId)).value(subQuery));
                totalPredicates.add(notIn);
//                return subQuery.getRestriction();
            }
            query.where(toPredicates(totalPredicates));
            return query.getRestriction();
        };

    }

}

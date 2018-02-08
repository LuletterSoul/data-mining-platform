package com.vero.dm.repository.specifications;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vero.dm.model.Student;
import com.vero.dm.model.Student_;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 21:47 2018/2/8.
 * @since data-mining-platform
 */

public class StudentSpecifications
{
    public static Specification<Student> findStudentsByMultipleParams(String className,
                                                                      String profession,
                                                                      String grade,
                                                                      String studentIdPrefix,
                                                                      String studentName)
    {
        return (root, query, builder) -> {
            Predicate classNamePredicate = builder.equal(root.get(Student_.CLASS_NAME), className);
            Predicate professionPredicate = builder.equal(root.get(Student_.PROFESSION),
                profession);
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
            query.where(
                builder.and(totalPredicates.toArray(new Predicate[totalPredicates.size()])));
            return query.getRestriction();
        };
    }

}

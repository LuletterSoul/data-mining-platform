package com.vero.dm.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vero.dm.model.Student;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:18 2018/1/24.
 * @since data-mining-platform
 */

public interface StudentJpaRepository extends JpaRepository<Student, String>
{

    @Query(value = "select s2 from Student s2 where s2 not in (select s from Student s left join s.miningGroups g left join g.dataMiningTask t where t.plannedStartTime >= :beginDate and t.plannedFinishTime<= :endDate )")
    List<Student> fetchStudentWithoutGroup(@Param("beginDate") Date beginDate,
                                           @Param("endDate") Date endDate);

    @Query(value = "select s.userId from Student s")
    List<String> findAllStudentUserId();
}

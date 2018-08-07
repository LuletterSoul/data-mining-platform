package com.vero.dm.repository;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vero.dm.model.Student;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:18 2018/1/24.
 * @since data-mining-platform
 */

public interface StudentJpaRepository extends JpaRepository<Student, String>,JpaSpecificationExecutor<Student>
{
    Student findByUserId(String userId);

    Student findByUsername(String username);

    @Query(value = "SELECT s2 FROM Student s2 where s2 not in (select s from Student s left join s.miningGroups g left join g.dataMiningTask t where t.plannedStartTime >= :beginDate and t.plannedFinishTime<= :endDate )")
    List<Student> fetchStudentWithoutGroup(@Param("beginDate") Date beginDate,
                                           @Param("endDate") Date endDate);

    @Query(value = "SELECT s.userId from Student s")
    List<String> findAllStudentUserId();

    @Query(value = "SELECT s.studentId from Student s")
    List<String> findAllStudentIds();


    @Query(value = "SELECT s from Student s where s.studentId in :studentIds")
    List<Student> findByStudentIds(@Param("studentIds") List<String> studentIds);

    Student findByStudentId(String studentId);

    @Modifying
    @Query(value = "DELETE FROM Student s WHERE s.studentId in :studentIds")
    int deleteBatchStudentsById(@Param("studentIds") List<String> studentIds);

    @Query(value = "SELECT distinct s.className from Student s")
    List<String> findClassNameOptions();

    @Query(value = "SELECT distinct s.profession from Student s")
    List<String> findProfessionOptions();

    @Query(value = "SELECT distinct s.grade from Student s")
    List<String> findGradeOptions();

}

package com.vero.dm.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vero.dm.model.DataMiningTask;
import com.vero.dm.model.Student;
import com.vero.dm.model.enums.ResultState;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:23 2018/1/24.
 * @since data-mining-platform
 */

public interface DataMiningTaskJpaRepository extends JpaRepository<DataMiningTask, String>, JpaSpecificationExecutor<DataMiningTask>
{

    @Query(value = "SELECT max(t.groups.size) from DataMiningTask t")
    int findMaxGroupNum();

    @Query(value = "SELECT min(t.groups.size) from DataMiningTask t")
    int findMinGroupNum();

    @Query(value = "SELECT  t.taskName from DataMiningTask t")
    List<String> findAllTaskNames();

    @Query(value = "SELECT t from DataMiningTask t where t.groups.size  >=:lowBound and t.groups.size <=:upperBound")
    List<DataMiningTask> findByLinkedGroupsBound(@Param("lowBound") Integer lowBound,
                                                 @Param("upperBound") Integer upperBound);

    /**
     * 获取特定状态的学生
     */
    @Query(value = "SELECT tsre.submitter from DataMiningTask t left join t.stages ts left join ts.results tsre where t.taskId = :taskId and tsre.state = :state")
    List<Student> findSpecializedStateStudents(@Param("taskId") String taskId,
                                               @Param("state") ResultState state);
}

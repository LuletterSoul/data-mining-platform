package com.vero.dm.repository;


import com.vero.dm.model.DataMiningGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vero.dm.model.DataMiningTask;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:23 2018/1/24.
 * @since data-mining-platform
 */

public interface DataMiningTaskJpaRepository extends JpaRepository<DataMiningTask, String>
                                                    ,JpaSpecificationExecutor<DataMiningTask>
{
    @Query(value = "SELECT max(t.groups.size) from DataMiningTask t")
    int findMaxGroupNum();

    @Query(value = "SELECT min(t.groups.size) from DataMiningTask t")
    int findMinGroupNum();

    @Query(value = "SELECT  t.taskName from DataMiningTask t")
    List<String> findAllTaskNames();

    @Query(value = "SELECT t from DataMiningTask t where t.groups.size  >=:lowBound and t.groups.size <=:upperBound")
    List<DataMiningTask> findByLinkedGroupsBound(@Param("lowBound") Integer lowBound,@Param("upperBound")  Integer upperBound);


}

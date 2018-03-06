package com.vero.dm.repository;


import java.util.List;

import com.vero.dm.model.DataSetCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vero.dm.model.DataMiningGroup;
import org.springframework.data.repository.query.Param;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:24 2018/1/24.
 * @since data-mining-platform
 */

public interface DataMiningGroupJpaRepository extends JpaRepository<DataMiningGroup, String>
{
    @Query(value = "select  g from DataMiningGroup g left join g.dataMiningTask t where t.taskId = :taskId")
    List<DataMiningGroup> findByDataMiningTaskId(@Param("taskId") String taskId);

    @Query(value = "select  g.groupName from DataMiningGroup g")
    List<String> findGroupNames();

}

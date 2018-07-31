package com.vero.dm.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vero.dm.model.MiningResult;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:30 2018/7/24.
 * @since data-mining-platform
 */

public interface MiningResultRepository extends JpaRepository<MiningResult, Integer>,JpaSpecificationExecutor<MiningResult>
{
    @Query(value = "select  r from MiningResult r left join r.stage  rs where rs.stageId = :stageId and rs.task.taskId = :taskId")
    List<MiningResult> findByStageIdAndTaskId(@Param("taskId") String taskId, @Param("stageId") Integer stageId);

    @Query(value = "DELETE from MiningResult mr WHERE mr.resultId in (SELECT sr.resultId from DataMiningTask t left join t.stages s left join s.results sr where t.taskId = :taskId)")
    int deleteMiningResultByTaskId(@Param("taskId") String taskId);
}

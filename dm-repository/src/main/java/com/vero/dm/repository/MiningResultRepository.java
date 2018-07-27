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
    @Query(value = "select  r from MiningResult r left join r.stage  where r.stage.stageId = :stageId")
    List<MiningResult> findByStageId(@Param("stageId") Integer stageId);
}

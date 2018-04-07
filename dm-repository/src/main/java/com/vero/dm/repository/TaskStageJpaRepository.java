package com.vero.dm.repository;

import com.vero.dm.model.MiningTaskStage;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vero.dm.model.Algorithm;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  0:51 2018/1/31.
 * @since data-mining-platform
 */

public interface TaskStageJpaRepository extends JpaRepository<MiningTaskStage,Integer>{

}

package com.vero.dm.repository;

import com.vero.dm.model.MiningTaskStage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  22:36 2018/7/24.
 * @since data-mining-platform
 */

public interface StageRepository extends JpaRepository<MiningTaskStage,Integer> {
}

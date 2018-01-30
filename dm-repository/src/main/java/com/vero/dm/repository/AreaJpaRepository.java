package com.vero.dm.repository;

import com.vero.dm.model.AreaType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  20:21 2018/1/29.
 * @since data-mining-platform
 */

public interface AreaJpaRepository extends JpaRepository<AreaType,Integer> {
}

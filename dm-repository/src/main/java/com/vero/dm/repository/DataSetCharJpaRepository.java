package com.vero.dm.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vero.dm.model.DataSetCharacteristic;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 20:49 2018/1/29.
 * @since data-mining-platform
 */

public interface DataSetCharJpaRepository extends JpaRepository<DataSetCharacteristic, Integer>
{}

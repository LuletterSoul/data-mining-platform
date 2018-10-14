package com.vero.dm.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vero.dm.model.DataSetContainer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:24 2018/1/24.
 * @since data-mining-platform
 */

public interface DataSetContainerJpaRepository extends JpaRepository<DataSetContainer, String>,JpaSpecificationExecutor<DataSetContainer>
{
    List<DataSetContainer> findAllByContainerIdIn(List<Integer> containerIds);

    @Modifying
    @Query(value = "DELETE FROM DataSetContainer d WHERE d.containerId in :containerIds")
    int deleteBatchContainersById(@Param("containerIds") List<Integer> containerIds);

    List<DataSetContainer>  findByContainerIdIn(List<String> containerIds);

}

package com.vero.dm.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vero.dm.model.DataSetCollection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:21 2018/1/24.
 * @since data-mining-platform
 */

public interface DataSetCollectionJpaRepository extends JpaRepository<DataSetCollection, String>
{
    @Query(value = "select con.filePath from DataSetCollection c left join c.dataSetContainers con where c.collectionId in :collectionIds")
    List<String> findAllDataSetsFilePaths(@Param("") List<String> collectionIds);

}

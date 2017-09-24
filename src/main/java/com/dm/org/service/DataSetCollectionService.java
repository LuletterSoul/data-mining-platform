package com.dm.org.service;

import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:38 2017/7/6.
 * @description
 * @modified by:
 */
public interface DataSetCollectionService extends BaseService<DataSetCollection, String>
{

    /**
     *
     */
    List<DataSetCollection> getPageableCollection(Pageable pageable);

    /**
     * 为当前数据集集合增加新的数据容器
     *
     * @param collectionId 集合
     * @param containerId  容器
     */
    DataSetContainer addDataSetContainer(String collectionId, DataSetContainer container);

    /**
     * 删除某一容器
     *
     * @param containerId 容器Id
     *
     */
    DataSetContainer removeDataSetContainer(String collectionId,String containerId);

    DataSetCollection getCollectionByName(String collectionName);

    DataSetCollection saveCollection(DataSetCollection collection);

    DataSetCollection deleteByName(String collectionName);

    List<DataSetCollection> deleteBatch(List<String> collectionIds);

    DataSetCollection deleteByCollectionId(String collectionId);

    DataSetCollection updateCollection(DataSetCollection dataSetCollection);

    List<DataSetContainer> getContainers(String collectionId);

    List<String> getCollectionNames();

    DataSetContainer relateContainer(String collectionId, String containerId);
}

package com.dm.org.service;

import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;

import java.util.List;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:47 2017/7/6.
 * @description
 * @modified by:
 */
public interface DataSetContainerService
{
    /**
     * @param collectionId 要更新的分类集合
     * @return 集合ID
     */
    public void setOrUpdateContainerCategorization(String collectionId,String containerId);

    public List<DataSetContainer> fetchDataSetContainers(String collectionId);


}

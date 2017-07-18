package com.dm.org.service;

import com.dm.org.exception.DataObjectNotFoundException;
import com.dm.org.exception.DataSetCollectionNoFoundException;
import com.dm.org.model.DataSetContainer;

import java.io.Serializable;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:38 2017/7/6.
 * @description
 * @modified by:
 */
public interface DataSetCollectionService
{
    /**
     * 为当前数据集集合增加新的数据容器
     *
     * @param collectionId  集合
     *@param  containerId   容器
     */
    public void addDataSetContainer(String collectionId,DataSetContainer container)
            throws DataObjectNotFoundException;

    /**
     * 删除某一容器
     *
     * @param containerId 容器Id
     *
     */
    public void removeDataSetContainer(String collectionId,String containerId);
}

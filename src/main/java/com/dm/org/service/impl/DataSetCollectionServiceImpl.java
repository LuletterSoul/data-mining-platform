package com.dm.org.service.impl;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dm.org.exceptions.DataAccessObjectException;
import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.exceptions.DataSetCollectionNoFoundException;
import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetCollectionService;

import java.util.List;
import java.util.Set;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 1:34 2017/7/6.
 * @description
 * @modified by:
 */
@Service
public class DataSetCollectionServiceImpl extends AbstractBaseServiceImpl<DataSetCollection, String> implements DataSetCollectionService
{
    public DataSetCollection fectchAllDataSetContainer()
    {
        return null;
    }

    public void saveBatchCollection(Set<DataSetCollection> collections) {
        this.saveBatch(collections);
    }

    @Override
    public List<DataSetCollection> getPageableCollection(Pageable pageable) {
        return collectionDao.get(pageable);
    }

    public DataSetContainer addDataSetContainer(String collectionId, DataSetContainer container)
    {
        DataSetCollection collection = findById(collectionId);
        collection.getDataSets().add(container);
        container.setDataSetCollection(collection);
        return container;
    }

    public DataSetContainer removeDataSetContainer(String collectionId, String containerId)
    {
        DataSetContainer container = containerDao.findById(containerId);
        DataSetCollection collection = findById(collectionId);
        collection.getDataSets().remove(container);
        container.setDataSetCollection(null);
        return container;
    }

    @Override
    public DataSetCollection getCollectionByName(String collectionName)
    {
        return collectionDao.getCollectionByName(collectionName);
    }

    @Override
    public DataSetCollection saveCollection(DataSetCollection collection)
    {
        this.save(collection);
        return collection;
    }

    @Override
    public DataSetCollection deleteByName(String collectionName)
    {
        DataSetCollection collection = getCollectionByName(collectionName);
        collectionDao.deleteByName(collectionName);
        return collection;
    }

    @Override
    public DataSetCollection deleteByCollectionId(String collectionId)
    {
        return this.findById(collectionId);
    }

    @Override
    public DataSetCollection updateCollection(DataSetCollection dataSetCollection)
    {
        this.update(dataSetCollection);
        return dataSetCollection;
    }

    @Override
    public List<DataSetContainer> getContainers(String collectionId) {
        return collectionDao.getContainers(collectionId);
    }
    @Override
    public List<String> getCollectionNames() {
        return collectionDao.getSetNames();
    }

    @Override
    public DataSetContainer relateContainer(String collectionId, String containerId) {
        DataSetContainer container = containerDao.findById(containerId);
        return this.addDataSetContainer(collectionId, container);
    }
}

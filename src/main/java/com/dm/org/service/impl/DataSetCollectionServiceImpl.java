package com.dm.org.service.impl;


import com.dm.org.dto.CollectionDTO;
import com.dm.org.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dm.org.exceptions.DataAccessObjectException;
import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.exceptions.DataSetCollectionNoFoundException;
import com.dm.org.service.DataSetCollectionService;

import java.util.*;


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

    public List<DataSetCollection> getCollectionByIds(List<String> collectionIds) {
        return collectionDao.getCollectionByIds(collectionIds);
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
    public DataSetCollection saveCollection(CollectionDTO collectionDTO) {
        List<MiningTaskType> miningTaskTypes = miningTaskTypeDao.getTaskTypes(collectionDTO.getAssociatedTaskIds());
        List<AttributeType> attributeTypes = attributeTypeDao.getAttrTypes(collectionDTO.getAttributeTypeIds());
        List<DataSetCharacteristic> characteristics = collectionCharDao.getCharTypes(collectionDTO.getCharacteristicIds());
        AreaType areaType = areaTypeDao.findById(collectionDTO.getAreaId());
        DataSetCollection collection = new DataSetCollection();
        BeanUtils.copyProperties(collectionDTO, collection);
        collectionDao.save(collection);
        collection.setAssociatedTasks(new LinkedHashSet<MiningTaskType>(miningTaskTypes));
        collection.setAttributeTypes(new LinkedHashSet<AttributeType>(attributeTypes));
        collection.setCharacteristics(new LinkedHashSet<DataSetCharacteristic>(characteristics));
        collection.setArea(areaType);
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
    public List<DataSetCollection> deleteBatch(List<String> collectionIds) {
        List<DataSetCollection> collections = this.getCollectionByIds(collectionIds);
        collectionDao.deleteBatch(collectionIds);
        return collections;
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

    @Override
    public Map<String, List<?>> getOptions() {
        List<AreaType> areaTypeOptions = areaTypeDao.findAll();
        List<DataSetCharacteristic> charOptions = collectionCharDao.findAll();
        List<AttributeType> attributeTypeOptions = attributeTypeDao.findAll();
        List<MiningTaskType> miningTaskTypeOptions = miningTaskTypeDao.findAll();
        Map<String, List<?>> optionsMap = new LinkedHashMap<String, List<?>>();
        optionsMap.put("areaTypeOptions", areaTypeOptions);
        optionsMap.put("charOptions", charOptions);
        optionsMap.put("attributeTypeOptions", attributeTypeOptions);
        optionsMap.put("miningTaskTypeOptions", miningTaskTypeOptions);
        return optionsMap;
    }

}

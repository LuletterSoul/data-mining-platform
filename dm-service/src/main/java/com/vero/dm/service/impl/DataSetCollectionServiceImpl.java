package com.vero.dm.service.impl;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.repository.dto.CollectionDTO;
import com.vero.dm.service.DataSetCollectionService;


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

    public void saveBatchCollection(Set<DataSetCollection> collections)
    {
        // this.saveBatch(collections);
    }

    @Override
    public Page<DataSetCollection> getPageableCollection(Pageable pageable)
    {
        // Integer totalElements = collectionDao.countAll();
        // return new PageImpl<DataSetCollection>(collectionDao.get(pageable), pageable,
        // totalElements);
        return collectionJpaRepository.findAll(pageable);
    }

    public List<DataSetCollection> getCollectionByIds(List<String> collectionIds)
    {
        // return collectionDao.getCollectionByIds(collectionIds);
        return null;
    }

    public DataSetContainer addDataSetContainer(String collectionId, DataSetContainer container)
    {
        // DataSetCollection collection = findById(collectionId);
        ////// collection.getDataSets().add(container);
        ////// container.setDataSetCollection(collection);
        //// return container;
        return null;
    }

    @Override
    public List<DataSetContainer> saveOrUpdateContainers(String collectionId,
                                                         List<String> containerIds)
    {
        // if (containerIds.isEmpty())
        // {
        // return null;
        // }
        // List<DataSetContainer> containers = containerDao.fetchContainers(containerIds);
        // DataSetCollection collection = findById(collectionId);
        // if (collection.getDataSets() == null)
        // {
        // collection.setDataSets(new LinkedHashSet<DataSetContainer>());
        // }
        // collection.getDataSets().addAll(containers);
        // for (DataSetContainer container : containers)
        // {
        // container.setDataSetCollection(collection);
        // containerDao.update(container);
        // }
        // return containers;
        return null;
    }

    public DataSetContainer removeDataSetContainer(String collectionId, String containerId)
    {
        // DataSetContainer container = containerDao.findById(containerId);
        // DataSetCollection collection = findById(collectionId);
        //// collection.getDataSets().remove(container);
        // container.setDataSetCollection(null);
        // return container;
        return null;
    }

    @Override
    public DataSetCollection getCollectionByName(String collectionName)
    {
        // return collectionDao.getCollectionByName(collectionName);
        return null;
    }

    @Override
    public DataSetCollection saveCollection(CollectionDTO collectionDTO)
    {
        // // List<DataSetContainer> containers =
        // // containerDao.fetchContainers(collectionDTO.getContainerIds());
        // DataSetCollection collection = new DataSetCollection();
        // // DTO对象相似属性克隆到真正Entity
        // BeanUtils.copyProperties(collectionDTO, collection);
        // collectionDao.save(collection);
        // // 关联多个以保存到系统中的数据文件
        // this.saveOrUpdateContainers(collection.getCollectionId(),
        // collectionDTO.getContainerIds());
        // // 保存多选值
        // updateCollectionMultipleTypes(collectionDTO, collection);
        // return collection;
        return null;
    }

    private void updateCollectionMultipleTypes(CollectionDTO collectionDTO,
                                               DataSetCollection collection)
    {
        // List<AssociatedTask> associatedTasks = miningTaskTypeDao.getTaskTypes(
        // collectionDTO.getAssociatedTaskIds());
        // List<AttributeType> attributeTypes = attributeTypeDao.getAttrTypes(
        // collectionDTO.getAttributeTypeIds());
        // List<DataSetCharacteristic> characteristics = collectionCharDao.getCharTypes(
        // collectionDTO.getCharacteristicIds());
        // AreaType areaType = areaTypeDao.findById(collectionDTO.getAreaId());
        // collection.setAssociatedTasks(new LinkedHashSet<AssociatedTask>(associatedTasks));
        //// collection.setAttributeTypes(new LinkedHashSet<AttributeType>(attributeTypes));
        //// collection.setCharacteristics(new
        // LinkedHashSet<DataSetCharacteristic>(characteristics));
        // collection.setArea(areaType);
    }

    @Override
    public DataSetCollection deleteByName(String collectionName)
    {
        // DataSetCollection collection = getCollectionByName(collectionName);
        // collectionDao.deleteByName(collectionName);
        // return collection;
        return null;
    }

    @Override
    public List<DataSetCollection> deleteBatch(List<String> collectionIds)
    {
        // List<DataSetCollection> collections = this.getCollectionByIds(collectionIds);
        //// collectionDao.deleteBatch(collectionIds);
        //// return collections;
        return null;
    }

    @Override
    public DataSetCollection deleteByCollectionId(String collectionId)
    {
        // DataSetCollection collection = this.findById(collectionId);
        // this.deleteById(collectionId);
        // return collection;
        return null;
    }

    @Override
    public DataSetCollection updateCollection(CollectionDTO dataSetCollection)
    {
        // DataSetCollection collection = this.findById(dataSetCollection.getCollectionId());
        // BeanUtils.copyProperties(dataSetCollection, collection);
        // updateCollectionMultipleTypes(dataSetCollection, collection);
        // this.update(collection);
        // return collection;
        return null;
    }

    @Override
    public List<DataSetContainer> getContainers(String collectionId)
    {
        // return collectionDao.getContainers(collectionId);
        return null;
    }

    @Override
    public List<String> getCollectionNames()
    {
        // return collectionDao.getSetNames();
        return null;
    }

    @Override
    public DataSetContainer relateContainer(String collectionId, String containerId)
    {
        // DataSetContainer container = containerDao.findById(containerId);
        // return this.addDataSetContainer(collectionId, container);
        return null;
    }

    @Override
    public Map<String, List<?>> getOptions()
    {
        // List<AreaType> areaTypeOptions = areaTypeDao.findAll();
        // List<DataSetCharacteristic> charOptions = collectionCharDao.findAll();
        // List<AttributeType> attributeTypeOptions = attributeTypeDao.findAll();
        // List<AssociatedTask> associatedTaskOptions = miningTaskTypeDao.findAll();
        // Map<String, List<?>> optionsMap = new LinkedHashMap<String, List<?>>();
        // optionsMap.put("areaTypeOptions", areaTypeOptions);
        // optionsMap.put("charOptions", charOptions);
        // optionsMap.put("attributeTypeOptions", attributeTypeOptions);
        // optionsMap.put("miningTaskTypeOptions", associatedTaskOptions);
        // return optionsMap;
        return null;
    }

}

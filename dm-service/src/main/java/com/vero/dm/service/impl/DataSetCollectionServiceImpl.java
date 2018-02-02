package com.vero.dm.service.impl;


import static com.vero.dm.util.PathUtils.concat;
import static com.vero.dm.util.PathUtils.getAbsolutePath;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.vero.dm.repository.dto.CollectionDto;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.model.*;
import com.vero.dm.service.DataSetCollectionService;
import com.vero.dm.service.constant.ResourcePath;
import com.vero.dm.util.PathUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 1:34 2017/7/6.
 * @description
 * @modified by:
 */
@Service
@Slf4j
public class DataSetCollectionServiceImpl extends AbstractBaseServiceImpl<DataSetCollection, String> implements DataSetCollectionService
{

    public DataSetCollection fectchAllDataSetContainer()
    {
        return null;
    }

    @Override
    public DataSetCollection findById(String id)
    {
        return collectionJpaRepository.findOne(id);
    }

    // public List<DataSetCollection> saveBatchCollection(Set<DataSetCollection> collections)
    //// {
    //// return collectionJpaRepository.save(collections);
    //// }

    @Override
    public Page<DataSetCollection> getPageableCollection(Pageable pageable)
    {
        return collectionJpaRepository.findAll(pageable);
    }

    @Override
    public DataSetContainer addDataSetContainer(String collectionId, MultipartFile multipartFile)
    {
        DataSetCollection collection = findById(collectionId);
        String absolutePath = concat(collection.getDataSetFolderPath(),
            multipartFile.getOriginalFilename());
        DataSetContainer container = new DataSetContainer();
        String fileType = handleFileTransfer(multipartFile, absolutePath);
        container.setFileName(multipartFile.getOriginalFilename());
        container.setDataSetCollection(findById(collectionId));
        container.setFilePath(absolutePath);
        container.setFileType(fileType);
        container.setSize(multipartFile.getSize());
        containerJpaRepository.save(container);
        return container;
    }

    private String handleFileTransfer(MultipartFile multipartFile, String absolutePath)
    {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(absolutePath);
            outputStream.write(multipartFile.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // try
        // {
        // FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),
        // new File(absolutePath));
        // }
        // catch (IOException e)
        // {
        // e.printStackTrace();
        // }
        String originalFileName = multipartFile.getOriginalFilename();
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."),
            originalFileName.length());
        log.debug("Uploaded file type is [{}]", fileType);
        return fileType;
    }

    public List<DataSetCollection> findCollectionsByIds(List<String> collectionIds)
    {
        return collectionJpaRepository.findAll(collectionIds);
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

//    public DataSetContainer removeDataSetContainer(String collectionId, String containerId)
//    {
//        DataSetContainer
//        containerJpaRepository.delete(containerId);
//        return null;
//    }

    @Override
    public DataSetCollection getCollectionByName(String collectionName)
    {
        // return collectionDao.getCollectionByName(collectionName);
        return null;
    }

    @Override
    public DataSetCollection saveCollection(CollectionDto collectionDto)
    {
        return collectionJpaRepository.save(convert(collectionDto));
    }

    @Override
    public List<DataSetCollection> saveCollections(List<CollectionDto> collectionDtos)
    {
        List<DataSetCollection> dataSetCollections = new LinkedList<>();
        collectionDtos.forEach(c -> dataSetCollections.add(convert(c)));
        return collectionJpaRepository.save(dataSetCollections);
    }

    private void updateCollectionMultipleTypes(CollectionDto collectionDto,
                                               DataSetCollection collection)
    {
        // List<AssociatedTask> associatedTasks = miningTaskTypeDao.getTaskTypes(
        // collectionDto.getAssociatedTaskIds());
        // List<AttributeType> attributeTypes = attributeTypeDao.getAttrTypes(
        // collectionDto.getAttributeTypeIds());
        // List<DataSetCharacteristic> characteristics = collectionCharDao.getCharTypes(
        // collectionDto.getCharacteristicIds());
        // AreaType areaType = areaTypeDao.findById(collectionDto.getAreaId());
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
        List<DataSetCollection> collections = findCollectionsByIds(collectionIds);
        collectionJpaRepository.delete(findCollectionsByIds(collectionIds));
        return collections;
    }

    @Override
    public DataSetCollection deleteByCollectionId(String collectionId)
    {
        DataSetCollection collection = findById(collectionId);
        collectionJpaRepository.delete(collectionId);
        return collection;
    }

    @Override
    public DataSetCollection updateCollection(DataSetCollection collection)
    {
        return collectionJpaRepository.saveAndFlush(collection);
    }

    // @Override
    // public DataSetCollection updateCollection(CollectionDto dataSetCollection)
    // {
    //// DataSetCollection collection = this.findById(dataSetCollection.getCollectionId());
    //// BeanUtils.copyProperties(dataSetCollection, collection);
    //// updateCollectionMultipleTypes(dataSetCollection, collection);
    //// this.update(collection);
    //// return collection;
    // return null;
    // }

    @Override
    public List<DataSetContainer> getContainers(String collectionId)
    {
        return new ArrayList<>(findById(collectionId).getDataSetContainers());
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

    public DataSetCollection convert(CollectionDto source)
    {
        List<DataSetDescription> descriptions = descriptionJpaRepository.findAll(
            source.getDescriptionIds());
        AreaType areaType = areaJpaRepository.findOne(source.getAreaId());
        List<AttributeCharacteristic> attributeCharacteristics = attributeCharJpaRepository.findAll(
            source.getAttrCharIds());
        List<AssociatedTask> associatedTasks = associatedTaskJpaRepository.findAll(
            source.getAssociatedTaskIds());
        List<DataSetCharacteristic> dataSetCharacteristics = setCharJpaRepository.findAll(
            source.getDataSetCharIds());
        Boolean isValidDtoParams = isValidDtoParams(areaType, attributeCharacteristics,
            associatedTasks, dataSetCharacteristics);
        // if (!isValidDtoParams)
        // {
        // throw new HttpMessageNotReadableException(
        // "The provided request body is not readable!");
        // }
        return convertResult(source, descriptions, areaType, attributeCharacteristics,
            associatedTasks, dataSetCharacteristics);
    }

    private DataSetCollection convertResult(CollectionDto source,
                                            List<DataSetDescription> descriptions,
                                            AreaType areaType,
                                            List<AttributeCharacteristic> attributeCharacteristics,
                                            List<AssociatedTask> associatedTasks,
                                            List<DataSetCharacteristic> dataSetCharacteristics)
    {
        if (source.getIsUpdated())
        {
            return updatedCollectionModel(source);
        }
        else
        {
            return newCollectionModel(source, descriptions, areaType, attributeCharacteristics,
                associatedTasks, dataSetCharacteristics);
        }
    }

    private boolean isValidDtoParams(AreaType areaType,
                                     List<AttributeCharacteristic> attributeCharacteristics,
                                     List<AssociatedTask> associatedTasks,
                                     List<DataSetCharacteristic> dataSetCharacteristics)
    {
        return areaType != null && attributeCharacteristics != null && associatedTasks != null
               && dataSetCharacteristics != null;
    }

    private DataSetCollection newCollectionModel(CollectionDto source,
                                                 List<DataSetDescription> descriptions,
                                                 AreaType areaType,
                                                 List<AttributeCharacteristic> attributeCharacteristics,
                                                 List<AssociatedTask> associatedTasks,
                                                 List<DataSetCharacteristic> dataSetCharacteristics)
    {
        DataSetCollection newCollection = new DataSetCollection();
        BeanUtils.copyProperties(source, newCollection);
        newCollection.setArea(areaType);
        newCollection.setAssociatedTasks(new LinkedHashSet<>(associatedTasks));
        newCollection.setAttributeCharacteristics(new LinkedHashSet<>(attributeCharacteristics));
        // newCollection.setDescriptions(new LinkedHashSet<>(descriptions));
        descriptions.forEach(d -> d.setDataSetCollection(newCollection));
        newCollection.setDataSetCharacteristics(new LinkedHashSet<>(dataSetCharacteristics));
        collectionJpaRepository.save(newCollection);
        String folderPath = concat(ResourcePath.COLLECTION_PATH, newCollection.getCollectionId());
        newCollection.setDataSetFolderPath(PathUtils.getAbsolutePath(folderPath));
        return newCollection;
    }

    private DataSetCollection updatedCollectionModel(CollectionDto source)
    {
        DataSetCollection collection = collectionJpaRepository.findOne(source.getCollectionId());
        BeanUtils.copyProperties(source, collection);
        return collection;
    }

}

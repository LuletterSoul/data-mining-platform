package com.vero.dm.service.impl;


import static com.vero.dm.util.PathUtils.concat;
import static com.vero.dm.util.PathUtils.handleFileTransfer;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.model.*;
import com.vero.dm.repository.dto.CollectionDto;
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
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
        threadPoolTaskExecutor.execute(() -> {
            try {
                multipartFile.transferTo(new File(absolutePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        String originalFileName = multipartFile.getOriginalFilename();
        String fileType = originalFileName.substring(originalFileName.lastIndexOf("."),
                originalFileName.length());
        log.debug("Uploaded file type is [{}]", fileType);
        container.setFileName(multipartFile.getOriginalFilename());
        container.setDataSetCollection(findById(collectionId));
        container.setFilePath(absolutePath);
        container.setFileType(fileType);
        container.setSize(multipartFile.getSize());
        containerJpaRepository.save(container);
        return container;
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

    // public DataSetContainer removeDataSetContainer(String collectionId, String containerId)
    // {
    // DataSetContainer
    // containerJpaRepository.delete(containerId);
    // return null;
    // }

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
//        List<String> setFilePaths = collectionJpaRepository.findAllDataSetsFilePaths(collectionIds);
        collections.forEach( c -> {
            try {
                FileUtils.deleteDirectory(new File(c.getDataSetFolderPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        collectionJpaRepository.delete(collections);
        return collections;
    }

    @Override
    public DataSetCollection deleteByCollectionId(String collectionId)
    {
        DataSetCollection collection = findById(collectionId);
        File dataDir = new File(collection.getDataSetFolderPath());
        try {
            FileUtils.deleteDirectory(dataDir);
            log.info("Delete [{}]:[{}] database index folder.", collection.getCollectionId(),
                    collection.getCollectionName());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        List<AreaType> areaTypeOptions = areaJpaRepository.findAll();
        List<DataSetCharacteristic> dataSetCharOptions = setCharJpaRepository.findAll();
        List<AttributeCharacteristic> attrCharOptions = attributeCharJpaRepository.findAll();
        List<AssociatedTask> associatedTaskOptions = associatedTaskJpaRepository.findAll();
        Map<String, List<?>> optionsMap = new LinkedHashMap<String, List<?>>();
        optionsMap.put("dataSetCharOptions", dataSetCharOptions);
        optionsMap.put("attrCharOptions", attrCharOptions);
        optionsMap.put("associatedTaskOptions", associatedTaskOptions);
        optionsMap.put("areaTypeOptions", areaTypeOptions);
        return optionsMap;
    }

    public DataSetCollection convert(CollectionDto source)
    {
        List<DataSetDescription> descriptions = descriptionJpaRepository.findAll(
            source.getDescriptionIds());
        AreaType areaType = areaJpaRepository.findOne(source.getAreaId());
        List<AttributeCharacteristic> attributeCharacteristics = attributeCharJpaRepository.findAll(
            source.getAttributeCharIds());
        List<AssociatedTask> associatedTasks = associatedTaskJpaRepository.findAll(
            source.getAssociatedTaskIds());
        List<DataSetCharacteristic> dataSetCharacteristics = setCharJpaRepository.findAll(
            source.getDataSetCharIds());
        Boolean isValidDtoParams = isValidDtoParams(areaType, attributeCharacteristics,
            associatedTasks, dataSetCharacteristics);
        if (!isValidDtoParams)
        {
            throw new HttpMessageNotReadableException(
                "The provided request body is not readable!");
        }
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

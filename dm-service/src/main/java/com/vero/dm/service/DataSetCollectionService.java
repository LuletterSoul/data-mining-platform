package com.vero.dm.service;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.repository.dto.CollectionDto;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:38 2017/7/6.
 * @description
 * @modified by:
 */
public interface DataSetCollectionService extends BaseService<DataSetCollection, String>
{


    Page<DataSetCollection> getPageableCollection(Pageable pageable);


    DataSetContainer addDataSetContainer(String collectionId, MultipartFile multipartFile);

    List<DataSetContainer> saveOrUpdateContainers(String collectionId, List<String> containerIds);

//    DataSetContainer removeDataSetContainer(String collectionId, String containerId);

    DataSetCollection getCollectionByName(String collectionName);

    DataSetCollection saveCollection(CollectionDto collectionDto);

    List<DataSetCollection> saveCollections(List<CollectionDto> collectionDtos);

    DataSetCollection deleteByName(String collectionName);

    List<DataSetCollection> deleteBatch(List<String> collectionIds);

    DataSetCollection deleteByCollectionId(String collectionId);

    DataSetCollection updateCollection(DataSetCollection collection);

    Page<DataSetContainer> getContainers(String collectionId, Pageable pageable);

    List<String> getCollectionNames();

    DataSetContainer relateContainer(String collectionId, String containerId);

    Map<String, List<?>> getOptions();

}

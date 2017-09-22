package com.dm.org.service;

import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  17:47 2017/7/6.
 * @description
 * @modified by:
 */
public interface  DataSetContainerService extends BaseService<DataSetContainer, String>
{
    /**
     * @param collectionId 要更新的分类集合
     * @return 集合ID
     */
    public void setOrUpdateContainerCategorization(String collectionId,String containerId);

    List<String> getContainerIds();

    List<DataSetContainer> fetchDataSetContainers(String collectionId);

    DataSetContainer deleteByContainerId(String containerId);

    DataSetCollection fetchCollectionRef(String containerId);

    List<String> getContainerFileNames();

    List<String> getContainerNames();

    String getFileName(String containerId);

    DataSetContainer getContainerByName(String containerName);

    DataSetContainer getContainerByFileName(String fileName);

    String uploadData(String containerId, MultipartFile file);

    byte[] downloadData(String containerId, String filePath);

    String getDataSetPath(String containerId);


}

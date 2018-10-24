package com.vero.dm.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;

import javax.servlet.http.HttpServletResponse;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:47 2017/7/6.
 * @description
 * @modified by:
 */
public interface DataSetContainerService extends BaseService<DataSetContainer, String>
{
    List<String> getContainerIds();

    Page<DataSetContainer> getPageableContainers(Pageable pageable);

    void downloadZip(List<Integer> containerIds, Integer collectionId, HttpServletResponse response);

    List<DataSetContainer> fetchDataSetContainers(String collectionId);

    List<DataSetContainer> deleteByContainerIds(List<Integer> containerIds);

    DataSetCollection fetchCollectionRef(String containerId);

    List<String> getContainerFileNames();

    List<String> getContainerNames();

    String getFileName(String containerId);

    DataSetContainer getContainerByName(String containerName);

    DataSetContainer getContainerByFileName(String fileName);

    byte[] downloadData(String containerId, String filePath);

    String getDataSetPath(String containerId);

}

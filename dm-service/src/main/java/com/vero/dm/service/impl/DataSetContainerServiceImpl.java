package com.vero.dm.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.service.DataSetContainerService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 1:35 2017/7/6.
 * @description
 * @modified by:
 */

@Slf4j
@Service
public class DataSetContainerServiceImpl extends AbstractBaseServiceImpl<DataSetContainer, String> implements DataSetContainerService
{
    @Override
    public List<String> getContainerIds()
    {
        // return containerDao.getContainerIds();
        return null;
    }

    @Override
    public Page<DataSetContainer> getPageableContainers(Pageable pageable)
    {
        return containerJpaRepository.findAll(pageable);
    }

    @Override
    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        // return containerDao.fetchDataSetContainers(collectionId);
        return null;
    }

    @Override
    public DataSetContainer deleteByContainerId(String containerId)
    {
        DataSetContainer container = containerJpaRepository.findOne(containerId);
        deleteDataSetFile(container);
        containerJpaRepository.delete(containerId);
        return container;
    }

    private void deleteDataSetFile(DataSetContainer container)
    {
        File file = new File(container.getFilePath());
        if (file.exists() && file.isFile())
        {
            if (file.delete())
            {
                log.debug("Delete data collection [{}] set:[{}]",
                    container.getDataSetCollection().getCollectionName(), container.getFileName());

            }
            else
            {
                log.error("Delete data set [{}] failed.File don't exists.",
                    container.getFileName());
            }
        }
    }

    @Override
    public DataSetCollection fetchCollectionRef(String containerId)
    {
        // return containerDao.fetchDataSetCollection(containerId);
        return null;
    }

    @Override
    public List<String> getContainerFileNames()
    {
        // return containerDao.getContainerFileNames();
        return null;
    }

    @Override
    public List<String> getContainerNames()
    {
        // return containerDao.getContainerNames();
        return null;
    }

    @Override
    public String getFileName(String containerId)
    {
        // return containerDao.getFileName(containerId);
        return null;
    }

    public DataSetContainer getContainerByName(String containerName)
    {
        return null;
        // return containerDao.getContainerByName(containerName);
    }

    @Override
    public DataSetContainer getContainerByFileName(String fileName)
    {
        return null;
        // return containerDao.getContainerByFileName(fileName);
    }

    @Override
    public byte[] downloadData(String containerId, String filePath)
    {
        File file = new File(filePath);
        try
        {
            return FileUtils.readFileToByteArray(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DataSetContainer findById(String id)
    {
        return containerJpaRepository.findOne(id);
    }

    @Override
    public String getDataSetPath(String containerId)
    {
        return findById(containerId).getFilePath();
    }

}

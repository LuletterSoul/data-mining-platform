package com.dm.org.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetContainerService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 1:35 2017/7/6.
 * @description
 * @modified by:
 */

@Service
public class DataSetContainerServiceImpl extends AbstractBaseServiceImpl<DataSetContainer, String> implements DataSetContainerService
{
    public void setOrUpdateContainerCategorization(String collectionId, String containerId)
    {
        DataSetCollection collection = collectionDao.findById(collectionId);
        DataSetContainer container = findById(containerId);
        container.setDataSetCollection(collection);
        collection.addDataSetContainer(container);
    }

    @Override
    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        return containerDao.fetchDataSetContainers(collectionId);
    }

    @Override
    public DataSetContainer deleteByContainerId(String containerId)
    {
        DataSetContainer container = this.findById(containerId);
        this.deleteById(containerId);
        return container;
    }

    @Override
    public DataSetCollection fetchCollectionRef(String containerId)
    {
        return containerDao.fetchDataSetCollection(containerId);
    }

    @Override
    public List<String> getContainerFileNames()
    {
        return containerDao.getContainerFileNames();
    }

    @Override
    public List<String> getContainerNames() {
        return containerDao.getContainerNames();
    }

    public DataSetContainer getContainerByName(String containerName)
    {
        return containerDao.getContainerByName(containerName);
    }

    @Override
    public DataSetContainer getContainerByFileName(String fileName) {
        return containerDao.getContainerByFileName(fileName);
    }

    @Override
    public String uploadData(String containerId, MultipartFile file)
    {
        DataSetContainer container = this.findById(containerId);
        // ServletContext context =
        // ContextLoader.getCurrentWebApplicationContext().getServletContext();
        String relativePath = "\\dataSetContainer\\" + container.getFileName();
        // String realPath = context.getRealPath(relativePath);
        String realPath ="D:\\Program Files\\Java\\apache-tomcat-8.0.36\\temp";
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(realPath).append(relativePath).append("\\").append(file.getOriginalFilename());
        try
        {
            FileUtils.copyInputStreamToFile(file.getInputStream(),
                    new File(stringBuffer.toString()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String containerName = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        return containerDao.linkPath(containerId, containerName, file.getOriginalFilename(), stringBuffer.toString());
    }

    @Override
    public byte[] downloadData(String containerId, String filePath) {
        File file = new File(filePath);
        try {
            return FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String getDataSetPath(String containerId) {
        return containerDao.getDataSetPath(containerId);
    }

}

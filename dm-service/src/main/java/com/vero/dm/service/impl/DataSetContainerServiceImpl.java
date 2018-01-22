package com.vero.dm.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetContainerService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;


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
    public List<String> getContainerIds() {
        return containerDao.getContainerIds();
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

    @Override
    public String getFileName(String containerId) {
        return containerDao.getFileName(containerId);
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
        //获取应用部署到服务器之后的应用上下文，为文件保存的路径做基础规划；
         ServletContext context =
         ContextLoader.getCurrentWebApplicationContext().getServletContext();
        //获取到绝对路径
        String relativePath = "\\dataSetContainer\\" + container.getContainerId();
         String realPath = context.getRealPath(relativePath);
        try
        {
            FileUtils.copyInputStreamToFile(file.getInputStream(),
                    new File(realPath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //获取文件名
        String containerName = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        //保存到数据库，并返回文件容器的描述
        return containerDao.linkPath(containerId, containerName, file.getOriginalFilename(), realPath);
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

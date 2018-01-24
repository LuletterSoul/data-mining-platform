package com.vero.dm.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.service.DataSetContainerService;


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
//        DataSetCollection collection = collectionDao.findById(collectionId);
//        DataSetContainer container = findById(containerId);
//        container.setDataSetCollection(collection);
//        collection.addDataSetContainer(container);
    }

    @Override
    public List<String> getContainerIds()
    {
//        return containerDao.getContainerIds();
        return null;
    }

    @Override
    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
//        return containerDao.fetchDataSetContainers(collectionId);
        return null;
    }

    @Override
    public DataSetContainer deleteByContainerId(String containerId)
    {
//        DataSetContainer container = this.findById(containerId);
//        this.deleteById(containerId);
//        return container;
        return null;
    }

    @Override
    public DataSetCollection fetchCollectionRef(String containerId)
    {
//        return containerDao.fetchDataSetCollection(containerId);
        return null;
    }

    @Override
    public List<String> getContainerFileNames()
    {
//        return containerDao.getContainerFileNames();
        return null;
    }

    @Override
    public List<String> getContainerNames()
    {
//        return containerDao.getContainerNames();
        return null;
    }

    @Override
    public String getFileName(String containerId)
    {
//        return containerDao.getFileName(containerId);
        return null;
    }

    public DataSetContainer getContainerByName(String containerName)
    {
        return null;
//        return containerDao.getContainerByName(containerName);
    }

    @Override
    public DataSetContainer getContainerByFileName(String fileName)
    {
        return null;
//        return containerDao.getContainerByFileName(fileName);
    }

    @Override
    public String uploadData(String containerId, MultipartFile file)
    {
//        DataSetContainer container = this.findById(containerId);
////        // 获取应用部署到服务器之后的应用上下文，为文件保存的路径做基础规划；
////        ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
////        // 获取到绝对路径
////        String relativePath = "\\dataSetContainer\\" + container.getContainerId();
////        String realPath = context.getRealPath(relativePath);
////        try
////        {
////            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath));
////        }
////        catch (IOException e)
////        {
////            e.printStackTrace();
////        }
////        // 获取文件名
////        String containerName = file.getOriginalFilename().substring(
////            file.getOriginalFilename().indexOf("."));
////        // 保存到数据库，并返回文件容器的描述
////        return containerDao.linkPath(containerId, containerName, file.getOriginalFilename(),
////            realPath);
        return null;
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
    public String getDataSetPath(String containerId)
    {
//        return containerDao.getDataSetPath(containerId);
        return null;
    }

}

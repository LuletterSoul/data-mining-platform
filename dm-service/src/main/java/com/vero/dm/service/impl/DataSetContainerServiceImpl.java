package com.vero.dm.service.impl;


import static com.vero.dm.util.DownloadUtils.bigFileDownload;
import static com.vero.dm.util.DownloadUtils.generateTimestampZipFileName;
import static com.vero.dm.util.PathUtils.concat;
import static com.vero.dm.util.PathUtils.getAbsolutePath;
import static org.apache.commons.io.FileUtils.forceDelete;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.vero.dm.util.CompressUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.exception.file.SetZipException;
import com.vero.dm.model.DataSetCollection;
import com.vero.dm.model.DataSetContainer;
import com.vero.dm.service.DataSetContainerService;
import com.vero.dm.service.constant.ResourcePath;

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
    public void downloadZip(List<Integer> containerIds, Integer collectionId,
                            HttpServletResponse response)
    {
        List<DataSetContainer> dataSetContainers = containerJpaRepository.findAllByContainerIdIn(
            containerIds);
        DataSetCollection collection = collectionJpaRepository.findOne(collectionId);
        ArrayList<File> files = new ArrayList<>();
        dataSetContainers.forEach(d -> files.add(new File(d.getFilePath())));
        String zipRelativePath = concat(ResourcePath.COLLECTION_PATH, "zips");
        String zipPath = getAbsolutePath(zipRelativePath);
        // 生成临时压缩文件
        String zipFileName = UUID.randomUUID().toString() + ".zip";
        // 输出的压缩文件路径
        String zipFilePath = concat(zipPath, zipFileName);
        try
        {
            // 进行文件压缩
            CompressUtil.zip(collection.getDataSetFolderPath(), zipFilePath,files,null, false );
//            ZipUtil.zip(collection.getDataSetFolderPath(), , zipFileName, filePaths);
            bigFileDownload(response, zipFilePath, generateTimestampZipFileName("data_set_"));
            try
            {
                // 删除临时创建的压缩文件
                forceDelete(new File(zipFilePath));
            }
            catch (IOException e)
            {
                throw new SetZipException("Could not delete temp zip files.",
                    ExceptionCode.ZipSetError);
            }
        }
        catch (Exception e)
        {
            throw new SetZipException("Process zip operation error.", ExceptionCode.ZipSetError);
        }
    }

    public void handleStudentExcelModuleDownload(HttpServletResponse response)
    {
        String filePath = "";
        // bigFileDownload(response, filePath);
    }

    @Override
    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        // return containerDao.fetchDataSetContainers(collectionId);
        return null;
    }

    @Override
    public List<DataSetContainer> deleteByContainerIds(List<Integer> containerIds)
    {
        List<DataSetContainer> containers = containerJpaRepository.findAllByContainerIdIn(
            containerIds);
        containerJpaRepository.deleteBatchContainersById(containerIds);
        containers.forEach(c -> {
            try
            {
                forceDelete(new File(c.getFilePath()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        return containers;
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
            return readFileToByteArray(file);
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

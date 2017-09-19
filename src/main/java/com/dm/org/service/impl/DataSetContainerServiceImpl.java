package com.dm.org.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dm.org.exceptions.DataAccessObjectException;
import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetContainerService;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  1:35 2017/7/6.
 * @description
 * @modified by:
 */

@Service
public class DataSetContainerServiceImpl extends AbstractBaseServiceImpl<DataSetContainer, String>
        implements DataSetContainerService
{
    public void setOrUpdateContainerCategorization(String collectionId,String containerId)
    {
        DataSetCollection collection = collectionDao.findById(collectionId);
        DataSetContainer container = findById(containerId);
        container.setDataSetCollection(collection);
        collection.addDataSetContainer(container);
    }

    @Override
    public void deleteById(String s) {
            DataSetContainer container = findById(s);
    }

    @Override
    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        return containerDao.fetchDataSetContainers(collectionId);
    }
}

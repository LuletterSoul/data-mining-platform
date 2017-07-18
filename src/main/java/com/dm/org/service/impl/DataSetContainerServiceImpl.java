package com.dm.org.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dm.org.exception.DataAccessObjectException;
import com.dm.org.exception.DataObjectNotFoundException;
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
        try
        {
            DataSetCollection collection = collectionDao.findById(collectionId);
            DataSetContainer container = findById(containerId);
            container.setDataSetCollection(collection);
            collection.addDataSetContainer(container);
        } catch (DataObjectNotFoundException e) {
            e.printStackTrace();
        } catch (DataAccessObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String s) {
        try {
            DataSetContainer container = findById(s);
        } catch (DataObjectNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<DataSetContainer> fetchDataSetContainers(String collectionId)
    {
        return containerDao.fetchDataSetContainers(collectionId);
    }
}

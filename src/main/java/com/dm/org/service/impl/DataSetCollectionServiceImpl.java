package com.dm.org.service.impl;

import org.springframework.stereotype.Service;

import com.dm.org.exceptions.DataAccessObjectException;
import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.exceptions.DataSetCollectionNoFoundException;
import com.dm.org.model.DataSetCollection;
import com.dm.org.model.DataSetContainer;
import com.dm.org.service.DataSetCollectionService;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  1:34 2017/7/6.
 * @description
 * @modified by:
 */
@Service
public class DataSetCollectionServiceImpl extends AbstractBaseServiceImpl<DataSetCollection, String>
        implements DataSetCollectionService
{

    public DataSetCollection fectchAllDataSetContainer()
    {
        return null;
    }
    public void addDataSetContainer(String collectionId, DataSetContainer container)
            throws DataSetCollectionNoFoundException

    {
        try {
            DataSetCollection collection = findById(collectionId);
            collection.getDataSets().add(container);
            container.setDataSetCollection(collection);
        } catch (DataObjectNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void removeDataSetContainer(String collectionId,String containerId)
    {
        try
        {
            DataSetContainer container
                    = containerDao.findById(containerId);
            DataSetCollection collection = findById(collectionId);
            collection.getDataSets().remove(container);
            container.setDataSetCollection(null);
        } catch (DataObjectNotFoundException e)
        {
            e.printStackTrace();
        } catch (DataAccessObjectException e) {
            e.printStackTrace();
        }
    }
}

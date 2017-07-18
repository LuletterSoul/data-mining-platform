package com.dm.org.dao.impl;

import com.dm.org.model.DataSetCollection;

import org.springframework.stereotype.Repository;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:40 2017/7/5.
 * @description
 * @modified by:
 */
@Repository
public class DataSetCollectionDao extends BaseDao<DataSetCollection, String>
{
    public DataSetCollectionDao()
    {
        super(DataSetCollection.class);
    }

}

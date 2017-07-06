package com.dm.org.dao.supprot;

import com.dm.org.model.DataSetCollection;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:40 2017/7/5.
 * @description
 * @modified by:
 */
@Repository
public class DataSetCollectionDao extends BaseDao<DataSetCollection, UUID>
{
    public DataSetCollectionDao()
    {
        super(DataSetCollection.class);
    }
}

package com.dm.org.dao.impl;

import com.dm.org.model.DataSetAttribute;
import org.springframework.stereotype.Repository;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:43 2017/7/5.
 * @description
 * @modified by:
 */
@Repository
public class DataSetAttributeDao extends BaseDao<DataSetAttribute, Long>
{
    public DataSetAttributeDao()
    {
        super(DataSetAttribute.class);
    }

}

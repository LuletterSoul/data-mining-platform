package com.vero.dm.repository.impl;


import org.springframework.stereotype.Repository;

import com.vero.dm.model.DataSetAttribute;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 23:43 2017/7/5.
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

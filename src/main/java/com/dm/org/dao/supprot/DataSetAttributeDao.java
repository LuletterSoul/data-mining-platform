package com.dm.org.dao.supprot;

import com.dm.org.model.DataSetAttribute;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:43 2017/7/5.
 * @description
 * @modified by:
 */
@Repository
public class DataSetAttributeDao extends BaseDao<DataSetAttribute, UUID>
{

    public DataSetAttributeDao()
    {
        super(DataSetAttribute.class);
    }

}

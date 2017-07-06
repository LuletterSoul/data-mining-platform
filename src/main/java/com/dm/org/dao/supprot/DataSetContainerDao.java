package com.dm.org.dao.supprot;

import com.dm.org.model.DataSetContainer;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  23:42 2017/7/5.
 * @description
 * @modified by:
 */

@Repository
public class DataSetContainerDao extends BaseDao<DataSetContainer, UUID>
{
    public DataSetContainerDao()
    {
        super(DataSetContainer.class);
    }
}

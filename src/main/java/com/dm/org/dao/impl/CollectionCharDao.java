package com.dm.org.dao.impl;

import com.dm.org.dao.impl.BaseDao;
import com.dm.org.model.DataSetCharacteristic;
import org.springframework.stereotype.Repository;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class CollectionCharDao extends BaseDao<DataSetCharacteristic,Integer>{
    public CollectionCharDao() {
        super(DataSetCharacteristic.class);
    }
}

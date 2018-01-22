package com.vero.dm.repository.impl;


import org.springframework.stereotype.Repository;

import com.vero.dm.model.AreaType;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AreaTypeDao extends BaseDao<AreaType, Integer>
{
    public AreaTypeDao()
    {
        super(AreaType.class);
    }
}

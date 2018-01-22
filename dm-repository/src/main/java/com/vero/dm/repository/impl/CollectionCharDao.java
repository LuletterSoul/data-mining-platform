package com.vero.dm.repository.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.vero.dm.model.DataSetCharacteristic;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Repository
@SuppressWarnings("unchecked")
public class CollectionCharDao extends BaseDao<DataSetCharacteristic, Integer>
{
    public CollectionCharDao()
    {
        super(DataSetCharacteristic.class);
    }

    public List<DataSetCharacteristic> getCharTypes(List<Integer> charIds)
    {
        String hqlString = "select distinct c from DataSetCharacteristic c where c.charId in :charIds";
        return getSession().createQuery(hqlString).setParameterList("charIds",
            charIds).getResultList();
    }
}

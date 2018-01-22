package com.vero.dm.repository.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.vero.dm.model.AttributeType;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AttributeTypeDao extends BaseDao<AttributeType, Integer>
{
    public AttributeTypeDao()
    {
        super(AttributeType.class);
    }

    public List<AttributeType> getAttrTypes(List<Integer> typeIds)
    {
        String hqlString = "select distinct a from AttributeType a where a.typeId in :typeIds";
        return getSession().createQuery(hqlString).setParameterList("typeIds",
            typeIds).getResultList();
    }
}

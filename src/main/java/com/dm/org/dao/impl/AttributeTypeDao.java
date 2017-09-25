package com.dm.org.dao.impl;

import com.dm.org.dao.impl.BaseDao;
import com.dm.org.model.AttributeType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Repository
@SuppressWarnings("unchecked")
public class AttributeTypeDao extends BaseDao<AttributeType, Integer> {
    public AttributeTypeDao() {
        super(AttributeType.class);
    }

    public List<AttributeType> getAttrTypes(List<Integer> typeIds) {
        String hqlString = "select distinct a from AttributeType a where a.typeId in :typeIds";
        return getSession().createQuery(hqlString).setParameterList("typeIds",typeIds).getResultList();
    }
}

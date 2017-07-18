package com.dm.org.dao.impl;

import com.dm.org.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:15 2017/7/14.
 * @description
 * @modified by:
 */
@Repository
public class PermissionDao  extends BaseDao<Permission,Long>
{

    public PermissionDao()
    {
        super(Permission.class);
    }

    public List<Permission> fetchPermissionListByIdList(List<Long> permissionIdList)
     {
         buildCriteriaQuery();
         criteriaQuery.select(baseRoot).where(baseRoot.get(Permission_.permissionId).in(permissionIdList));
         return getSession().createQuery(criteriaQuery).getResultList();
    }
}

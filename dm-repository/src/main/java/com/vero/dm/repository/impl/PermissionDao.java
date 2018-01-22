package com.vero.dm.repository.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.vero.dm.model.Permission;
import com.vero.dm.model.Permission_;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:15 2017/7/14.
 * @description
 * @modified by:
 */
@Repository
public class PermissionDao extends BaseDao<Permission, Long>
{

    public PermissionDao()
    {
        super(Permission.class);
    }

    public List<Permission> fetchPermissionListByIdList(List<Long> permissionIdList)
    {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot).where(
            baseRoot.get(Permission_.permissionId).in(permissionIdList));
        return getSession().createQuery(criteriaQuery).getResultList();
    }
}

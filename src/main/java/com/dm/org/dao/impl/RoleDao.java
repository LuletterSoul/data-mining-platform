package com.dm.org.dao.impl;

import com.dm.org.model.Role;
import com.dm.org.model.Role_;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:15 2017/7/14.
 * @description
 * @modified by:
 */
@Repository
public class RoleDao extends BaseDao<Role,Long>
{
    public RoleDao()
    {
        super(Role.class);
    }

    public List<Role> fetchRoleListByIdList(List<Long> roleIdList)
    {
        buildCriteriaQuery();
        criteriaQuery.select(baseRoot).where(baseRoot.get(Role_.roleId).in(roleIdList));
        return getSession().createQuery(criteriaQuery).getResultList();
    }

    public Role fetchRoleJoinPermission(Long roleId)
    {
        buildCriteriaQuery();
        baseRoot.fetch(Role_.permissionSet,JoinType.LEFT);
        criteriaQuery.select(baseRoot)
                .where(baseBuilder.equal(baseRoot.get(Role_.roleId), roleId));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public void removePermissions(Long roleId, List<Long> permissionList)
    {
//        buildCriteriaDelete();
//        Predicate permissionRestriction = baseRoot.get(Role_.permissionSet).in(permissionList);
//        Predicate roleRestriction = baseBuilder.equal(baseRoot.get(Role_.roleId), roleId);
//        Predicate whereRestriction = baseBuilder. and(roleRestriction,permissionRestriction);
//        criteriaDelete.where(whereRestriction);
//        getSession().createQuery(criteriaDelete).executeUpdate();
//        String hqlString = "delete from Role r " +
//                "where r.roleId = :roleId and  r.permissionSet in :permissionList";
//        getSession()
//                .createQuery(hqlString)
//                .setParameter("roleId", roleId)
//                .setParameter("permissionList", permissionList)
//                .executeUpdate();
        String sqlString = "DELETE from role_permission_re " +
                "where roleId = :roleId " +
                "and permissionId in :permissionList";
        getSession().createNativeQuery(sqlString)
                .setParameter("roleId", roleId)
                .setParameter("permissionList", permissionList)
                .executeUpdate();
    }

}

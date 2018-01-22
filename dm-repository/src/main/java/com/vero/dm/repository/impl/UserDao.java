package com.vero.dm.repository.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import com.vero.dm.model.*;
import org.springframework.stereotype.Repository;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:11 2017/7/14.
 * @description
 * @modified by:
 */

@Repository
@SuppressWarnings("unchecked")
public class UserDao extends BaseDao<User, String>
{

    public UserDao()
    {
        super(User.class);
    }

    public User fetchUserJoinRolesById(String userId)
    {
        buildCriteriaQuery();
        baseRoot.fetch(User_.roles, JoinType.LEFT);
        Predicate whereRestriction = baseBuilder.equal(baseRoot.get(User_.userId), userId);
        criteriaQuery.where(whereRestriction);
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public User findByUserName(String username)
    {
        buildCriteriaQuery();
        criteriaQuery.where(baseBuilder.equal(baseRoot.get(User_.username), username));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public User fetchByUserName(String username)
    {
        buildCriteriaQuery();
        baseRoot.fetch(User_.roles, JoinType.LEFT).fetch(Role_.permissionSet, JoinType.LEFT);
        criteriaQuery.where(baseBuilder.equal(baseRoot.get(User_.username), username));
        return getSession().createQuery(criteriaQuery).getSingleResult();
    }

    public String findPasswordByUserName(String userName)
    {
        buildCriteriaQuery();
        CriteriaQuery<String> stringCriteriaQuery = getSession().getCriteriaBuilder().createQuery(
            String.class);
        baseRoot = stringCriteriaQuery.from(User.class);
        Predicate whereRestriction = baseBuilder.equal(baseRoot.get(User_.username), userName);
        stringCriteriaQuery.select(baseRoot.get(User_.password)).where(whereRestriction);
        return getSession().createQuery(stringCriteriaQuery).getSingleResult();
    }

    public List<Permission> findPermissionByUserName(String userName)
    {
        String hqlString = "select p from User u " + "left join u.roles r "
                           + "left join r.permissionSet p " + "where u.username = :userName";
        return getSession().createQuery(hqlString).setParameter("userName",
            userName).list();
    }

    public Set<String> findPermissionNameSet(String userName)
    {
        String hqlString = "select p.permissionName from User u " + "left join u.roles r "
                           + "left join r.permissionSet p " + "where u.username = :userName";
        return new HashSet<String>(getSession().createQuery(hqlString).setParameter("userName",
            userName).getResultList());
    }

    public List<Role> findRolesByUserName(String username)
    {
        buildCriteriaQuery();
        baseRoot.fetch(User_.roles, JoinType.LEFT);
        criteriaQuery.select(baseRoot).where(
            baseBuilder.equal(baseRoot.get(User_.username), username));
        User user = getSession().createQuery(criteriaQuery).getSingleResult();
        return new ArrayList<Role>(user.getRoles());
    }

    public List<String> findRoleNameSetByUserName(String userName)
    {
        String hqlString = "select r.roleName from User u " + "left join u.roles r "
                           + "where u.username = :userName";
        return getSession().createQuery(hqlString).setParameter("userName",
            userName).getResultList();
    }

    public List<Long> findRoleIdListByUserId(String userId)
    {
        String hqlString = "select r.roleId from " + "User u left join u.roles r "
                           + "where u.userId = :userId";
        return getSession().createQuery(hqlString).setParameter("userId", userId).getResultList();
    }

    public void removeRoles(String userId, List<Long> roleIdList)
    {
        // User user = fetchUserJoinRolesById(userId);
        // for (Role role :
        // roleList)
        // {
        // user.getRoleSet().remove(role);
        // }
        String sqlString = "DELETE from user_role_re "
                           + "where userId = :userId and (roleId in :roleList)";
        getSession().createNativeQuery(sqlString).setParameter("userId", userId).setParameter(
            "roleList", roleIdList).executeUpdate();
        // buildCriteriaDelete();
        //// CriteriaDelete<Role> roleCriteriaDelete = getSession()
        //// .getCriteriaBuilder().createCriteriaDelete(Role.class);
        // // Root<Role> roleRoot = roleCriteriaDelete.from(Role.class);
        //// baseRoot.fetch(User_.roleSet,JoinType.LEFT);
        // Predicate userRestriction = baseBuilder.equal(baseRoot.get(User_.userId), userId);
        // Predicate roleRestriction = baseBuilder.in(baseRoot.get(User_.roleSet),roleList);
        // Predicate whereRestriction = baseBuilder.and(userRestriction, roleRestriction);
        // criteriaDelete.where(whereRestriction);
        // getSession().createQuery(criteriaDelete).executeUpdate();
    }

    public String getPublicSalt(String username)
    {
        String hqlString = "select u.publicSalt from User u " + "where u.username = :username";
        return (String)getSession().createQuery(hqlString).setParameter("username",
            username).getSingleResult();
    }

    public String getPrivateSalt(String username)
    {
        String hqlString = "select u.privateSalt from User u " + "where u.username = :username";
        return (String)getSession().createQuery(hqlString).setParameter("username",
            username).getSingleResult();
    }
}

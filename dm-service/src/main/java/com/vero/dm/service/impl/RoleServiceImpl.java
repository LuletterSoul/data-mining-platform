package com.vero.dm.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.vero.dm.model.Permission;
import com.vero.dm.model.Role;
import com.vero.dm.service.RoleService;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:22 2017/7/14.
 * @description
 * @modified by:
 */
@Service
public class RoleServiceImpl extends AbstractBaseServiceImpl<Role, Long> implements RoleService
{
    public List<Role> fetchRoleListByIdList(List<Long> roleIdList)
    {
        return roleDao.fetchRoleListByIdList(roleIdList);
    }

    public Role fetchRoleJoinPermissions(Long roleId)
    {
        return roleDao.fetchRoleJoinPermission(roleId);
    }

    public List<Permission> fetchPermissionListById(Long roleId)
    {
        Set<Permission> permissionSet = fetchRoleJoinPermissions(roleId).getPermissionSet();
        return new ArrayList<Permission>(permissionSet);
    }

    public void correlatePermissions(Long roleId, List<Long> permissionList)
    {
        Role role = roleDao.fetchRoleJoinPermission(roleId);
        for (Long permissionId : permissionList)
        {
            role.getPermissionSet().add(permissionDao.findById(permissionId));
        }
        roleDao.update(role);
    }

    public void correlatePermission(Long roleId, Long permissionId)
    {
        List<Long> permissionList = new ArrayList<Long>();
        permissionList.add(permissionId);
        correlatePermissions(roleId, permissionList);
    }

    public void removePermission(Long roleId, Long permissionId)
    {
        List<Long> permissionIdList = new ArrayList<Long>();
        permissionIdList.add(permissionId);
        removePermissions(roleId, permissionIdList);
    }

    public void removePermissions(Long roleId, List<Long> permissionIdList)
    {
        roleDao.removePermissions(roleId, permissionIdList);
    }

}

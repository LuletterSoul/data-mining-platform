package com.dm.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dm.org.exception.DataObjectNotFoundException;
import com.dm.org.model.Permission;
import com.dm.org.model.Role;
import com.dm.org.service.RoleService;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:22 2017/7/14.
 * @description
 * @modified by:
 */
@Service
public class RoleServiceImpl extends AbstractBaseServiceImpl<Role,Long> implements RoleService
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
        try
        {
            Role role = roleDao.fetchRoleJoinPermission(roleId);
            for (Long permissionId :
                    permissionList)
            {
                role.getPermissionSet().add(permissionDao.findById(permissionId));
            }
            roleDao.update(role);
        } catch (DataObjectNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void correlatePermission(Long roleId,Long permissionId)
    {
        List<Long> permissionList = new ArrayList<Long>();
        permissionList.add(permissionId);
        correlatePermissions(roleId,permissionList);
    }

    public void removePermission(Long roleId,Long permissionId)
    {
        List<Long> permissionIdList = new ArrayList<Long>();
        permissionIdList.add(permissionId);
        removePermissions(roleId,permissionIdList);
    }

    public void removePermissions(Long roleId, List<Long> permissionIdList)
    {
        roleDao.removePermissions(roleId, permissionIdList);
    }

}

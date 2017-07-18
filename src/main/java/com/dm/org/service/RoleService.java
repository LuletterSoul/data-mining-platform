package com.dm.org.service;

import com.dm.org.model.Permission;
import com.dm.org.model.Role;

import java.util.List;
import java.util.Set;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:21 2017/7/14.
 * @description
 * @modified by:
 */
public interface RoleService extends BaseService<Role,Long>
 {
  List<Role> fetchRoleListByIdList(List<Long> roleIdList);

  List<Permission> fetchPermissionListById(Long roleId);

  void correlatePermissions(Long roleId, List<Long> permissionList);

  void correlatePermission(Long roleId,Long permissionId);

  void removePermission(Long roleId,Long permissionId);

  void removePermissions(Long roleId, List<Long> permissionIdList);
}

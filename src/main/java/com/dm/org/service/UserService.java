package com.dm.org.service;


import com.dm.org.exceptions.DataObjectNotFoundException;
import com.dm.org.model.Permission;
import com.dm.org.model.Role;
import com.dm.org.model.User;
import com.dm.org.security.credentials.DisposableSaltEntry;

import java.util.List;
import java.util.Set;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:09 2017/7/14.
 * @description
 * @modified by:
 */
public interface UserService extends BaseService<User, String>
{
    User registerUser(User user);

    User doUserCredentialsMatch(User user, DisposableSaltEntry entry);

    DisposableSaltEntry  getRandomVerifySaltEntry(String preSaltId);

    List<Long> findRoleIdListByUserId(String userId);

    Set<String> findPermissionNameSet(String userName);

    User fetchUserJoinRolesById(String userId);

    String findPasswordByUserName(String userName);

    void updatePassword(String userName, String newPassword)
        throws DataObjectNotFoundException;

    void correlateRole(String userId, Long roleId);

    void correlateRoles(String userId, List<Long> roleIdList);

    User findByUserName(String userName);

    User fetchByUserName(String userName);

    List<Permission> findPermissionByUserName(String userName);

    List<Role> findRolesByUserName(String userName);

    Set<String> findRoleNameSetByUserName(String userName);

    void removeRole(String userId, Long roleId);

    void removeRoles(String userId, List<Long> roleIdList);

    String getPublicSalt(String username);

    String fetchPublicSalt(String username);

    String fetchPrivateSalt(String username);
}

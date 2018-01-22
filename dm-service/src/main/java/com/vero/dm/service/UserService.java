package com.vero.dm.service;


import java.util.List;
import java.util.Set;

import com.vero.dm.exception.DataObjectNotFoundException;
import com.vero.dm.model.Permission;
import com.vero.dm.model.Role;
import com.vero.dm.model.User;
import com.vero.dm.repository.dto.UserDTO;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:09 2017/7/14.
 * @description
 * @modified by:
 */
public interface UserService extends BaseService<User, String>
{
    UserDTO registerUser(User user);

    UserDTO getUserProfile(String username);

    UserDTO updateUser(UserDTO userDTO);
    // User doUserCredentialsMatch(User user, DisposableSaltEntry entry);

    // DisposableSaltEntry getRandomVerifySaltEntry(String preSaltId);

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

    List<String> findRoleNameSetByUserName(String userName);

    void removeRole(String userId, Long roleId);

    void removeRoles(String userId, List<Long> roleIdList);

    String getPublicSalt(String username);

    String fetchPublicSalt(String username);

    String fetchPrivateSalt(String username);
}

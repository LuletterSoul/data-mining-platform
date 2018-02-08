package com.vero.dm.service;


import java.util.List;
import java.util.Set;

import com.vero.dm.exception.DataObjectNotFoundException;
import com.vero.dm.model.Permission;
import com.vero.dm.model.Role;
import com.vero.dm.model.User;
import com.vero.dm.repository.dto.UserDto;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:09 2017/7/14.
 * @description
 * @modified by:
 */
public interface UserService extends BaseService<User, String>
{
    UserDto registerUser(User user);

    UserDto getUserProfile(String username);

    UserDto updateUser(UserDto userDto);

    void correlateRoles(String userId, List<Long> roleIdList);

    User fetchByUserName(String userName);

    List<String> findRoleNameSetByUserName(String userName);

    List<String> findPermissionNameSet(String username);

    void removeRoles(String userId, List<Long> roleIdList);

    String fetchPublicSalt(String username);

    String fetchPrivateSalt(String username);
}

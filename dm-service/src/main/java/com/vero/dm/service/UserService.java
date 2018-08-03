package com.vero.dm.service;


import java.util.List;

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

    UserDto getUserProfile(String username);

    UserDto updateUser(UserDto userDto, String accessToken);

    boolean createUsername(String username);

    void correlateRoles(List<? extends User> users, List<String> roleNames);

    void correlateRoles(String userId, List<String> roleNames);

    User fetchByUserName(String userName);

    List<String> findRoleNameSetByUserName(String userName);

    List<String> findPermissionNameSet(String username);

    void removeRoles(String userId, List<Long> roleIdList);

    String fetchPublicSalt(String username);

    String fetchPrivateSalt(String username);

}

package com.dm.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dm.org.exception.DataObjectNotFoundException;
import com.dm.org.model.Permission;
import com.dm.org.model.Role;
import com.dm.org.model.User;
import com.dm.org.security.PasswordHelper;
import com.dm.org.service.UserService;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  18:14 2017/7/14.
 * @description
 * @modified by:
 */
@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User,String> implements UserService
 {
     public User registerUser(User user)
     {
         PasswordHelper.encryptPassword(user);
         userDao.save(user);
         return user;
     }

     public User fetchUserJoinRolesById(String userId)
     {
         return userDao.fetchUserJoinRolesById(userId);
     }

     public List<Long> findRoleIdListByUserId(String userId)
     {
         return userDao.findRoleIdListByUserId(userId);
     }


     public Set<String> findPermissionNameSet(String userName)
     {
         return userDao.findPermissionNameSet(userName);
     }


     public void updatePassword(String userName, String newPassword) throws DataObjectNotFoundException {
             User user = userDao.findByUserName(userName);
             user.setPassword(newPassword);
             PasswordHelper.encryptPassword(user);
             userDao.update(user);
     }

     public void correlateRole(String userId, Long roleId)
     {
         List<Long> roleIdList = new ArrayList<Long>();
         roleIdList.add(roleId);
         correlateRoles(userId,roleIdList);
     }

     public void correlateRoles(String userId, List<Long> roleIdList)
     {
         try {
             User user = userDao.fetchUserJoinRolesById(userId);
             for (Long roleId :
                     roleIdList)
             {
                 user.getRoleSet().add(roleDao.findById(roleId));
             }
             userDao.update(user);
         } catch (DataObjectNotFoundException e)
          {
             e.printStackTrace();
          }
     }

//     public void removeRoles(String userId, List<Long> roleIdList)
//      {
//          try {
//              User user = userDao.findById(userId);
//              for (Long roleId :
//                      roleIdList)
//              {
//                  user.getRoleSet().remove(roleDao.findById(roleId));
//              }
//              userDao.update(user);
//          } catch (DataObjectNotFoundException e)
//           {
//              e.printStackTrace();
//          }
//      }
//
//     public void removeRole(String userId, Long roleId)
//      {
//         List<Long> roleIdList = new ArrayList<Long>();
//         roleIdList.add(roleId);
//         removeRoles(userId,roleIdList);
//     }

     public User findByUserName(String userName)
     {
         return userDao.findByUserName(userName);
     }

     public List<Permission> findPermissionByUserName(String userName)
     {
         return userDao.findPermissionByUserName(userName);
     }

     public List<Role> findRolesByUserName(String userName)
     {
         return userDao.findRolesByUserName(userName);
     }


     public Set<String> findRoleNameSetByUserName(String userName)
     {
         return userDao.findRoleNameSetByUserName(userName);
     }

     public void removeRole(String userId, Long roleId)
     {
         List<Long> roleIdList = new ArrayList<Long>();
         roleIdList.add(roleId);
         removeRoles(userId, roleIdList);
     }


     public void removeRoles(String userId,List<Long> roleIdList)
     {
        // List<Role> roleList = roleDao.fetchRoleListByIdList(roleIdList);
         userDao.removeRoles(userId,roleIdList);
     }
}

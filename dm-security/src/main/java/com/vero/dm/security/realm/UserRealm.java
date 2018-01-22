package com.vero.dm.security.realm;


import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vero.dm.model.User;
import com.vero.dm.model.UserAccessStatus;
import com.vero.dm.repository.impl.UserDao;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 21:20 2017/7/16.
 * @description
 * @modified by:
 */
@Component
public class UserRealm extends AuthorizingRealm
{

    // private UserService userService;

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    // @Autowired
    // @Qualifier("userServiceImpl")
    // public void setUserService(UserService userService)
    // {
    // this.userService = userService;
    // }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleNames = new LinkedHashSet<String>(
            userDao.findRoleNameSetByUserName(username));
        authorizationInfo.setRoles(roleNames);
        authorizationInfo.setStringPermissions(userDao.findPermissionNameSet(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException
    {

        String username = (String)token.getPrincipal();

        User user = userDao.findByUserName(username);
        if (user == null)
        {
            String message = "User " + username
                             + " is unknown or password not correct.Please try again or contact adminstractor.";
            throw new UnknownAccountException(message);// 没找到帐号
        }

        if (user.getAccountStatus().equals(UserAccessStatus.LOCKED))
        {
            throw new LockedAccountException(); // 帐号锁定
        }

        return new SimpleAuthenticationInfo(user.getUsername(), // 用户名
            user.getPassword(), // 密码
            ByteSource.Util.bytes(user.getPublicSalt()), getName());
    }
    //
    // @Override
    // public void clearCachedAuthorizationInfo(PrincipalCollection principals)
    // {
    // super.clearCachedAuthorizationInfo(principals);
    // }
    //
    // @Override
    // public void clearCachedAuthenticationInfo(PrincipalCollection principals)
    // {
    // super.clearCachedAuthenticationInfo(principals);
    // }
    //
    // @Override
    // public void clearCache(PrincipalCollection principals)
    // {
    // super.clearCache(principals);
    // }
    //
    // public void clearAllCachedAuthorizationInfo()
    // {
    // getAuthorizationCache().clear();
    // }
    //
    // public void clearAllCachedAuthenticationInfo()
    // {
    // getAuthenticationCache().clear();
    // }
    //
    // public void clearAllCache()
    // {
    // clearAllCachedAuthenticationInfo();
    // clearAllCachedAuthorizationInfo();
    // }

}

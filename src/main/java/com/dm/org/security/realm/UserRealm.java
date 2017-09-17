package com.dm.org.security.realm;


import com.dm.org.model.User;
import com.dm.org.enums.UserAccessStatus;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dm.org.service.UserService;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 21:20 2017/7/16.
 * @description
 * @modified by:
 */
@Component
public class UserRealm extends AuthorizingRealm
{

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRoleNameSetByUserName(username));
        authorizationInfo.setStringPermissions(userService.findPermissionNameSet(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException
    {

        String username = (String)token.getPrincipal();

        User user = userService.findByUserName(username);
        if (user == null)
        {
            String message = "User "+username+" is unknown or password not correct.Please try again or contact adminstractor.";
            throw new UnknownAccountException(message);// 没找到帐号
        }

        if (user.getStatus().equals(UserAccessStatus.LOCKED))
        {
            throw new LockedAccountException(); // 帐号锁定
        }

        return new SimpleAuthenticationInfo(user.getUserName(), // 用户名
            user.getPassword(), // 密码
            ByteSource.Util.bytes(user.getPublicSalt()),
            getName()
        );
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

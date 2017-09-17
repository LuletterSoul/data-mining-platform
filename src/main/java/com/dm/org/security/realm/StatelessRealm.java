package com.dm.org.security.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dm.org.model.User;
import com.dm.org.security.credentials.TokenManager;
import com.dm.org.service.UserService;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:52 2017/7/17.
 * @description
 * @modified by:
 */
public class StatelessRealm extends AuthorizingRealm
{
    private UserService userService;

    private TokenManager tokenManager;


    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }



    public boolean supports(AuthenticationToken token)
    {
        // 仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
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
        StatelessToken statelessToken = (StatelessToken)token;
        String username = statelessToken.getUsername();
        User user = userService.fetchByUserName(username);
        String password = user.getPassword();
        Hash tokenSaltHash = tokenManager.getHashToken(username);
        if (tokenSaltHash == null) {
            throw new AuthenticationException("Token is not valid.Please apply previous time out token firstly.");
        }
        return new StatelessInfo(
                username,
                //混入一次性token的证书;
                tokenSaltHash.toBase64()+password,
                statelessToken.getParams(),
                getName()
        );
    }
}
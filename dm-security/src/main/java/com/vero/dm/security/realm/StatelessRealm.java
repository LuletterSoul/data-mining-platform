package com.vero.dm.security.realm;


import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.security.credentials.DisposableTokenMaintainer;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.security.credentials.UserProfileAccessor;
import com.vero.dm.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:52 2017/7/17.
 * @description
 * @modified by:
 */
@Slf4j
public class StatelessRealm extends AuthorizingRealm
{
    private UserService userService;
    //
    // @Autowired
    // private UserDao userDao;

    private TokenManager tokenManager;

    private UserProfileAccessor profileAccessor;

    private DisposableTokenMaintainer disposableTokenMaintainer;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatelessRealm.class);

    @Autowired
    public void setDisposableTokenMaintainer(DisposableTokenMaintainer disposableTokenMaintainer)
    {
        this.disposableTokenMaintainer = disposableTokenMaintainer;
    }

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager)
    {
        this.tokenManager = tokenManager;
    }

    @Autowired
    public void setProfileAccessor(UserProfileAccessor profileAccessor)
    {
        this.profileAccessor = profileAccessor;
    }

    public boolean supports(AuthenticationToken token)
    {
        return token instanceof StatelessToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roleNames = new LinkedHashSet<String>(
            userService.findRoleNameSetByUserName(username));
        authorizationInfo.setRoles(roleNames);
        Set<String> permissionNames = new LinkedHashSet<>(
            userService.findPermissionNameSet(username));
        authorizationInfo.setStringPermissions(permissionNames);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException
    {
        StatelessToken statelessToken = (StatelessToken)token;
        String username = statelessToken.getUsername();
        String accessToken = statelessToken.getAccessToken();
        LinkedList<String> candidates = buildCredentialCandidates(accessToken);
        // 如果是按照当前令牌授权,则从缓存中拉取对应的用户信息
        if (statelessToken.getIsAccessByAvailableToken())
        {
            return buildForCachedUserInfo(statelessToken, accessToken,candidates );
        }
        //否则,使用
        return new StatelessInfo(username, candidates.getLast(),
            statelessToken.getParams(), getName(),candidates ,
            accessToken);
    }

    private AuthenticationInfo buildForCachedUserInfo(StatelessToken statelessToken,
                                                      String accessToken, LinkedList<String> candidates)
    {
        UserDto userDto = profileAccessor.fetchProfile(statelessToken.getAccessToken());
        return new StatelessInfo(userDto.getUsername(), accessToken,
                statelessToken.getParams(), getName(),candidates,
                accessToken);
    }


    private LinkedList<String> buildCredentialCandidates(String accessToken)
    {
        List<String> tokenList = disposableTokenMaintainer.retrieveTokenList(accessToken);
        LinkedList<String> tokenListCandidates = new LinkedList<>();
        tokenList.forEach(t ->
                tokenListCandidates.add(accessToken + t));
        return tokenListCandidates;
    }
}
package com.vero.dm.security.realm;


import java.util.Map;

import lombok.*;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 12:07 2017/7/17.
 * @description
 * @modified by:
 */

@Data
public class StatelessToken implements AuthenticationToken, SaltedAuthenticationInfo
{

    private static final long serialVersionUID = 254642675520751570L;

    private String username;

    private String accessToken;

    private Map<String, ?> params;

    private String clientDigest;

    private Boolean isAccessByAvailableToken = false;


    public StatelessToken(String username, String key, Map<String, ?> params, String clientDigest,Boolean isAccessByAvailableToken)
    {
        this.username = username;
        this.accessToken = key;
        this.params = params;
        this.clientDigest = clientDigest;
        this.isAccessByAvailableToken = isAccessByAvailableToken;
    }

    @Override
    public Object getPrincipal()
    {
        return username;
    }

    @Override
    public PrincipalCollection getPrincipals()
    {
        return null;
    }

    @Override
    public Object getCredentials()
    {
        return getClientDigest();
    }

    @Override
    public ByteSource getCredentialsSalt()
    {
        return null;
    }
}
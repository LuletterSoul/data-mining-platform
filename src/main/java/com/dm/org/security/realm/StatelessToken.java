package com.dm.org.security.realm;


import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 12:07 2017/7/17.
 * @description
 * @modified by:
 */
public class StatelessToken implements AuthenticationToken
{

    private static final long serialVersionUID = 7026548777981436489L;

    private String username;

    private Map<String, ?> params;

    private String clientDigest;

    public StatelessToken(String username, Map<String, ?> params, String clientDigest)
    {
        this.username = username;
        this.params = params;
        this.clientDigest = clientDigest;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Map<String, ?> getParams()
    {
        return params;
    }

    public void setParams(Map<String, ?> params)
    {
        this.params = params;
    }

    public String getClientDigest()
    {
        return clientDigest;
    }

    public void setClientDigest(String clientDigest)
    {
        this.clientDigest = clientDigest;
    }

    @Override
    public Object getPrincipal()
    {
        return username;
    }

    @Override
    public Object getCredentials()
    {
        return clientDigest;
    }
}

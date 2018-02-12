package com.vero.dm.security.realm;


import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.vero.dm.security.credentials.StatelessCredentialsMatcher;
import com.vero.dm.security.filter.StatelessAuthenticatingFilter;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:46 2017/9/14.
 * @since data-minning-platform
 */

/**
 * 由{@link StatelessRealm#doGetAuthenticationInfo(AuthenticationToken)}
 * 根据客户端token传入的用户名,查询到当前数据库的用户信息,封装成{@link StatelessInfo}对象
 * 该对象用于{@link StatelessCredentialsMatcher#doCredentialsMatch(AuthenticationToken, AuthenticationInfo)}
 * 的授权逻辑
 */

public class StatelessInfo extends SimpleAuthenticationInfo
{

    private static final long serialVersionUID = -4140362315755317128L;


    private Map<String, ?> clientParams;

    public StatelessInfo()
    {}

    public StatelessInfo(Object principal, Object credentials, Map<String, ?> clientParams,
                         String realmName)
    {
        super(principal, credentials, realmName);
        this.clientParams = clientParams;
    }

    public Map<String, ?> getClientParams()
    {
        return clientParams;
    }

    /**
     * 客户端与服务器协商好的加密算法;
     * 
     * @param clientParams
     *            {@link StatelessAuthenticatingFilter#onAccessDenied(ServletRequest, ServletResponse)}
     *            拦截到客户端传来的消息摘要，
     *            用于{@link com.dm.org.service.StatelessCredentialsService#computeHashWithParams(StatelessInfo, int)}
     *            中进行摘要运算.
     */
    public void setClientParams(Map<String, ?> clientParams)
    {
        this.clientParams = clientParams;
    }
}

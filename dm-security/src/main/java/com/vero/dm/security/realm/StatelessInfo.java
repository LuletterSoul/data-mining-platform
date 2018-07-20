package com.vero.dm.security.realm;


import java.util.LinkedList;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.vero.dm.security.credentials.StatelessChainCredentialsMatcher;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 13:46 2017/9/14.
 * @since data-minning-platform
 */

/**
 * 由{@link StatelessRealm#doGetAuthenticationInfo(AuthenticationToken)}
 * 根据客户端token传入的用户名,查询到当前数据库的用户信息,封装成{@link StatelessInfo}对象
 * 该对象用于{@link StatelessChainCredentialsMatcher#doCredentialsMatch(AuthenticationToken, AuthenticationInfo)}
 * 的授权逻辑
 */
@Getter
@Setter
public class StatelessInfo extends SimpleAuthenticationInfo
{

    private static final long serialVersionUID = -4140362315755317128L;

    private Map<String, ?> clientParams;

    private String accesstoken;

    /**
     * 可以作为最新的缓存机制
     */
    private LinkedList<String> credentialCandidates = new LinkedList<>();


    public StatelessInfo()
    {}

    public StatelessInfo(Object principal, Object credentials, Map<String, ?> clientParams,
                         String realmName, LinkedList<String> credentialCandidates, String accessToken)
    {
        super(principal, credentials, realmName);
        this.clientParams = clientParams;
        this.credentialCandidates = credentialCandidates;
        this.accesstoken = accessToken;
    }

    public StatelessInfo(Object principal, Object credentials, Map<String, ?> clientParams,
                         String realmName,LinkedList<String> candidates)
    {
        super(principal, credentials, realmName);
        this.clientParams = clientParams;
        this.credentialCandidates = candidates;
    }

    public Map<String, ?> getClientParams()
    {
        return clientParams;
    }


    public LinkedList<String> getCredentialCandidates()
    {
        return credentialCandidates;
    }
}

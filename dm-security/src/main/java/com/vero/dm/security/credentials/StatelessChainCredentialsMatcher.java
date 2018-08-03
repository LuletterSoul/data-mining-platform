package com.vero.dm.security.credentials;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;
import com.vero.dm.security.strategy.StrategyMatchChain;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:29 2017/9/14.
 * @since data-mining-platform
 */

@Slf4j
@Setter
public class StatelessChainCredentialsMatcher extends HashedCredentialsMatcher
{
    private StatelessCredentialsServer statelessCredentialsServer;

    private StrategyMatchChain matchChain;

    public StatelessChainCredentialsMatcher()
    {

    }

    public StatelessChainCredentialsMatcher(StrategyMatchChain matchChain)
    {
        this.matchChain = matchChain;
    }

    @Autowired
    public void setStatelessCredentialsServer(StatelessCredentialsServer statelessCredentialsServer)
    {
        this.statelessCredentialsServer = statelessCredentialsServer;
    }

    @Autowired
    public void setMatchChain(StrategyMatchChain matchChain)
    {
        this.matchChain = matchChain;
    }

    /**
     * 客户端与服务器协商好的验证算法;
     * 
     * @param token
     *            客户端传入的加密证书
     * @param info
     *            服务器已知的用户信息 如果得到服务器摘要与客户端的摘要一致，则返回<code>true</code>
     * @see StatelessInfo
     * @see StatelessToken
     * @return 用于验证验证的成功与否.
     */

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
    {
        StatelessInfo statelessInfo = (StatelessInfo)info;
        StatelessToken statelessToken = (StatelessToken)token;
        matchChain.reuse();
        return matchChain.doMatch(statelessInfo.getCredentialCandidates(), statelessInfo,
            statelessToken);
    }
}

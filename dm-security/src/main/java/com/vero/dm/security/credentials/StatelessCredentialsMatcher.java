package com.vero.dm.security.credentials;


import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;

import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessRealm;
import com.vero.dm.security.realm.StatelessToken;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 1:29 2017/9/14.
 * @since data-mining-platform
 */

@Slf4j
public class StatelessCredentialsMatcher extends HashedCredentialsMatcher
{
    // private UserPasswordService passwordService;
    private StatelessCredentialsService statelessCredentialsService;

    private TokenManager tokenManager;

    @Autowired
    public void setStatelessCredentialsService(StatelessCredentialsService statelessCredentialsService)
    {
        this.statelessCredentialsService = statelessCredentialsService;
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
        Hash serverDigestHash = computeServerDigest(statelessInfo);
        return equals(statelessToken.getClientDigest(), serverDigestHash);
    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager)
    {
        this.tokenManager = tokenManager;
    }

    /**
     * 客户端与服务器协商好的加密算法;
     * 
     * @param statelessInfo
     *            用以生成客户端的消息摘要
     * @see StatelessRealm#doGetAuthenticationInfo(AuthenticationToken)
     */
    private Hash computeServerDigest(StatelessInfo statelessInfo)
    {
        return statelessCredentialsService.computeHashWithParams(statelessInfo,
            getHashIterations());
    }

    @Override
    protected boolean equals(Object tokenCredentials, Object accountCredentials)
    {
        String accountBase64 = ((Hash)accountCredentials).toBase64();
        log.debug("tokenCredentials:{},  accountCredentials:{}", tokenCredentials,
            ((Hash)accountCredentials).toBase64());
        return tokenCredentials.equals(accountBase64);
    }
}

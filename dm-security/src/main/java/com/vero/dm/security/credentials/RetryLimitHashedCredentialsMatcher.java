package com.vero.dm.security.credentials;


import com.dm.org.security.UserPasswordService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 21:39 2017/7/16.
 * @description
 * @modified by:
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher
{

    private Cache<String, AtomicInteger> passwordRetryCache;

    private UserPasswordService passwordService;

    @Autowired
    public void setPasswordService(UserPasswordService passwordService)
    {
        this.passwordService = passwordService;
    }

    @Autowired
    public void setEhCacheManager(EhCacheManager ehCacheManager)
    {
        passwordRetryCache = ehCacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
    {
        String username = (String)token.getPrincipal();
        String accountCredentials = (String) info.getCredentials();
        Object submittedCredential = token.getCredentials();
        ByteSource credentialsSalt = getCredentialsSalt(info);
        AtomicInteger retryCount = getPasswordRetryCount(username);

        if (retryCount.incrementAndGet() > 5)
        {
            throw new ExcessiveAttemptsException();
        }

        boolean isMatched = passwordService.passwordMatch(submittedCredential,accountCredentials,credentialsSalt);
        if (isMatched)
        {
            passwordRetryCache.remove(username);
        }
        return isMatched;
    }

    private ByteSource getCredentialsSalt(AuthenticationInfo info)
     {
         if(info instanceof SaltedAuthenticationInfo)
        return ((SaltedAuthenticationInfo) info).getCredentialsSalt();
         else
             throw new  AuthenticationException();
    }

    private AtomicInteger getPasswordRetryCount(String username)
    {
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null)
        {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        return retryCount;
    }
}

package com.dm.org.security.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  21:39 2017/7/16.
 * @description
 * @modified by:
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher
 {

     private Cache<String, AtomicInteger> passwordRetryCache;

     public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager)
     {
         passwordRetryCache = cacheManager.getCache("passwordRetryCache");
     }
     @Override
     public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
     {
         String username = (String)token.getPrincipal();
         //retry count + 1
         AtomicInteger retryCount = passwordRetryCache.get(username);
         if(retryCount == null) {
             retryCount = new AtomicInteger(0);
             passwordRetryCache.put(username, retryCount);
         }
         if(retryCount.incrementAndGet() > 5) {
             //if retry count > 5 throw
             throw new ExcessiveAttemptsException();
         }

         boolean matches = super.doCredentialsMatch(token, info);
         if(matches) {
             //clear retry count
             passwordRetryCache.remove(username);
         }
         return matches;
     }


 }

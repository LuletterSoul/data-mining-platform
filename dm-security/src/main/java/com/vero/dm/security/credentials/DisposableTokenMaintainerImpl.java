package com.vero.dm.security.credentials;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.vero.dm.exception.auth.ConcurrentAccessException;
import com.vero.dm.exception.auth.EmptyTokenListException;
import com.vero.dm.exception.auth.ExpiredCredentialsException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.service.UserService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:38 2018/2/13.
 * @since data-mining-platform
 */
@Profile(value = {"prod","dev","test"})
@SuppressWarnings("unchecked")
@Slf4j
@Component
@Getter
public class DisposableTokenMaintainerImpl implements DisposableTokenMaintainer
{
    /**
     * 每个一次性Token组的最大Size
     */
    private Integer cacheListSize = 20;

    /**
     * 写锁超时时间
     */
    private long writeLockTimeOut = 3000;

    private long readLockTimeOut = 3000;

    private Ehcache disposableTokenCache;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private TokenExpiredChecker expiredChecker;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    public void setDisposableTokenCache(CacheManager cacheManager)
    {
        this.disposableTokenCache = cacheManager.getCache("disposableTokenCache");
    }

    @Override
    public String signToken(String key, String privateSalt)
    {
        try
        {
            disposableTokenCache.tryWriteLockOnKey(key, getWriteLockTimeOut());
            Element element = disposableTokenCache.get(key);
            element = insureTokenListNotNull(key, element);
            LinkedList<String> cachedList = (LinkedList<String>)element.getObjectValue();
            return writeNewToken(key, privateSalt, cachedList);
        }
        catch (InterruptedException e)
        {
            String message = "Could not fetch disposable token cache Write Lock.";
            throw new ConcurrentAccessException(message, ExceptionCode.ConcurrencyError);
        }
        finally
        {
            disposableTokenCache.releaseWriteLockOnKey(key);
        }
    }

    private Element insureTokenListNotNull(String key, Element element)
    {
        if (element == null)
        {
            element = new Element(key, new LinkedList<String>());
            disposableTokenCache.put(element);
        }
        return element;
    }

    private String writeNewToken(String key, String privateSalt, LinkedList<String> cachedList)
    {
        String disposableToken = tokenGenerator.generateExpiredToken(key,
            userService.fetchPrivateSalt(privateSalt));
        // 超过阈值,删除列头
        if (cachedList.size() + 1 > getCacheListSize())
        {
            cachedList.removeFirst();
        }
        cachedList.addLast(disposableToken);
        if (log.isDebugEnabled())
        {
            log.debug("Sign a new disposable [{}] token for [{}]", disposableToken, key);
        }
        disposableTokenCache.put(new Element(key, cachedList));
        return disposableToken;
    }

    @Override
    public String queryLatestToken(String key)
    {
        return ((LinkedList<String>)retrieveTokenList(key)).getLast();
    }

    @Override
    public List<String> retrieveTokenList(String key)
    {
        try
        {
            disposableTokenCache.tryReadLockOnKey(key, getReadLockTimeOut());
            if (expiredChecker.isTokenExpired(key))
            {
                log.error("Unexpected Expired access token [{}].", key);
                String message = key + " turned out expired,Please re-apply again.";
                throw new ExpiredCredentialsException(message, ExceptionCode.ExpiredToken);
            }
            else
            {
                List<String> tokenList = (List<String>)disposableTokenCache.get(
                    key).getObjectValue();
                if (tokenList == null || tokenList.isEmpty())
                {
                    String message = "Empty token list.";
                    throw new EmptyTokenListException(message, ExceptionCode.TokenListNotFound);
                }
                return tokenList;
            }
        }
        catch (InterruptedException e)
        {
            String message = "Could not fetch disposable token cache Read Lock.";
            throw new ConcurrentAccessException(message, ExceptionCode.ConcurrencyError);
        }
        finally
        {
            disposableTokenCache.releaseReadLockOnKey(key);
        }
    }

    @Override
    public void cleanTokenList(String key)
    {
        try
        {
            disposableTokenCache.tryWriteLockOnKey(key, getWriteLockTimeOut());
            disposableTokenCache.remove(key);
        }
        catch (InterruptedException e)
        {
            String message = "Could not fetch disposable token cache Write Lock.";
            throw new ConcurrentAccessException(message, ExceptionCode.ConcurrencyError);
        }
        finally
        {
            disposableTokenCache.releaseWriteLockOnKey(key);
        }
    }
}

package com.vero.dm.security.credentials;


import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.vero.dm.exception.auth.ConcurrentAccessException;
import com.vero.dm.exception.auth.ExpiredCredentialsException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.service.UserService;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:38 2018/2/13.
 * @since data-mining-platform
 */
@SuppressWarnings("unchecked")
@Slf4j
@Component
public class DisposableTokenMaintainerImpl implements DisposableTokenMaintainer
{
    /**
     * 每个一次性Token组的最大Size
     */
    private Integer cacheListSize = 5;

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
    public void signToken(String key, String privateSalt)
    {
        List<String> cachedList = (List<String>)disposableTokenCache.get(key).getObjectValue();
        try
        {
            disposableTokenCache.tryWriteLockOnKey(key, writeLockTimeOut);
            writeNewToken(key, privateSalt, cachedList);
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

    private void writeNewToken(String key, String privateSalt, List<String> cachedList)
    {
        if (cachedList == null)
        {
            cachedList = new LinkedList<>();
        }
        else
        {
            String disposableToken = tokenGenerator.generateExpiredToken(key,
                userService.fetchPrivateSalt(privateSalt));
            // 超过阈值,删除列头
            if (cachedList.size() + 1 > cacheListSize)
            {
                cachedList.remove(0);
            }
            cachedList.add(disposableToken);
        }
        disposableTokenCache.put(new Element(key, cachedList));
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
            disposableTokenCache.tryReadLockOnKey(key, readLockTimeOut);
            if (expiredChecker.isTokenExpired(key))
            {
                log.info("Unexpected Expired access token [{}].", key);
                String message = key + " turned out expired,Please re-apply again.";
                throw new ExpiredCredentialsException(message, ExceptionCode.ExpiredToken);
            }
            else
                return (List<String>)disposableTokenCache.get(key).getObjectValue();
        }
        catch (InterruptedException e)
        {
            String message = "Could not fetch disposable token cache Read Lock.";
            throw new ConcurrentAccessException(message, ExceptionCode.ConcurrencyError);
        }
        finally
        {
            disposableTokenCache.releaseWriteLockOnKey(key);
        }
    }
}

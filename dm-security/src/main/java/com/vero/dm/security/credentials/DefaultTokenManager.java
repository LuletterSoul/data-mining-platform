package com.vero.dm.security.credentials;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vero.dm.service.UserService;
import com.vero.dm.util.DateStyle;
import com.vero.dm.util.DateUtil;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:03 2017/9/15.
 * @since data-minning-platform
 */

@Service
@Slf4j
@Transactional
public class DefaultTokenManager implements TokenManager
{
    private Cache<String, String> tokenCache;

    private UserService userService;

    // private UserDao userDao;

    public final static int DEFAULT_TIME_OUT_LIMIT = 3;

    private int timeOutInternal;

    private StatelessCredentialsService credentialsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTokenManager.class);

    public DefaultTokenManager()
    {
        // 默认有效期为3分钟
        this.timeOutInternal = DEFAULT_TIME_OUT_LIMIT;
    }

    @Autowired
    public void setTokenCache(EhCacheManager ehCacheManager)
    {
        this.tokenCache = ehCacheManager.getCache("tokenCache");
    }


    @Autowired
    public void setCredentialsService(StatelessCredentialsService credentialsService)
    {
        this.credentialsService = credentialsService;
    }

     @Autowired
     @Qualifier("userServiceImpl")
     public void setUserService(UserService userService)
     {
     this.userService = userService;
     }

    @Override
    public String generateTimeOutToken(String username)
    {
        Date date = new Date();
        String formattedDate = dateToString(date);
        String privateSalt = userService.fetchPrivateSalt(username);
        // String publicSalt = userService.fetchPublicSalt(username);
        // token.setPublicSalt(publicSalt);
        return generateEncryptedToken(privateSalt, username, formattedDate);
    }

    private String generateEncryptedToken(String privateSalt, String username,
                                          String formattedDate)
    {
        return credentialsService.digest(privateSalt + username, formattedDate).toBase64();
    }

    private String dateToString(Date date)
    {
        return DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
    }

    @Override
    public ClientToken getTimeOutToken(String username, String dateString)
    {
        if (!checkTokenValidity(dateString))
        {
            throw new ExpiredCredentialsException("Invalid token.");
        }
        String token = generateTimeOutToken(username);
        String apiKey = UUID.randomUUID().toString();
        tokenCache.put(apiKey, token);
        return new ClientToken(token, apiKey);
    }

    @Override
    public String getHashToken(String key)
    {
        return tokenCache.get(key);
    }

    /**
     * 验证每个对一次行证书申请的时间，对于无效期内的request直接拒绝申请 主要是为了防止重放攻击，与试图申请过期证书的非法操作
     * 
     * @param dateString
     *            每一次http传入的时间戳
     * @return 判断当前时间是否在有效期内
     */
    private boolean checkTokenValidity(String dateString)
    {
        Date date = DateUtil.StringToDate(dateString, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
        Date now = new Date();
        float internal = (now.getTime() - (date != null ? date.getTime() : 0)) / 60000;
        return !(internal > timeOutInternal);
    }

    @Override
    public boolean cleanTokenCache(String username)
    {
        String tokenHash = tokenCache.get(username);
        if (tokenHash != null)
        {
            tokenCache.remove(username);
            return true;
        }
        return false;
    }
}

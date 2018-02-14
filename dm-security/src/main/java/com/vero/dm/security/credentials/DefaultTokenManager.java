package com.vero.dm.security.credentials;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vero.dm.exception.auth.AccessTokenNotExistException;
import com.vero.dm.exception.auth.ExpiredCredentialsException;
import com.vero.dm.exception.auth.IncorrectCredentialsException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.model.User;
import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.service.UserService;
import com.vero.dm.util.DateStyle;
import com.vero.dm.util.DateUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:03 2017/9/15.
 * @since data-minning-platform
 */

@Service
@Slf4j
@Transactional
public class DefaultTokenManager implements TokenManager, TokenExpiredChecker
{

    /**
     * Ehcache 缓存令牌 有效期为半小时
     */
    private Ehcache currentDisposableTokenCache;

    private Ehcache accessTokenCache;

    @Autowired
    private TokenGenerator tokenGenerator;

    private float timeOutInternal = 2;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    private StatelessCredentialsComputer credentialsService;

    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    public void setCurrentDisposableTokenCache(CacheManager cacheManager)
    {
        this.currentDisposableTokenCache = cacheManager.getCache("currentDisposableTokenCache");
    }

    @Autowired
    public void setAccessTokenCache(CacheManager cacheManager)
    {
        this.accessTokenCache = cacheManager.getCache("accessTokenCache");
    }

    @Override
    public String applyExpiredToken(String username, String providedCredential, String dateString)
    {
        validateTokenDate(username, dateString);
        // 先清除之前的令牌信息
        cleanPreApplicationInfo(providedCredential);
        if (tokenValidator.validate(username, providedCredential))
        {
            User user = userService.fetchByUserName(username);
            // 生成访问证书
            String accessToken = tokenGenerator.generateHighSecurityTAccessToken(username,
                userService.fetchPrivateSalt(username));
            // 签发证书,记录申请的令牌信息,进行超时记录;
            signAccessToken(accessToken, user);
            log.debug("Generate access token applied to [{}].", user.getUsername());
            return accessToken;
        }
        else
        {
            String message = "Mismatch the correct password.";
            throw new IncorrectCredentialsException(message, ExceptionCode.InvalidAccount);
        }
    }


    /**
     * 清空所有登录缓存
     * 
     * @param accessToken
     *            令牌
     */
    private void cleanPreApplicationInfo(String accessToken)
    {
        if (!cleanTokenCache(accessToken))
        {
            log.debug("[{}] hasn't apply token during period time.", accessToken);
        }
    }


    private void validateTokenDate(String username, String dateString)
    {
        if (!checkTokenValidity(dateString))
        {
            log.info("Expired token from [{}].", username);
            throw new ExpiredCredentialsException("Invalid token.", ExceptionCode.ExpiredToken);
        }
    }

    /**
     * 混入私盐、用户名、当前时间生成一个可传递的令牌
     * 
     * @param privateSalt
     *            私盐
     * @param username
     *            用户名
     * @param formattedDate
     *            证书生成日期
     * @return 生成的证书
     */
    private String generateMixedToken(String privateSalt, String username, String formattedDate)
    {
        return credentialsService.digest(privateSalt + username, formattedDate).toBase64();
    }

    private String dateToString(Date date)
    {
        return DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS.getValue());
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
    public boolean cleanTokenCache(String key)
    {
        return currentDisposableTokenCache.remove(key) && accessTokenCache.remove(key);
    }

    @Override
    public boolean isTokenExpired(String accessToken)
    {
        if (accessTokenCache.isKeyInCache(accessToken))
        {
            Element element = accessTokenCache.get(accessToken);
            if (element == null || accessTokenCache.isExpired(element))
            {
                log.info("Detect a expired token [{}] from [{}].", accessToken);
                return true;
            }
            else
                return false;
        }
        else
        {
            String message = accessToken + "is invalid.Required apply an access token before.";
            throw new AccessTokenNotExistException(message, ExceptionCode.TokenNotExist);
        }
    }

    @Override
    public void signAccessToken(String accessToken, User user)
    {
        List<String> roleNames = userService.findRoleNameSetByUserName(user.getUsername());
        List<String> permissionNames = userService.findPermissionNameSet(user.getUsername());
        accessTokenCache.put(
            new Element(accessToken, UserDto.build(user, roleNames, permissionNames)));
    }

    @Override
    public boolean isAccessTokenRegistered(String accessToken)
    {
        return accessTokenCache.get(accessToken) != null;
    }

}

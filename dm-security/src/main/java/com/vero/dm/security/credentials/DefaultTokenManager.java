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
import com.vero.dm.util.date.DateStyle;
import com.vero.dm.util.date.DateUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import javax.servlet.http.HttpServletResponse;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:03 2017/9/15.
 * @since data-mining-platform
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

    private Ehcache usernameToTokenCache;

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
    private DisposableTokenWriter disposableTokenWriter;

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

    @Autowired
    public void setUsernameToTokenCache(CacheManager cacheManager)
    {
        this.usernameToTokenCache= cacheManager.getCache("usernameToTokenCache");
    }



    @Override
    public String applyExpiredToken(String username, String providedCredential, String dateString, HttpServletResponse response)
    {
        validateTokenDate(username, dateString);
        // 先清除之前的令牌信息
        cleanPreTokenAuth(username);
        if (tokenValidator.validate(username, providedCredential))
        {
            User user = userService.fetchByUserName(username);
            // 生成访问证书;
            String accessToken = tokenGenerator.generateHighSecurityTAccessToken(username,
                userService.fetchPrivateSalt(username));
            // 签发证书,记录申请的令牌信息,进行超时记录;
            // 更新最后一次访问(申请令牌)的时间;
            signAccessToken(accessToken, user);
            log.debug("Generate access token applied to [{}].", user.getUsername());
            log.info("User [{}] last login time: [{}]", user.getUsername(),
                user.getLastLoginTime());
            disposableTokenWriter.write(response,accessToken);
            return accessToken;
        }
        else
        {
            String message = "Mismatch the correct password.";
            log.info("");
            throw new IncorrectCredentialsException(message, ExceptionCode.InvalidAccount);
        }
    }

    /**
     * 清空所有登录缓存
     * 
     * @param username
     *            令牌
     */
    private void cleanPreTokenAuth(String username)
    {
        if (!cleanTokenCache(username))
        {
            log.debug("[{}] hasn't apply token during period time.", username);
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
    public boolean cleanTokenCache(String username)
    {
        accessTokenCache.evictExpiredElements();
        Element entry = usernameToTokenCache.get(username);
        if(entry == null || usernameToTokenCache.isExpired(entry)){
            usernameToTokenCache.evictExpiredElements();
            log.debug("Expired token of User:[{}].", username);
            return false;
        }
        else{
            String accessToken = (String) entry.getObjectValue();
            accessTokenCache.remove(accessToken);
            log.info("Clean pre exist access token of User:[{}]", username);
            return true;
        }
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
        UserDto userDto = UserDto.build(user, roleNames, permissionNames);
        userDto.setLastLoginTime(new Date());
        log.info("User [{}]:[{}] last login time: [{}]", userDto.getUsername(), userDto.getName(),
            userDto.getLastLoginTime());
        userService.updateUser(userDto, accessToken);
        mapUsernameToToken(accessToken, user);
    }

    private void mapUsernameToToken(String accessToken, User user) {
        usernameToTokenCache.remove(user.getUsername());
        usernameToTokenCache.put(new Element(user.getUsername(), accessToken));
    }

    @Override
    public boolean isAccessTokenRegistered(String accessToken)
    {
        return accessTokenCache.get(accessToken) != null;
    }

}

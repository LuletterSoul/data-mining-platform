package com.vero.dm.security.credentials;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vero.dm.exception.auth.ExpiredCredentialsException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.repository.dto.UserDto;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:33 2018/2/10.
 * @since data-mining-platform
 */

@Component
@Slf4j
public class UserProfileAccessorImpl implements UserProfileAccessor
{
    private Ehcache accessTokenCache;

    @Autowired
    private TokenExpiredChecker tokenExpiredChecker;

    @Autowired
    public void setAccessTokenCache(CacheManager cacheManager)
    {
        this.accessTokenCache = cacheManager.getCache("accessTokenCache");
    }

    @Override
    public UserDto fetchProfile(String accessToken)
    {
        if (!tokenExpiredChecker.isTokenExpired(accessToken))
        {
            UserDto userDto = (UserDto)accessTokenCache.get(accessToken).getObjectValue();
            log.debug("[{}] fetch user profile.", userDto.getUsername());
            return userDto;
        }
        else
        {
            log.info("A Request post a expired token [{}].", accessToken);
            String message = accessToken + " turned out expired,Please re-apply again.";
            throw new ExpiredCredentialsException(message, ExceptionCode.ExpiredToken);
        }
    }
}

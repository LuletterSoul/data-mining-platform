package com.vero.dm.security.credentials;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.vero.dm.security.constants.Constants;
import com.vero.dm.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:36 2018/2/12.
 * @since data-mining-platform
 */
@Profile(value = {"dev","prod","test"})
@Component
@Slf4j
public class SimpleDisposableTokenWriter implements DisposableTokenWriter
{
    @Autowired
    private UserProfileAccessor profileAccessor;

    @Autowired
    private DisposableTokenMaintainer tokenMaintainer;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Override
    public void write(HttpServletResponse httpServletResponse, String accessToken)
    {
        String username = profileAccessor.fetchProfile(accessToken).getUsername();
        String privateSalt = userService.fetchPrivateSalt(username);
        updateDisposableToken(httpServletResponse, accessToken, username, privateSalt);
    }

    /**
     * 设置客户端下次请求的一次性令牌
     *
     * @param httpServletResponse
     * @param accessToken
     * @param username
     * @param privateSalt
     */
    private void updateDisposableToken(HttpServletResponse httpServletResponse, String accessToken,
                                       String username, String privateSalt)
    {
        String newToken = tokenMaintainer.signToken(accessToken, privateSalt);
        // 写入一次性令牌附带返回客户端
        httpServletResponse.setHeader(Constants.DISPOSABLE_TOKEN_HEADER, newToken);
        if (log.isDebugEnabled())
        {
            log.debug("Update disposable token as [{}] form [{}].", newToken, username);
        }
    }
}

package com.vero.dm.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.ClientToken;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.service.UserService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:27 2018/1/23.
 * @since data-mining-platform
 */
@Slf4j
@RestController
public class CredentialsController
{
    private UserService userService;

    private TokenManager tokenManager;

    @Autowired
    public void setTokenManager(TokenManager tokenManager)
    {
        this.tokenManager = tokenManager;
    }

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @RequestMapping(value = "/token/{username}", method = RequestMethod.GET)
    public ClientToken getToken(@RequestHeader(Constants.HEADER_TIMESTAMP) String timestamp,
                                @PathVariable("username") String username)
    {
        return tokenManager.getTimeOutToken(username, timestamp);
    }

    @RequestMapping(value = "/public_salt/{username}", method = RequestMethod.GET)
    public String publicSalt(@PathVariable("username") String username)
    {
        return userService.fetchPublicSalt(username);
    }
}

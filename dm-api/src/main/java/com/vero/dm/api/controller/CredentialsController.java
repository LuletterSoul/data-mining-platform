package com.vero.dm.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.ClientToken;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("根据用户名获取访问令牌")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/tokens/{username}")
    public ClientToken getToken(@RequestHeader(Constants.HEADER_TIMESTAMP) String timestamp,
                                @PathVariable("username") String username)
    {
        return tokenManager.getTimeOutToken(username, timestamp);
    }

    @Cacheable(cacheNames = "userPublicSaltCache")
    @ApiOperation("根据用户名获取公盐")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/public_salts/{username}")
    public String publicSalt(@PathVariable("username") String username)
    {
        return userService.fetchPublicSalt(username);
    }
}

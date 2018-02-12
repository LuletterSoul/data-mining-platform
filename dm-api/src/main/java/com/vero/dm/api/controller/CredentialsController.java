package com.vero.dm.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.security.constants.Constants;
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

    @ApiOperation("申请访问令牌")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "timestamp", value = "请求时间戳", dataType = "String", paramType = "header", required = true),
        @ApiImplicitParam(name = "providedCredential", value = "认证信息", dataType = "String", paramType = "header", required = true)})
    @GetMapping(value = "/tokens/{username}")
    public String getExpiredToken(@RequestHeader(Constants.TIMESTAMP_HEADER) String timestamp,
                                  @RequestHeader(Constants.APPLY_CREDENTIAL) String providedCredential,
                                  @PathVariable("username") String username)
    {
        return tokenManager.applyExpiredToken(username, providedCredential, timestamp);
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

//    @Cacheable(cacheNames = "userPublicSaltCache")
//    @ApiOperation("根据用户名获取公盐")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "path", required = true)})
//    @GetMapping(value = "/users/profile")
//    public ResponseEntity<UserDto> profile(@RequestHeader(Constants.TIMESTAMP_HEADER) String timestamp,
//                                           @RequestHeader(Constants.ACCESS_TOKEN_HEADER) String accessToken)
//    {
//
//    }
}

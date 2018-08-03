package com.vero.dm.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.User;
import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.security.credentials.UserProfileAccessor;
import com.vero.dm.service.UserService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:40 2017/7/20.
 * @since data-mining-platform
 */
@Profile(value = {"prod", "dev", "test"})
@RestController
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.USER_PATH)
@Slf4j
public class UserController
{
    private UserService userService;

    // private UserPasswordService passwordService;

    private TokenManager tokenManager;

    private UserProfileAccessor profileAccessor;

    private StatelessCredentialsServer credentialsService;

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

    @Autowired
    public void setCredentialsService(StatelessCredentialsServer credentialsService)
    {
        this.credentialsService = credentialsService;
    }

    @Autowired
    public void setProfileAccessor(UserProfileAccessor profileAccessor)
    {
        this.profileAccessor = profileAccessor;
    }

    @ApiOperation("根据用户名查询用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "accessToken", value = "令牌", dataType = "String", paramType = "header", required = true)})
    @GetMapping(value = "/profile")
    public UserDto profile(@RequestHeader(Constants.ACCESS_TOKEN_HEADER) String accessToken)
    {
        return profileAccessor.fetchProfile(accessToken);
    }

    @ApiOperation("注册用户")
    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody User user)
    {
        return new ResponseEntity<>(credentialsService.registerUser(user), HttpStatus.CREATED);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto,
                          @RequestHeader(Constants.ACCESS_TOKEN_HEADER) String accessToken)
    {
        return userService.updateUser(userDto, accessToken);
    }

    @ApiOperation("创建一个用户名,重复的用户名将创建失败，用于账户创建时的重复校验")
    @PostMapping(value = "/username")
    public boolean createUsername(String username)
    {
        return userService.createUsername(username);
    }

    // @ApiOperation("根据用户名的获取权限角色")
    // @GetMapping(value = "/roles")
    // public List<String> roles(@RequestHeader(Constants.ACCESS_TOKEN_HEADER) String accessToken)
    // {
    // return userService.findRoleNameSetByUserName(username);
    // }

    // @ApiOperation(value = "用户注销")
    // @PostMapping(value = "/preLogout")
    // public String preLogout(@RequestHeader(Constants.ACCESS_TOKEN_HEADER) String accessToken)
    // {
    // tokenManager.cleanTokenCache(accessToken);
    // tokenMaintainer.cleanTokenList(accessToken);
    // return "Logout Success";
    // }
}

package com.vero.dm.api.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.User;
import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.ClientToken;
import com.vero.dm.security.credentials.StatelessCredentialsService;
import com.vero.dm.security.credentials.TokenManager;
import com.vero.dm.service.UserService;
import com.vero.dm.service.constant.ResourcePath;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:40 2017/7/20.
 * @since data-mining-platform
 */
@RestController
@RequestMapping(value = ApiVersion.API_VERSION + ResourcePath.USER_PATH)
@Slf4j
public class UserController
{
    private UserService userService;

    // private UserPasswordService passwordService;

    private TokenManager tokenManager;

    private StatelessCredentialsService credentialsService;

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
    public void setCredentialsService(StatelessCredentialsService credentialsService)
    {
        this.credentialsService = credentialsService;
    }

    @ApiOperation("根据用户名查询用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", paramType = "path", required = true)})
    @GetMapping(value = "/{username}")
    public UserDto profile(@ApiParam(value = "用户名", required = true) @PathVariable("username") String username)
    {
        return userService.getUserProfile(username);
    }

    @ApiOperation("注册用户")
    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody User user)
    {
        return new ResponseEntity<>(credentialsService.registerUser(user), HttpStatus.CREATED);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto)
    {
        return userService.updateUser(userDto);
    }

    @ApiOperation("根据用户名的获取权限角色")
    @GetMapping(value = "/{username}/roles")
    public List<String> roles(@PathVariable("username") String username)
    {
        return userService.findRoleNameSetByUserName(username);
    }



    @ApiOperation(value = "用户注销")
    @PostMapping(value = "/logout")
    public String logout(@RequestParam("username") String username)
    {
        tokenManager.cleanTokenCache(username);
        return "Logout Success";
    }
}

package com.vero.dm.api.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vero.dm.repository.dto.UserDto;
import com.vero.dm.security.credentials.TokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vero.dm.model.User;
import com.vero.dm.security.constants.Constants;
import com.vero.dm.security.credentials.ClientToken;
import com.vero.dm.service.UserService;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:40 2017/7/20.
 * @since data-mining-platform
 */
@RestController
@RequestMapping(value = ApiVersion.API_VERSION + "/user")
public class UserController
{
    private UserService userService;

    // private UserPasswordService passwordService;

    private TokenManager tokenManager;

    // private StatelessCredentialsService credentialsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    // @Autowired
    // public void setPasswordService(UserPasswordService passwordService)
    // {
    // this.passwordService = passwordService;
    // }

//     @Autowired
        // public void setTokenManager(TokenManager tokenManager)
        // {
        // this.tokenManager = tokenManager;
        // }

    // @Autowired
    // public void setCredentialsService(StatelessCredentialsService credentialsService) {
    // this.credentialsService = credentialsService;
    // }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public UserDto profile(@PathVariable("username") String username)
    {
        return userService.getUserProfile(username);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDto> register(@RequestBody User user)
    {
        return new ResponseEntity<UserDto>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public UserDto update(@RequestBody UserDto userDto)
    {
        return userService.updateUser(userDto);
    }

    @RequestMapping(value = "/{username}/roles", method = RequestMethod.GET)
    public List<String> roles(@PathVariable("username") String username)
    {
        return userService.findRoleNameSetByUserName(username);
    }

    @RequestMapping(value = "/{username}/token", method = RequestMethod.GET)
    public ClientToken getToken(@RequestHeader(Constants.HEADER_TIMESTAMP) String timestamp,
                                @PathVariable("username") String username)
    {
        return tokenManager.getTimeOutToken(username, timestamp);
    }

    @RequestMapping(value = "/{username}/publicSalt", method = RequestMethod.GET)
    public String publicSalt(@PathVariable("username") String username)
    {
        return userService.fetchPublicSalt(username);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, User> login(@RequestParam(Constants.PARAM_USERNAME) String username)
    {
        Map<String, User> userMap = new LinkedHashMap<String, User>();
        userMap.put("userProfile", userService.fetchByUserName(username));
        return userMap;
    }

    @RequestMapping(value = "/logout")
    public String logout(@RequestParam("username") String username)
    {
        tokenManager.cleanTokenCache(username);
        return "Logout Success";
    }
}

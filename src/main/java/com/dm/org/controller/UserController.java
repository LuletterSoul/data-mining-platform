package com.dm.org.controller;


import com.dm.org.model.User;
import com.dm.org.security.UserPasswordService;
import com.dm.org.security.constants.Constants;
import com.dm.org.security.credentials.TimeOutToken;
import com.dm.org.security.credentials.TokenManager;
import com.dm.org.service.StatelessCredentialsService;
import com.dm.org.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:40 2017/7/20.
 * @since data-minning-platform
 */
@RestController
@RequestMapping(value = "/user")
public class UserController
{
    private UserService userService;

    private UserPasswordService passwordService;

    private TokenManager tokenManager;

    private StatelessCredentialsService credentialsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordService(UserPasswordService passwordService)
    {
        this.passwordService = passwordService;
    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager)
    {
        this.tokenManager = tokenManager;
    }

    @Autowired
    public void setCredentialsService(StatelessCredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public User profile(@PathVariable String userName)
    {
        return userService.fetchByUserName(userName);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody User user)
    {
        return userService.registerUser(user);
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public TimeOutToken getToken(@RequestHeader(Constants.HEADER_TIMESTAMP) String timestamp,
                                 @RequestParam(Constants.PARAM_USERNAME) String username)
    {
        return tokenManager.getTimeOutToken(username, timestamp);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, User> login(@RequestParam(Constants.PARAM_USERNAME) String username)
    {
        Map<String, User> userMap = new LinkedHashMap<String, User>();
        userMap.put("userProfile", userService.fetchByUserName(username));
        return userMap;
    }
}

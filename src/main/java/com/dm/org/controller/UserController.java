package com.dm.org.controller;


import com.dm.org.dto.UserDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:40 2017/7/20.
 * @since data-mining-platform
 */
@RestController
@RequestMapping(value = "/user")
public class UserController
{
    private UserService userService;

//    private UserPasswordService passwordService;

    private TokenManager tokenManager;

//    private StatelessCredentialsService credentialsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    @Qualifier("userServiceImpl")
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

//    @Autowired
//    public void setPasswordService(UserPasswordService passwordService)
//    {
//        this.passwordService = passwordService;
//    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager)
    {
        this.tokenManager = tokenManager;
    }

//    @Autowired
//    public void setCredentialsService(StatelessCredentialsService credentialsService) {
//        this.credentialsService = credentialsService;
//    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public UserDTO profile(@PathVariable("username") String username)
    {
        return UserDTO.build(userService.findByUserName(username));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTO> register(@RequestBody User user)
    {
        UserDTO userDTO = UserDTO.build(userService.registerUser(user));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{username}/roles",method = RequestMethod.GET)
    public List<String> roles(@PathVariable("username") String username) {
        return userService.findRoleNameSetByUserName(username);
    }


    @RequestMapping(value = "/{username}/token",method = RequestMethod.GET)
    public String getToken(@RequestHeader(Constants.HEADER_TIMESTAMP) String timestamp,
                                 @PathVariable("username") String username)
    {
        return tokenManager.getTimeOutToken(username, timestamp);
    }

    @RequestMapping(value = "/{username}/publicSalt",method = RequestMethod.GET)
    public String publicSalt(@PathVariable("username") String username) {
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
    public String logout(@RequestParam("username") String username) {
        tokenManager.cleanTokenCache(username);
        return "Logout Success";
    }
}

package com.dm.org.controller;


import com.dm.org.model.User;
import com.dm.org.security.UserPasswordService;
import com.dm.org.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setPasswordService(UserPasswordService passwordService)
    {
        this.passwordService = passwordService;
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public User profile(@PathVariable String userName)
    {
        return userService.fetchByUserName(userName);
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public User register(@RequestBody User user)
    {
        String randomSalt = passwordService.generateRandomSalt(16) + user.getUserName();
        String encryptedPassword = passwordService.encryptPasswordWithSalt(user.getPassword(), randomSalt);
        user.setSalt(randomSalt);
        user.setPassword(encryptedPassword);
        return userService.registerUser(user);
    }
}

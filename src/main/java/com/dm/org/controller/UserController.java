package com.dm.org.controller;


import com.dm.org.model.User;
import com.dm.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author ÁõÏéµÂ qq313700046@icloud.com .
 * @date created in 21:08 2017/6/29.
 * @description
 * @modified by:
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserController
{
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/demo/addUser")
    public User saveUser()
    {
        return userService.save();
    }

    @ResponseBody
    @RequestMapping(value = "/demo/findAllUsers")
    public List<User> findAllUsers()
    {
        return userService.findAllUsers();
    }
}

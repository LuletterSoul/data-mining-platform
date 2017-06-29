package com.dm.org.controller;

import com.dm.org.model.User;
import com.dm.org.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ÁõÏéµÂ qq313700046@icloud.com .
 * @date created in  22:49 2017/6/28.
 * @description
 * @modified by:
 */
@Controller
public class TestHibernateController
{
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    public UserService getUserService()
    {
        return userService;
    }

    @ResponseBody
    @RequestMapping(value = "/demo/saveUser")
    public User saveUser()
    {
        return userService.save();
    }

    @ResponseBody
    @RequestMapping(value="/demo/findAllUser")
    public List<User> findAllUser()
    {
        return userService.findAllUsers();
    }

}

package com.dm.org.controller;

import com.dm.org.model.User;
import com.dm.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;


/**
 * @author ����� qq313700046@icloud.com .
 * @date created in 21:08 2017/6/29.
 * @description
 * @modified by:
 */

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(value = "/user")
public class UserController
{
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/demo/addUser")
    public User saveUser(User user)
    {
        return userService.save(user);
    }

    @ResponseBody
    @RequestMapping(value = "/demo/findAllUsers")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(value = "/demo/test", method = RequestMethod.GET)
    public String mockTest(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAllAttributes(users);
        return "test";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegInfo",new User());
        return "/user/registerForm";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String processRegistration(User user)
    {
        userService.save(user);
        return "redirect:/user/"
                + user.getUserId();
    }

    @RequestMapping(value = "/{userId}",method = RequestMethod.GET)
    public String showUserProfile(@PathVariable String userId,Model model)
    {
        User user = userService.getUserById(userId);
        //model.addAttribute("userId", userId);
        model.addAttribute("userProfile", user);
        return "/user/profile";
    }
}

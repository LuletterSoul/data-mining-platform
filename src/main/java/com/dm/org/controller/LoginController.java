package com.dm.org.controller;


import com.dm.org.model.User;
import com.dm.org.security.credentials.DisposableSaltEntry;
import com.dm.org.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 1:05 2017/7/11.
 * @description
 * @modified by:
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request)
    {
//        try
//        {
//            return userService.doUserCredentialsMatch(userAndToken.user, userAndToken.entry);
//        }
//        catch (UnknownAccountException e)
//        {
//            e.printStackTrace();
//        }
//        catch (LockedAccountException e)
//        {
//            e.printStackTrace();
//        }
//        catch (ExcessiveAttemptsException e)
//        {
//            e.printStackTrace();
//        }
//        catch (AuthenticationException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
    }

    @RequestMapping(value = "/disposableSalt.json", method = RequestMethod.POST)
    @ResponseBody
    public DisposableSaltEntry generateDisposableSalted(@RequestBody String preSaltId)
    {

        return userService.getRandomVerifySaltEntry(preSaltId);
    }

    @RequestMapping(value = "/doLogin")
    @RequiresAuthentication
    public String doLogin()
    {
        return "redirect:/static/manager/data_sets.html";
    }

    static class UserAndToken
    {
        public User user;
        public DisposableSaltEntry entry;
    }

}

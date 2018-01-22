package com.vero.dm.api.controller;


import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 16:26 2017/7/31.
 * @since data-minning-platform
 */

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StaticResourcesMappingController
{
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login()
    {
        return "forward:/static/login/login.html";
    }

    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager()
    {
        return "forward:/static/manager";
    }

    @RequiresAuthentication
    @RequestMapping(value = "/manager/data_sets", method = RequestMethod.GET)
    public String dataSet()
    {
        return "forward:/static/manager/data_sets.html";
    }

    @RequiresAuthentication
    @RequestMapping(value = "/manager/students", method = RequestMethod.GET)
    public String manageStudent()
    {
        return "forward:/static/manager/students.html";
    }

    @RequiresAuthentication
    @RequestMapping(value = "/manager/tasks", method = RequestMethod.GET)
    public String manageTask()
    {
        return "forward:/static/manager/students.html";
    }

    @RequestMapping(value = "/manager/data_mining", method = RequestMethod.GET)
    public String forwardDataMining()
    {
        return "forward:/static/manager/data_mining.html";
    }
}

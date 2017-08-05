package com.dm.org.base;


import com.dm.org.controller.LoginController;
import com.dm.org.model.Permission;
import com.dm.org.service.PermissionService;
import com.dm.org.service.RoleService;
import com.dm.org.service.UserService;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 0:35 2017/7/22.
 * @since data-minning-platform
 */

public class ControllerTestingInitializer extends ConfigurationWirer
{
    protected UserService userService;

    protected RoleService roleService;

    protected PermissionService permissionService;

    protected LoginController loginController;

    @Autowired
    public void setLoginController(LoginController loginController)
    {
        this.loginController = loginController;
    }

    @Autowired
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService)
    {
        this.roleService = roleService;
    }

    @Autowired
    public void setPermissionService(PermissionService permissionService)
    {
        this.permissionService = permissionService;
    }

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }
}

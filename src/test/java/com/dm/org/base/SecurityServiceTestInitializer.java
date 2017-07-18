package com.dm.org.base;


import com.dm.org.model.Permission;
import com.dm.org.model.Role;
import com.dm.org.model.User;
import com.dm.org.enums.UserAccessStatus;
import com.dm.org.service.PermissionService;
import com.dm.org.service.RoleService;
import com.dm.org.service.UserService;
import com.dm.org.webconfig.DataMiningPlatformWebAppInitializer;
import com.dm.org.webconfig.dataSource.impl.ComboPooledDataSourceConfig;
import com.dm.org.webconfig.hibernate.HibernateConfiguration;
import com.dm.org.webconfig.security.ShiroSecurityConfiguration;
import com.dm.org.webconfig.springMvc.SpringMvcConfiguration;
import com.dm.org.webconfig.transaction.HibernateTransactionConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 16:06 2017/7/15.
 * @description
 * @modified by:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DataMiningPlatformWebAppInitializer.class,
                                HibernateTransactionConfig.class,
        HibernateConfiguration.class,
        SpringMvcConfiguration.class,
        ShiroSecurityConfiguration.class,
        ComboPooledDataSourceConfig.class})
public class SecurityServiceTestInitializer
{
    protected UserService userService;

    protected RoleService roleService;

    protected PermissionService permissionService;

    protected String password = "123";

    protected Permission p1;

    protected Permission p2;

    protected Permission p3;

    protected Role r1;

    protected Role r2;

    protected User u1;

    protected User u2;

    protected User u3;

    protected User u4;

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
    public void initialize()
        throws Exception
    {
        userService.deleteAll();
        roleService.deleteAll();
        permissionService.deleteAll();

        // 新增权限
        p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
        p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
        p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
        permissionService.save(p1);
        permissionService.save(p2);
        permissionService.save(p3);

        // 新增角色
        r1 = new Role("admin", "管理员", Boolean.TRUE);
        r2 = new Role("user", "用户管理员", Boolean.TRUE);
        roleService.save(r1);
        roleService.save(r2);


       //新增用户
        u1 = new User("zhang", password);
        u2 = new User("li", password);
        u3 = new User("wu", password);
        u4 = new User("wang", password);
        u4.setStatus(UserAccessStatus.LOCKED);
    }

}

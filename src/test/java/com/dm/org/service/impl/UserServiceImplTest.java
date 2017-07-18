package com.dm.org.service.impl;


import com.dm.org.base.SecurityServiceTestInitializer;
import com.dm.org.model.Permission;
import com.dm.org.model.Role;
import com.dm.org.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * UserServiceImpl Tester.
 *
 * @author XiangDe Liu qq313700046@icloud.com
 * @since
 *
 *        <pre>
 * July 15, 2017
 *        </pre>
 *
 * @version 1.0
 */
public class UserServiceImplTest extends SecurityServiceTestInitializer
{

    @Before
    public void before()
            throws Exception
    {
        userService.registerUser(u1);
        userService.registerUser(u2);
        userService.registerUser(u3);
        userService.registerUser(u4);
    }

    @After
    public void after()
            throws Exception
    {

    }

    /**
     * Method: registerUser(User user)
     */
    @Test
    public void testRegisterUser()
            throws Exception
    {

    }
    /**
     * Method: fetchUserJoinRolesById()
     */
    @Test
    public void testFetchUserJoinRolesById()
            throws Exception
    {
        userService.correlateRole(u1.getUserId(),r1.getRoleId());
        User user = userService.fetchUserJoinRolesById(u1.getUserId());
        Set<Role> roleList = user.getRoleSet();
        Assert.assertEquals(1,roleList.size());
        System.out.println(roleList);
    }

    /**
     * Method: updatePassword(String userName, String newPassword)
     */
    @Test
    public void testUpdatePassword()
            throws Exception
    {
        userService.updatePassword(u1.getUserName(), "1234");
    }

    /**
     * Method: correlateRole(String userId, Long roleId)
     */
    @Test
    public void testCorrelateRole()
            throws Exception
    {
        userService.correlateRole(u1.getUserId(),r1.getRoleId());
        userService.correlateRole(u1.getUserId(),r2.getRoleId());
        List<Role> roleList = userService.findRolesByUserName(u1.getUserName());
        Assert.assertEquals(2,roleList.size());
        Assert.assertTrue(roleList.contains(r1));
        Assert.assertTrue(roleList.contains(r2));
    }

    /**
     * Method: correlateRoles(String userId, List<Long> roleIdList)
     */
    @Test
    public void testCorrelateRoles()
            throws Exception
    {
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(r1.getRoleId());
        roleIdList.add(r2.getRoleId());
        userService.correlateRoles(u2.getUserId(), roleIdList);
        List<Role> roleList = userService.findRolesByUserName(u2.getUserName());
        Assert.assertTrue(roleList.contains(r1));
        Assert.assertTrue(roleList.contains(r2));
    }

    /**
     * Method: findByUserName(String userName)
     */
    @Test
    public void testFindByUserName()
            throws Exception
    {
        User user = userService.findByUserName(u1.getUserName());
        Assert.assertNotNull(user);
    }

    /**
     * Method: findPermissionByUserName(String userName)
     */
    @Test
    public void testFindPermissionByUserName()
            throws Exception
    {
        roleService.correlatePermission(r1.getRoleId(), p1.getPermissionId());
        roleService.correlatePermission(r1.getRoleId(), p2.getPermissionId());
        roleService.correlatePermission(r1.getRoleId(), p3.getPermissionId());
        roleService.correlatePermission(r2.getRoleId(), p1.getPermissionId());
        roleService.correlatePermission(r2.getRoleId(), p2.getPermissionId());
        userService.correlateRole(u1.getUserId(),r1.getRoleId());
        List<Permission> permissionList = userService.findPermissionByUserName(u1.getUserName());
        for (Permission permission : permissionList
                )
        {
            System.out.println(permission);
        }
        Assert.assertEquals(3,permissionList.size());
        Assert.assertTrue(permissionList.contains(p3));
    }

    /**
     * Method: findRolesByUserName(String userName)
     */
    @Test
    public void testFindRolesByUserName()
            throws Exception
    {
        userService.correlateRole(u1.getUserId(),r1.getRoleId());
        List<Role> roleList = userService.findRolesByUserName(u1.getUserName());
        Assert.assertEquals(1,roleList.size());
        Assert.assertTrue(roleList.contains(r1));
    }

    /**
     * Method: removeRole(String userId, Long roleId)
     */
    @Test
    public void testRemoveRole()
            throws Exception
    {
        testCorrelateRoles();
        userService.removeRole(u2.getUserId(),r1.getRoleId());
        List<Role> roleList = userService.findRolesByUserName(u2.getUserName());
        Assert.assertTrue(!roleList.contains(r1));
        Assert.assertEquals(roleList.size(),1);
    }

    /**
     * Method: removeRoles(String userId, List<Long> roleIdList)
     */
    @Test
    public void testRemoveRoles()
            throws Exception
    {
        testCorrelateRoles();
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(r1.getRoleId());
        roleIdList.add(r2.getRoleId());
        userService.removeRoles(u2.getUserId(), roleIdList);
        List<Role> roleList = userService.findRolesByUserName(u2.getUserName());
        Assert.assertEquals(roleList.size(), 0);
    }

    /**
     * Method: findRoleIdListByUserId(String userId)
     */
    @Test
    public void testFindRoleIdListByUserId()
            throws Exception
    {
        testCorrelateRoles();
        List<Long> roleIdList = userService.findRoleIdListByUserId(u2.getUserId());
        for (Long roleId :
                roleIdList)
        {
            System.out.println(roleId);
        }
    }
    
    /**
     * Method: findPermissionNameSet()()
     */
    @Test
    public void testFindPermissionNameSet()
                throws Exception
    {
        roleService.correlatePermission(r1.getRoleId(), p1.getPermissionId());
        roleService.correlatePermission(r1.getRoleId(), p2.getPermissionId());
        roleService.correlatePermission(r1.getRoleId(), p3.getPermissionId());
        roleService.correlatePermission(r2.getRoleId(), p1.getPermissionId());
        roleService.correlatePermission(r2.getRoleId(), p2.getPermissionId());
        userService.correlateRole(u1.getUserId(),r1.getRoleId());

        Set<String> permissionNameList = userService.findPermissionNameSet(u1.getUserName());

        for (String permissionName :
                permissionNameList) {
            System.out.println(permissionName);
        }

        Assert.assertEquals(permissionNameList.size(), 3);
        Assert.assertTrue(permissionNameList.contains(p3.getPermissionName()));
    }
    /**
     * Method: findRoleNameSetByUserName(String userName)
     */
    @Test
    public void testFindRoleNameSetByUserName()
                throws Exception
    {
        userService.correlateRole(u1.getUserId(),r1.getRoleId());
        Set<String> roleNameList = userService.findRoleNameSetByUserName(u1.getUserName());
        Assert.assertEquals(1,roleNameList.size());
        Assert.assertTrue(roleNameList.contains(r1.getRoleName()));
    }
}

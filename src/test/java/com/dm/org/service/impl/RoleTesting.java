package com.dm.org.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.dm.org.base.SecurityTestingInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dm.org.model.Permission;
import com.dm.org.model.Role;


/**
 * RoleServiceImpl Tester.
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
public class RoleTesting extends SecurityTestingInitializer
{

    @Before
    public void before()
        throws Exception
    {}

    @After
    public void after()
        throws Exception
    {

    }

    /**
     * Method: fetchRoleListByIdList(List<Long> roleIdList)
     */
    @Test
    public void testFetchRoleListByIdList()
        throws Exception
    {
        testCorrelatePermissions();
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(r1.getRoleId());
        roleIdList.add(r2.getRoleId());
        List<Role> roleList = roleService.fetchRoleListByIdList(roleIdList);
        Assert.assertEquals(roleList.size(), 2);
    }

    /**
     * Method: correlatePermissions(Long roleId, List<Long> permissionList)
     */
    @Test
    public void testCorrelatePermissions()
        throws Exception
    {
        // 关联角色-权限
        List<Long> permissionIdList1 = new ArrayList<Long>();
        permissionIdList1.add(p1.getPermissionId());
        permissionIdList1.add(p2.getPermissionId());
        permissionIdList1.add(p3.getPermissionId());

        List<Long> permissionIdList2 = new ArrayList<Long>();
        permissionIdList2.add(p1.getPermissionId());
        permissionIdList2.add(p2.getPermissionId());

        roleService.correlatePermissions(r1.getRoleId(),permissionIdList1);
        roleService.correlatePermissions(r2.getRoleId(), permissionIdList2);

        List<Permission> permissionList = roleService.fetchPermissionListById(r1.getRoleId());
        Assert.assertEquals(permissionList.size(), 3);
    }

    /**
     * Method: correlatePermission(Long roleId, Long permissionId)
     */
    @Test
    public void testCorrelatePermission()
        throws Exception
    {
        roleService.correlatePermission(r1.getRoleId(),p1.getPermissionId());
        roleService.correlatePermission(r1.getRoleId(),p2.getPermissionId());
        roleService.correlatePermission(r1.getRoleId(),p3.getPermissionId());
        List<Permission> permissionList = roleService.fetchPermissionListById(r1.getRoleId());
        Assert.assertEquals(permissionList.size(), 3);
    }

    /**
     * Method: removePermission(Long roleId, Long permissionId)
     */
    @Test
    public void testRemovePermission()
        throws Exception
    {
        roleService.removePermission(r1.getRoleId(), p1.getPermissionId());
        roleService.removePermission(r1.getRoleId(), p2.getPermissionId());
        roleService.removePermission(r1.getRoleId(), p3.getPermissionId());
        List<Permission> permissionList = roleService.fetchPermissionListById(r1.getRoleId());
        Assert.assertTrue(!permissionList.contains(p1));
        Assert.assertEquals(permissionList.size(), 0);
    }

    /**
     * Method: removePermissions(Long roleId, List<Long> permissionIdList)
     */
    @Test
    public void testRemovePermissions()
        throws Exception
    {
        testCorrelatePermissions();
        List<Long> permissionIdList = new ArrayList<Long>();
        permissionIdList.add(p1.getPermissionId());
        permissionIdList.add(p2.getPermissionId());
        permissionIdList.add(p3.getPermissionId());
        roleService.removePermissions(r1.getRoleId(), permissionIdList);
        List<Permission> permissionList = roleService.fetchPermissionListById(r1.getRoleId());
        Assert.assertTrue(!permissionList.contains(p1));
        Assert.assertEquals(permissionList.size(), 0);
    }

}

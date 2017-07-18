package com.dm.org.model;


import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:34 2017/7/14.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "role_info", catalog = "")
public class Role
{
    private Long roleId;

    private String roleName;

    private String description;

    private boolean isAvailable;

    private Set<Permission> permissionSet;

    public Role()
    {

    }

    public Role(String roleName, String description, boolean isAvailable)
    {
        this.roleName = roleName;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    @Column(name = "roleId")
    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @Column(name = "roleName")
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    @Column(name = "description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Column(name = "available")
    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission_re", joinColumns = @JoinColumn(name = "roleId"), inverseJoinColumns = @JoinColumn(name = "permissionId"))
    public Set<Permission> getPermissionSet()
    {
        return permissionSet;
    }

    public void setPermissionSet(Set<Permission> permissionSet)
    {
        this.permissionSet = permissionSet;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role that = (Role)o;

        return Objects.equal(this.roleId, that.roleId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(roleId);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("roleId", roleId).add("roleName",
            roleName).add("description", description).add("isAvailable", isAvailable).toString();
    }
}

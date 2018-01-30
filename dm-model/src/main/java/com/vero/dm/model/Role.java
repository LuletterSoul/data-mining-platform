package com.vero.dm.model;


import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    @Column(name = "roleId")
    private Long roleId;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "description")
    private String description;

    private boolean isAvailable;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission_re", joinColumns = @JoinColumn(name = "roleId"), inverseJoinColumns = @JoinColumn(name = "permissionId"))
    @JsonIgnore
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

    @Column(name = "available")
    public boolean isAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(boolean available)
    {
        isAvailable = available;
    }

    public Long getRoleId()
    {
        return this.roleId;
    }

    public String getRoleName()
    {
        return this.roleName;
    }

    public String getDescription()
    {
        return this.description;
    }

    public Set<Permission> getPermissionSet()
    {
        return this.permissionSet;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPermissionSet(Set<Permission> permissionSet)
    {
        this.permissionSet = permissionSet;
    }

    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Role)) return false;
        final Role other = (Role)o;
        if (!other.canEqual((Object)this)) return false;
        final Object this$roleId = this.getRoleId();
        final Object other$roleId = other.getRoleId();
        if (this$roleId == null ? other$roleId != null : !this$roleId.equals(other$roleId))
            return false;
        final Object this$roleName = this.getRoleName();
        final Object other$roleName = other.getRoleName();
        if (this$roleName == null ? other$roleName != null : !this$roleName.equals(other$roleName))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(
            other$description)) return false;
        if (this.isAvailable() != other.isAvailable()) return false;
        final Object this$permissionSet = this.getPermissionSet();
        final Object other$permissionSet = other.getPermissionSet();
        if (this$permissionSet == null ? other$permissionSet != null : !this$permissionSet.equals(
            other$permissionSet)) return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        int result = roleId.hashCode();
        result = 31 * result + roleName.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (isAvailable ? 1 : 0);
        return result;
    }

    protected boolean canEqual(Object other)
    {
        return other instanceof Role;
    }

    public String toString()
    {
        return "Role(roleId=" + this.getRoleId() + ", roleName=" + this.getRoleName()
               + ", description=" + this.getDescription() + ", isAvailable=" + this.isAvailable()
               + ", permissionSet=" + this.getPermissionSet() + ")";
    }
}

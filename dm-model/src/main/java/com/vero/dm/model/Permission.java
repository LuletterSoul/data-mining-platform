package com.vero.dm.model;


import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.google.common.base.Objects;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:49 2017/7/14.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "permission_info", catalog = "")
public class Permission
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Long permissionId;

    @Column(name = "permissions", nullable = false)
    private String permissionName;

    @Column(name = "description")
    private String description;

    private Boolean isAvailable;

    public Permission()
    {

    }

    public Permission(String permissionName, String description, Boolean isAvailable)
    {
        this.permissionName = permissionName;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    @Column(name = "available")
    public Boolean getAvailable()
    {
        return isAvailable;
    }

    public void setAvailable(Boolean available)
    {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Permission that = (Permission)o;
        return Objects.equal(permissionId, that.permissionId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(super.hashCode(), permissionId);
    }
}

package com.dm.org.model;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:49 2017/7/14.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "permission_info", catalog = "")
public class Permission
{
    private Long permissionId;

    private String permissionName;

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

    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public Long getPermissionId()
    {
        return permissionId;
    }

    public void setPermissionId(Long permissionId)
    {
        this.permissionId = permissionId;
    }

    @Column(name = "permissionName", nullable = false)
    public String getPermissionName()
    {
        return permissionName;
    }

    public void setPermissionName(String permissionName)
    {
        this.permissionName = permissionName;
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

         Permission that = (Permission) o;

          return Objects.equal(this.permissionId, that.permissionId);
     }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(permissionId);
    }


    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("permissionId", permissionId)
                .add("permissionName", permissionName)
                .add("description", description)
                .add("isAvailable", isAvailable)
                .toString();
    }
}

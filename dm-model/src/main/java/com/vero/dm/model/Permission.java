package com.vero.dm.model;


import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private Long permissionId;

    @Column(name = "permissionName", nullable = false)
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

}

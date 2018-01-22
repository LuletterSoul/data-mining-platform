package com.vero.dm.model;


import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 17:34 2017/7/14.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "role_info", catalog = "")
public class Role
{
    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
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

}

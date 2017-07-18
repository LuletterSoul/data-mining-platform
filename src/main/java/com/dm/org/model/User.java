package com.dm.org.model;


import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import com.dm.org.enums.UserAccessStatus;
import org.hibernate.annotations.GenericGenerator;

import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @date created in 22:55 2017/6/28.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "user_info", catalog = "")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "serviceLevel",discriminatorType = DiscriminatorType.STRING)
public class User implements EntityIdentifier
{
    private String userId;

    private String userName;

    private String password;

    private String salt;

    private UserAccessStatus status = UserAccessStatus.AVAILABLE;

    private Set<Role> roleSet;

    public User()
    {

    }

    public User(String userName, String password, String salt, UserAccessStatus status)
    {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "userId", nullable = false, length = 32)
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }


    @Size(max = 16,min = 2)
    @Column(name = "userName", length = 32)
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String name)
    {
        this.userName = name;
    }

    @Column(name = "password",nullable = false)
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Column(name = "salt")
    public String getSalt()
    {
        return salt;
    }



    public void setSalt(String salt)
     {
        this.salt = salt;
    }

    @Transient
    public String getCredentialsSalt()
    {
        return userName + salt;
    }

    @Enumerated
    @Column(name = "status")
    public UserAccessStatus getStatus()
    {
        return status;
    }

    public void setStatus(UserAccessStatus status)
     {
        this.status = status;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_re",joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        return Objects.equal(this.userId, that.userId) ;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(userId);
    }


    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("password", password)
                .add("salt", salt)
                .add("status", status)
                .add("roleSet", roleSet)
                .toString();
    }
}

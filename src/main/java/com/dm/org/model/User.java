package com.dm.org.model;


import com.dm.org.enums.UserAccessStatus;
import com.dm.org.identifier.EntityIdentifier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;


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
    protected String userId;

    protected String userName;

    protected byte[] avatar;

    protected String gender;

    protected String password;

    protected String publicSalt;

    protected String privateSalt;

    protected UserAccessStatus accountStatus = UserAccessStatus.AVAILABLE;

    protected Set<Role> roleSet;


    public User() {
    }

    public User(String userName, String password, String publicSalt, UserAccessStatus status)
    {
        this.userName = userName;
        this.password = password;
        this.publicSalt = publicSalt;
        this.accountStatus = status;
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


    @Column(name = "userName")
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

    @Column(name = "publicSalt")
    public String getPublicSalt()
    {
        return publicSalt;
    }


    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public void setPublicSalt(String salt)
     {
        this.publicSalt = salt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrivateSalt() {
        return privateSalt;
    }

    public void setPrivateSalt(String privateSalt) {
        this.privateSalt = privateSalt;
    }

    @Enumerated
    @Column(name = "status")
    public UserAccessStatus getAccountStatus()
    {
        return accountStatus;
    }

    public void setAccountStatus(UserAccessStatus status)
     {
        this.accountStatus = status;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_re",joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "roleId"))
    @JsonIgnore
    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        return Objects.equal(this.userId, that.userId);
    }





    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("password", password)
                .add("saltEntry", publicSalt)
                .add("status", accountStatus)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, userName, password, publicSalt, privateSalt, accountStatus);
    }
}

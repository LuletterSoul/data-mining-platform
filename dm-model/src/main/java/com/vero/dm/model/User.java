package com.vero.dm.model;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @date created in 22:55 2017/6/28.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "user_info", catalog = "")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "serviceLevel", discriminatorType = DiscriminatorType.STRING)
public class User implements EntityIdentifier
{
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "userId", nullable = false, length = 32)
    protected String userId;

    @Column(name = "username")
    protected String username;

    protected String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    protected byte[] avatar;

    protected String gender;

    @Column(name = "password", nullable = false)
    protected String password;

    protected Date birthday;

    @Column(name = "publicSalt")
    protected String publicSalt;

    protected String privateSalt;

    @Column(name = "status")
    protected UserAccessStatus accountStatus = UserAccessStatus.AVAILABLE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_re", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    @JsonIgnore
    protected Set<Role> roles;

    protected String regionCode;

    protected String introduction;

    protected Timestamp lastLoginTime;

    public User()
    {}

    public User(String username, String password, String publicSalt, UserAccessStatus status)
    {
        this.username = username;
        this.password = password;
        this.publicSalt = publicSalt;
        this.accountStatus = status;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

}

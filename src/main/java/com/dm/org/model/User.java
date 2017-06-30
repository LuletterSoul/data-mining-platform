package com.dm.org.model;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;


/**
 * @author ÁõÏéµÂ qq313700046@icloud.com .
 * @date created in 22:55 2017/6/28.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "user_info", catalog = "")
public class User
{
    private String userId;

    private String name;

    private String gender;

    private Integer age;

    public User()
    {
    }

    public User(String userId, String name, String gender, Integer age) {
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    @Id
    @Column(name = "userId", nullable = false, length = 32)
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 32)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Basic
    @Column(name = "gender", nullable = true, length = 20)
    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User)o;

        return Objects.equal(this.userId, that.userId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("userId", userId).add("name", name).add(
            "gender", gender).add("age", age).toString();
    }
}

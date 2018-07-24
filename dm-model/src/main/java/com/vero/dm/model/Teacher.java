package com.vero.dm.model;


import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:37 2017/7/14.
 * @description
 * @modified by:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "Teacher")
public class Teacher extends User
{
    private String teacherName;

    @Column(unique = true)
    private String teacherId;

    /**
     * 一个老师可建多个分组
     */
    @JsonIgnore
    @OneToMany(mappedBy = "teacherBuilder")
    private Set<DataMiningGroup> buildGroups;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_stu_rel", joinColumns = @JoinColumn(name = "t_userId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "s_userId", referencedColumnName = "userId"))
    private Set<Student> favoriteStudent;

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherName='" + teacherName + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", buildGroups=" + buildGroups +
                ", favoriteStudent=" + favoriteStudent +
                '}';
    }
}

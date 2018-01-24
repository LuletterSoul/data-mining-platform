package com.vero.dm.model;


import java.util.Set;

import javax.persistence.*;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:37 2017/7/14.
 * @description
 * @modified by:
 */
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
    @OneToMany(mappedBy = "groupBuilder")
    private Set<DataMiningGroup> buildGroups;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_stu_rel", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "studentId"))
    private Set<Student> favoriteStudent;

}

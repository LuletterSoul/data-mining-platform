package com.vero.dm.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:36 2017/7/14.
 * @description
 * @modified by:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue(value = "Student")
@ToString(exclude = {"ruleMiningGroups", "miningGroups"})
public class Student extends User implements Serializable
{
    private static final long serialVersionUID = 8479971255524788081L;

    private String studentName;

    /**
     * 学号唯一
     */
    @Column(unique = true)
    private String studentId;

    private String grade;

    private String className;

    private String profession;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusId", foreignKey = @ForeignKey(name = "STATUS_FOREIGN_KEY"))
    private StudentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "favoriteId", foreignKey = @ForeignKey(name = "FAVORITE_FOREIGN_KEY"))
    private FavoriteStatus favorite;

    /**
     * 一个学生可以管理多个分组
     */
    @JsonIgnore
    @OneToMany(mappedBy = "groupLeader")
    private Set<DataMiningGroup> ruleMiningGroups;

    /**
     * 一个学生在不同时段可以位于不同的分组 不同分组也有不同学生 需要校验合法性
     */
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "group_student_rel", joinColumns = @JoinColumn(name = "memberId", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId"))
    private Set<DataMiningGroup> miningGroups;

    public Student()
    {}

    public Student(String userName, String password, String publicSalt, UserAccessStatus status,
                   String studentName)
    {
        super(userName, password, publicSalt, status);
        this.studentName = studentName;
    }

}

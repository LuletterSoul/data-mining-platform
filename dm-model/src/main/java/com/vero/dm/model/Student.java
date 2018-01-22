package com.vero.dm.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 18:36 2017/7/14.
 * @description
 * @modified by:
 */
@Data
@Entity
@DiscriminatorValue(value = "Student")
public class Student extends User implements Serializable
{
    private static final long serialVersionUID = 8479971255524788081L;

    private String studentName;

    @Column(unique = true)
    private String studentId;

    private String grade;

    private String className;

    private String profession;

    // private Set<GroupInfo> groupInfos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statusId", foreignKey = @ForeignKey(name = "STATUS_FOREIGN_KEY"))
    private StudentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "favoriteId", foreignKey = @ForeignKey(name = "FAVORITE_FOREIGN_KEY"))
    private FavoriteStatus favorite;

    private int finishedTaskCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "group_student_rel", joinColumns = @JoinColumn(name = "studentUid", referencedColumnName = "studentId"), inverseJoinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId"))
    private Set<DataMiningGroup> miningGroups;

    public Student()
    {}

    public Student(String userName, String password, String publicSalt, UserAccessStatus status,
                   String studentName)
    {
        super(userName, password, publicSalt, status);
        this.studentName = studentName;
    }

    // @OneToMany(cascade = CascadeType.ALL,mappedBy = "student",fetch = FetchType.EAGER)
    // public Set<GroupInfo> getGroupInfos() {
    // return groupInfos;
    // }
    //
    // public void setGroupInfos(Set<GroupInfo> groupInfos) {
    // this.groupInfos = groupInfos;
    // }

}

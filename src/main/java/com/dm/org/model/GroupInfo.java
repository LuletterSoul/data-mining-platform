package com.dm.org.model;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Entity
@Table(name = "group_info")
public class GroupInfo implements Serializable
{
    private static final long serialVersionUID = 5090742807776694601L;

    private Student student;

    private DataMiningGroup group;


    @Id
    @ManyToOne
    @JoinColumn(name = "groupId", foreignKey = @ForeignKey(name = "GROUP_FK_ID"))
    public DataMiningGroup getGroup()
    {
        return group;
    }

    public void setGroup(DataMiningGroup group)
    {
        this.group = group;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "studentId", foreignKey = @ForeignKey(name = "STUDENT_FK_ID"))
    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("serialVersionUID", serialVersionUID)
                .add("student", student)
                .add("group", group)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupInfo that = (GroupInfo) o;

        return Objects.equal(this.student, that.student) &&
                Objects.equal(this.group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialVersionUID, student, group);
    }
}

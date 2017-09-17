package com.dm.org.model;


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

    private Date setUpDate;

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

    public Date getSetUpDate()
    {
        return setUpDate;
    }

    public void setSetUpDate(Date setUpDate)
    {
        this.setUpDate = setUpDate;
    }
}

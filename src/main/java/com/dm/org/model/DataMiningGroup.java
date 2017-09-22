package com.dm.org.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * @since data-mining-platform
 */

@Entity
@Table(name = "mining_group")
public class DataMiningGroup
{
    private String groupId;

    private String groupName;

    private Timestamp setUpDate;

    // private Set<GroupInfo> groupInfos;

    private Set<Student> groupMembers;

    private Student groupLeader;

    private DataMiningTask dataMiningTask;

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupLeaderId", foreignKey = @ForeignKey(name = "LEADER_FK_ID"))
    public Student getGroupLeader()
    {
        return groupLeader;
    }

    public void setGroupLeader(Student groupLeader)
    {
        this.groupLeader = groupLeader;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public Timestamp getSetUpDate()
    {
        return setUpDate;
    }

    public void setSetUpDate(Timestamp setUpDate)
    {
        this.setUpDate = setUpDate;
    }

    // @OneToMany(cascade = CascadeType.ALL,mappedBy = "group", fetch = FetchType.EAGER)
    // public Set<GroupInfo> getGroupInfos() {
    // return groupInfos;
    // }
    //
    // public void setGroupInfos(Set<GroupInfo> groupInfos) {
    // this.groupInfos = groupInfos;
    // }
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "group_student_rel", joinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId")
            , inverseJoinColumns = @JoinColumn(name = "studentUId", referencedColumnName = "userId"))
    public Set<Student> getGroupMembers()
    {
        return groupMembers;
    }

    public void setGroupMembers(Set<Student> groupMembers)
    {
        this.groupMembers = groupMembers;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId", foreignKey = @ForeignKey(name = "TASK_FOREIGN_KEY"))
    public DataMiningTask getDataMiningTask()
    {
        return dataMiningTask;
    }

    public void setDataMiningTask(DataMiningTask dataMiningTask)
    {
        this.dataMiningTask = dataMiningTask;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataMiningGroup that = (DataMiningGroup) o;

        return Objects.equal(this.groupId, that.groupId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(groupId, groupName, setUpDate, groupLeader, dataMiningTask);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("groupId", groupId)
                .add("groupName", groupName)
                .add("setUpDate", setUpDate)
                .add("groupLeader", groupLeader)
                .add("dataMiningTask", dataMiningTask)
                .toString();
    }
}

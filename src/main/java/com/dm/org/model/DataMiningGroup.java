package com.dm.org.model;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
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

    private Date setUpDate;

    private Set<GroupInfo> groupInfos;

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

    @OneToOne
    @JoinColumn(name="groupLeaderId",foreignKey = @ForeignKey(name="LEADER_FK_ID"))
    public Student getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(Student groupLeader) {
        this.groupLeader = groupLeader;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getSetUpDate() {
        return setUpDate;
    }

    public void setSetUpDate(Date setUpDate) {
        this.setUpDate = setUpDate;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "group", fetch = FetchType.EAGER)
    public Set<GroupInfo> getGroupInfos() {
        return groupInfos;
    }

    public void setGroupInfos(Set<GroupInfo> groupInfos) {
        this.groupInfos = groupInfos;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId",foreignKey = @ForeignKey(name = "TASK_FOREIGN_KEY"))
    public DataMiningTask getDataMiningTask() {
        return dataMiningTask;
    }

    public void setDataMiningTask(DataMiningTask dataMiningTask) {
        this.dataMiningTask = dataMiningTask;
    }
}

package com.vero.dm.model;


import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * @since data-mining-platform
 */

@Data
@Entity
@Table(name = "mining_group")
public class DataMiningGroup
{
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String groupId;

    private String groupName;

    private Timestamp setUpDate;

    // private Set<GroupInfo> groupInfos;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "group_student_rel", joinColumns = @JoinColumn(name = "groupId", referencedColumnName = "groupId"), inverseJoinColumns = @JoinColumn(name = "studentUId", referencedColumnName = "userId"))
    private Set<Student> groupMembers;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupLeaderId", foreignKey = @ForeignKey(name = "LEADER_FK_ID"))
    private Student groupLeader;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taskId", foreignKey = @ForeignKey(name = "TASK_FOREIGN_KEY"))
    private DataMiningTask dataMiningTask;

}

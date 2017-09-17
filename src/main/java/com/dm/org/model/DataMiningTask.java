package com.dm.org.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 * @since data-mining-platform
 */

@Entity
@Table(name = "data_mining_task")
public class DataMiningTask
{
    private String taskId;

    private String type;

    private String taskDescription;

    private int duration;

    private Date startTime;

    private Date finishTime;

    private Set<DataMiningGroup> groups;

    private Set<Algorithm> algorithms;

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getFinishTime()
    {
        return finishTime;
    }

    public void setFinishTime(Date finishTime)
    {
        this.finishTime = finishTime;
    }

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, mappedBy = "dataMiningTask")
    public Set<DataMiningGroup> getGroups()
    {
        return groups;
    }

    public void setGroups(Set<DataMiningGroup> groups)
    {
        this.groups = groups;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_algorithm_rela", joinColumns = @JoinColumn(name = "taskId"), inverseJoinColumns = @JoinColumn(name = "algorithmId"))
    public Set<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Set<Algorithm> algorithms)
    {
        this.algorithms = algorithms;
    }
}

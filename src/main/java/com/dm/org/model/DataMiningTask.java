package com.dm.org.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
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

    private String taskName;

    private String type;

    private String taskDescription;

    private int duration;

    private Timestamp startTime;

    private Timestamp finishTime;

    private Set<DataMiningGroup> groups;

    private Set<DataSetContainer> dataSetContainers;

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public Timestamp getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Timestamp startTime)
    {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime()
    {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime)
    {
        this.finishTime = finishTime;
    }

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "dataMiningTask")
    public Set<DataMiningGroup> getGroups()
    {
        return groups;
    }

    public void setGroups(Set<DataMiningGroup> groups)
    {
        this.groups = groups;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_algorithm_rela", joinColumns = @JoinColumn(name = "taskId",referencedColumnName = "taskId")
            , inverseJoinColumns = @JoinColumn(name = "algorithmId",referencedColumnName = "algorithmId"))
    public Set<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(Set<Algorithm> algorithms)
    {
        this.algorithms = algorithms;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("taskId", taskId)
                .add("type", type)
                .add("taskDescription", taskDescription)
                .add("duration", duration)
                .add("startTime", startTime)
                .add("finishTime", finishTime)
                .toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataMiningTask that = (DataMiningTask) o;

        return Objects.equal(this.taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId, type, taskDescription, duration, startTime, finishTime);
    }

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "task_data_set_ref",joinColumns = @JoinColumn(name = "taskId",referencedColumnName = "taskId"),
    inverseJoinColumns = @JoinColumn(name = "containerId",referencedColumnName = "containerId"))
    public Set<DataSetContainer> getDataSetContainers() {
        return dataSetContainers;
    }

    public void setDataSetContainers(Set<DataSetContainer> dataSetContainers) {
        this.dataSetContainers = dataSetContainers;
    }
}

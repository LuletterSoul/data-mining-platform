package com.vero.dm.model;


import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 * @since data-mining-platform
 */

@Data
@Entity
@Table(name = "data_mining_task")
public class DataMiningTask
{
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    private String taskId;

    private String taskName;

    private String type;

    private String taskDescription;

    private int duration;

    private String startTime;

    private String finishTime;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST,
        CascadeType.MERGE}, mappedBy = "dataMiningTask")
    private Set<DataMiningGroup> groups;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "task_data_set_ref", joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "taskId"), inverseJoinColumns = @JoinColumn(name = "collectionId", referencedColumnName = "collectionId"))
    private Set<DataSetCollection> collections;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_algorithm_rela", joinColumns = @JoinColumn(name = "taskId", referencedColumnName = "taskId"), inverseJoinColumns = @JoinColumn(name = "algorithmId", referencedColumnName = "algorithmId"))
    private Set<Algorithm> algorithms;

}

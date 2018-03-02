package com.vero.dm.model;


import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
@Entity
@Table(name = "algorithm_info",schema = "")
public class Algorithm
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private Integer algorithmId;

    private String algorithmName;

    private String interfaceDescription;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "algorithm_type_rel", joinColumns = @JoinColumn(name = "algorithmId", referencedColumnName = "algorithmId"), inverseJoinColumns = @JoinColumn(name = "typeId", referencedColumnName = "typeId"))
    private AlgorithmType type;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "algorithm", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<AlgorithmParameter> algorithmParameters;

    public Algorithm()
    {}

    public Algorithm(String algorithmName, String interfaceDescription, AlgorithmType type)
    {
        this.algorithmName = algorithmName;
        this.interfaceDescription = interfaceDescription;
        this.type = type;
    }



}

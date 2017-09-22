package com.dm.org.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Entity
@Table(name = "algorithm_info")
public class Algorithm
{
    private String algorithmId;

    private String algorithmName;

    private String interfaceDescription;

    private AlgorithmType type;

    private Set<AlgorithmParameter> algorithmParameters;

    public Algorithm() {
    }

    public Algorithm(String algorithmName, String interfaceDescription, AlgorithmType type) {
        this.algorithmName = algorithmName;
        this.interfaceDescription = interfaceDescription;
        this.type = type;
    }

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    public String getAlgorithmId()
    {
        return algorithmId;
    }

    public void setAlgorithmId(String algorithmId)
    {
        this.algorithmId = algorithmId;
    }

    public String getInterfaceDescription()
    {
        return interfaceDescription;
    }

    public void setInterfaceDescription(String interfaceDescription)
    {
        this.interfaceDescription = interfaceDescription;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "algorithm",fetch = FetchType.LAZY,orphanRemoval = true)
    public Set<AlgorithmParameter> getAlgorithmParameters() {

        return algorithmParameters;
    }

    public void setAlgorithmParameters(Set<AlgorithmParameter> algorithmParameters)
    {
        this.algorithmParameters = algorithmParameters;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Algorithm that = (Algorithm) o;

        return Objects.equal(this.algorithmId, that.algorithmId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(algorithmId);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("algorithmId", algorithmId)
                .add("interfaceDescription", interfaceDescription)
                .add("type", type)
                .add("algorithmName",algorithmName)
                .toString();
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @ManyToOne(fetch = FetchType.EAGER,cascade =CascadeType.PERSIST)
    @JoinTable(name = "algorithm_type_rel",joinColumns = @JoinColumn(name ="algorithmId",referencedColumnName = "algorithmId")
            ,inverseJoinColumns = @JoinColumn(name = "typeId",referencedColumnName = "typeId"))
    public AlgorithmType getType() {
        return type;
    }

    public void setType(AlgorithmType type) {
        this.type = type;
    }
}

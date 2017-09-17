package com.dm.org.model;


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

    private String interfaceDescription;

    private String type;

    private Set<AlgorithmParameter> algorithmParameters;

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

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "algorithm",fetch = FetchType.LAZY,orphanRemoval = true)
    public Set<AlgorithmParameter> getAlgorithmParameters() {

        return algorithmParameters;
    }

    public void setAlgorithmParameters(Set<AlgorithmParameter> algorithmParameters)
    {
        this.algorithmParameters = algorithmParameters;
    }
}

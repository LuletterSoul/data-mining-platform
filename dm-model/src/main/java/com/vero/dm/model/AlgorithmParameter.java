package com.vero.dm.model;


import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
@Entity
@Table(name = "algorithm_param")
public class AlgorithmParameter
{
    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private Long paramId;

    private String paramDesc;

    private String dataType;

    private boolean isNecessary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithmId", foreignKey = @ForeignKey(name = "ALGORITHM_FK_ID"))
    private Algorithm algorithm;

    public boolean isNecessary()
    {
        return isNecessary;
    }

    public void setNecessary(boolean necessary)
    {
        isNecessary = necessary;
    }

}

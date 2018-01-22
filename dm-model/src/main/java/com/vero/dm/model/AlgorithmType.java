package com.vero.dm.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
@Entity
@Table(name = "algorithm_type", catalog = "")
public class AlgorithmType
{
    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private int typeId;

    private String chTypeName;

    private String enTypeName;

    public AlgorithmType()
    {

    }

    public AlgorithmType(String chTypeName, String enTypeName)
    {
        this.typeId = typeId;
        this.chTypeName = chTypeName;
        this.enTypeName = enTypeName;
    }

}

package com.dm.org.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Entity
@Table(name = "algorithm_type",catalog = "")
public class AlgorithmType {
    private int typeId;
    private String chTypeName;
    private String enTypeName;

    public AlgorithmType() {

    }

    public AlgorithmType(String chTypeName, String enTypeName) {
        this.typeId = typeId;
        this.chTypeName = chTypeName;
        this.enTypeName = enTypeName;
    }

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getChTypeName() {
        return chTypeName;
    }

    public void setChTypeName(String chTypeName) {
        this.chTypeName = chTypeName;
    }

    public String getEnTypeName() {
        return enTypeName;
    }

    public void setEnTypeName(String enTypeName) {
        this.enTypeName = enTypeName;
    }
}

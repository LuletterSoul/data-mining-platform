package com.dm.org.model;

import javax.persistence.Entity;

import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  15:49 2017/7/5.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "collec_char", catalog = "")
public class CollectionCharMap implements EntityIdentifier,Serializable
{
    private Integer id;
    private DataSetCharacteristic characteristic;

    @Id
    @Column(name = "charId")
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="char",nullable = false)
    public DataSetCharacteristic getCharacteristic()
    {
        return characteristic;
    }

    public void setCharacteristic(DataSetCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}

package com.dm.org.model;

import javax.persistence.Entity;

import com.dm.org.enums.DataSetCharacteristic;
import com.dm.org.identifier.EntityIdentifier;

import javax.persistence.*;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  15:49 2017/7/5.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "collec_char_info", catalog = "")
public class CollectionCharMap implements EntityIdentifier
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
    @Column(name="characteristic",nullable = false)
    public DataSetCharacteristic getCharacteristic()
    {
        return characteristic;
    }

    public void setCharacteristic(DataSetCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}

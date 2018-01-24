package com.vero.dm.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 14:55 2017/7/4.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "attr_feature_info")
public class AttributeCharacteristic
{
    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private int charId;

    private String chineseName;

    private String englishName;

    /**
     * 缩写
     */
    private String abbreviation;
}

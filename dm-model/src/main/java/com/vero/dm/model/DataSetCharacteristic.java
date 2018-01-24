package com.vero.dm.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 14:51 2017/7/4.
 * @description 数据集特征
 * @modified by:2018.1.24
 */
@Data
@Entity
@Table(name = "data_set_char")
public class DataSetCharacteristic
{
    @Id
    private Integer charId;

    /**
     * 数据集特征的中文描述
     */
    private String chineseName;

    /**
     * 数据集特征的英文描述
     */
    private String englishName;

    /**
     * 缩写
     */
    private String abbreviation;

}

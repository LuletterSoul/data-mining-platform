package com.vero.dm.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */
@Data
@Entity
@Table(name = "favorite_status", catalog = "")
public class FavoriteStatus
{
    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    private int favoriteId;

    private String chineseValue;

    private String englishValue;

    public FavoriteStatus()
    {}

    public FavoriteStatus(String chineseValue, String englishValue)
    {
        this.chineseValue = chineseValue;
        this.englishValue = englishValue;
    }

}

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
@Table(name = "favorite_status",catalog = "")
public class FavoriteStatus {
    private int favoriteId;
    private String chineseValue;
    private String englishValue;

    public FavoriteStatus() {
    }

    public FavoriteStatus(String chineseValue, String englishValue) {
        this.chineseValue = chineseValue;
        this.englishValue = englishValue;
    }

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getChineseValue() {
        return chineseValue;
    }

    public void setChineseValue(String key) {
        this.chineseValue = key;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    public void setEnglishValue(String value) {
        this.englishValue = value;
    }
}

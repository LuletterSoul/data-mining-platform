package com.dm.org.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  14:51 2017/7/4.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "set_char_info")
public class DataSetCharacteristic {
    private String charId;
    private String chineseName;
    private String englishName;

    @Id
    public String getCharId() {
        return charId;
    }

    public void setCharId(String charId) {
        this.charId = charId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}

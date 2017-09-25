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
@Table(name = "attr_type", catalog = "")
public class AttributeType {
    private Integer typeId;
    private String chineseName;
    private String englishName;

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseValue) {
        this.chineseName = chineseValue;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishValue) {
        this.englishName = englishValue;
    }
}

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
    private int typeId;
    private String chineseValue;
    private String englishValue;

    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getChineseValue() {
        return chineseValue;
    }

    public void setChineseValue(String chineseValue) {
        this.chineseValue = chineseValue;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    public void setEnglishValue(String englishValue) {
        this.englishValue = englishValue;
    }
}

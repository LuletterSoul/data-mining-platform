package com.dm.org.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  14:55 2017/7/4.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "attr_feature_info")
public class AttributeFeature
{
    private int featureId;
    private String chineseName;
    private String englishName;

    @Id
    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
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

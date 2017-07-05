package com.dm.org.model;


import javax.persistence.Entity;

import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in  13:21 2017/7/5.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "attr_feature", catalog = "")
public class AttributeFeatureMap implements EntityIdentifier,Serializable
{
    private Integer id;
    private AttributeFeature feature;

    @Id
    @Column(name="attrFeatureId")
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "feature")
    public AttributeFeature getFeature()
    {
        return feature;
    }

    public void setFeature(AttributeFeature feature)
    {
        this.feature = feature;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeFeatureMap that = (AttributeFeatureMap) o;

        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.feature, that.feature);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id, feature);
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("feature", feature)
                .toString();
    }
}

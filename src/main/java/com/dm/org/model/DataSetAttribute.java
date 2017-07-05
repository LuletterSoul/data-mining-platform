package com.dm.org.model;


import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "data_set_attribute", catalog = "")
public class DataSetAttribute implements EntityIdentifier,Serializable
{
    private UUID attributeId;

    private String fieldName;

    private String description;

    private Set<AttributeFeatureMap> features;

    private DataSetContainer dataSetContainer;


    @Id
    @Column(name = "attributeId", nullable = false)
    public UUID getAttributeId()
    {
        return attributeId;
    }

    public void setAttributeId(UUID attributeId)
    {
        this.attributeId = attributeId;
    }

    @Basic
    @Column(name = "fieldName", nullable = false, length = 30)
    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(name = "attr_features",
            joinColumns = @JoinColumn(name = "containerId"),
            inverseJoinColumns = @JoinColumn(name = "feature"))
    public Set<AttributeFeatureMap> getFeatures()
    {
        return features;
    }

    public void setFeatures(Set<AttributeFeatureMap> features) {
        this.features = features;
    }

    @ManyToOne
    @JoinColumn(name = "containerId", foreignKey = @ForeignKey(name = "CONTAINER_ID_FK"))
    public DataSetContainer getDataSetContainer() {
        return dataSetContainer;
    }

    public void setDataSetContainer(DataSetContainer dataSetContainer) {
        this.dataSetContainer = dataSetContainer;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetAttribute that = (DataSetAttribute) o;

        return Objects.equal(this.attributeId, that.attributeId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(attributeId);
    }


    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("attributeId", attributeId)
                .add("fieldName", fieldName)
                .add("description", description)
                .add("features", features)
                .add("dataSetContainer", dataSetContainer)
                .toString();
    }
}

package com.dm.org.model;


import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "data_set_attribute", catalog = "")
public class DataSetAttribute implements EntityIdentifier
{
    private Long attributeId;

    private String fieldName;

    private byte[] description;

    private Set<AttributeFeatureMap> features;

    private DataSetContainer dataSetContainer;

    private List<DataSetCell> cellList;


    @Id
    @GenericGenerator(name = "identityGenerator",strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    public Long getAttributeId()
    {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
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


    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "description" ,nullable = false)
    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(name = "attr_feature_rela",
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
    public DataSetContainer getDataSetContainer()
    {
        return dataSetContainer;
    }

    public void setDataSetContainer(DataSetContainer dataSetContainer) {
        this.dataSetContainer = dataSetContainer;
    }


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dataSetAttribute"
            ,fetch = FetchType.LAZY,orphanRemoval = true)
    public List<DataSetCell> getCellList()
    {
        return cellList;
    }

    public void setCellList(List<DataSetCell> cells)
    {
        this.cellList = cells;
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attributeId", attributeId)
                .add("fieldName", fieldName)
                .add("description", description)
                .add("features", features)
                .add("dataSetContainer", dataSetContainer)
                .add("cells", cellList)
                .toString();
    }
}

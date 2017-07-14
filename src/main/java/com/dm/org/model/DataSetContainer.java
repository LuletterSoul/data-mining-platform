package com.dm.org.model;


import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "data_set_container", catalog = "")
public class DataSetContainer implements EntityIdentifier
{
    private String containerId;

    private String setName;

    private String attributeTypes;

    private Double size;

    private Long instances;

    private String fileType;

    private byte[] data;

    private DataSetCollection dataSetCollection;

    private Set<DataSetAttribute> attributeSet;

    private List<DataSetRow> rowList;



    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "containerId", nullable = false,length = 32)
    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }


    @Basic
    @Column(name = "setName", nullable = false, length = 100)
    public String getSetName()
    {
        return setName;
    }

    public void setSetName(String setName)
    {
        this.setName = setName;
    }

    @Basic
    @Column(name = "attributeTypes", nullable = false, length = 50)
    public String getAttributeTypes()
    {
        return attributeTypes;
    }

    public void setAttributeTypes(String attributeTypes)
    {
        this.attributeTypes = attributeTypes;
    }

    @Basic
    @Column(name = "size", nullable = false, precision = 0)
    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        this.size = size;
    }

    @Basic
    @Column(name = "instances", nullable = false)
    public Long getInstances()
    {
        return instances;
    }

    public void setInstances(Long instances)
    {
        this.instances = instances;
    }

    @Basic
    @Column(name = "fileType", nullable = false, length = 20)
    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    @Lob
    @Column(name = "data", nullable = false)
    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectionId",
            foreignKey = @ForeignKey(name = "COLLECTION_ID_FK"))
    public DataSetCollection getDataSetCollection()
    {
        return dataSetCollection;
    }

    public void setDataSetCollection(DataSetCollection dataSetCollection)
    {
        this.dataSetCollection = dataSetCollection;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dataSetContainer"
            ,orphanRemoval = true,fetch = FetchType.LAZY)
    public Set<DataSetAttribute> getAttributeSet() {
        return attributeSet;
    }

    public void setAttributeSet(Set<DataSetAttribute> attributeSet)
    {
        this.attributeSet = attributeSet;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dataSetContainer"
            ,orphanRemoval = true,fetch = FetchType.LAZY)
    public List<DataSetRow> getRowList()
    {
        return rowList;
    }

    public void setRowList(List<DataSetRow> rows)
    {
        this.rowList = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetContainer that = (DataSetContainer) o;

        return Objects.equal(this.containerId, that.containerId);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(containerId);
    }


    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("containerId", containerId)
                .add("setName", setName)
                .add("attributeTypes", attributeTypes)
                .add("size", size)
                .add("instances", instances)
                .add("fileType", fileType)
                .add("data", data)
                .add("dataSetCollection", dataSetCollection)
                .toString();
    }
}

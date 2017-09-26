package com.dm.org.model;


import com.dm.org.identifier.EntityIdentifier;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String fileName;

    private String containerName;

    private String fileDescription;

//    private String attributeTypes;

    private Double size;

    private Long instances;

    private String fileType;

    private byte[] data;

    private DataSetCollection dataSetCollection;

//    private Map<String, String> filePathMapping;

    private String filePath;

    private Set<DataSetAttribute> attributeSet;

    private List<DataSetRow> rowList;

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "containerId")
    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }


    @Basic
    @Column(name = "fileName")
    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String setName)
    {
        this.fileName = setName;
    }

//    @Basic
//    @Column(name = "attributeTypes")
//    public String getAttributeTypes()
//    {
//        return attributeTypes;
//    }
//
//    public void setAttributeTypes(String attributeTypes)
//    {
//        this.attributeTypes = attributeTypes;
//    }

    @Basic
    @Column(name = "size")
    public Double getSize()
    {
        return size;
    }

    public void setSize(Double size)
    {
        this.size = size;
    }

    @Basic
    @Column(name = "instances")
    public Long getInstances()
    {
        return instances;
    }

    public void setInstances(Long instances)
    {
        this.instances = instances;
    }


    @Column(name = "fileType")
    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }

    @Lob
    @Column(name = "data")
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
    @JsonIgnore
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
    @JsonIgnore
    public Set<DataSetAttribute> getAttributeSet() {
        return attributeSet;
    }

    public void setAttributeSet(Set<DataSetAttribute> attributeSet)
    {
        this.attributeSet = attributeSet;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dataSetContainer"
            ,orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonIgnore
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
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("containerId", containerId)
                .add("setName", fileName)
                .add("size", size)
                .add("instances", instances)
                .add("fileType", fileType)
                .add("data", data)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(containerId, fileName, size, instances, fileType,
                data);
    }


//    @ElementCollection
//    @JoinTable(name = "file_path_rel")
//    @MapKeyColumn(name = "fileName")
//    @JoinColumn(name = "containerId", referencedColumnName = "containerId")
//    @Column(name = "filePath")
//    public Map<String, String> getFilePathMapping() {
//        return filePathMapping;
//    }
//
//    public void setFilePathMapping(Map<String, String> filePathMapping) {
//        this.filePathMapping = filePathMapping;
//    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String setName) {
        this.containerName = setName;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }
}

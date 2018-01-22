package com.vero.dm.model;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "data_set_container", catalog = "")
public class DataSetContainer implements EntityIdentifier
{
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "containerId")
    private String containerId;

    @Basic
    @Column(name = "fileName")
    private String fileName;

    private String containerName;

    private String fileDescription;

    // private String attributeTypes;

    @Basic
    @Column(name = "size")
    private Double size;

    @Basic
    @Column(name = "instances")
    private Long instances;

    @Column(name = "fileType")
    private String fileType;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectionId", foreignKey = @ForeignKey(name = "COLLECTION_ID_FK"))
    @JsonIgnore
    private DataSetCollection dataSetCollection;

    // private Map<String, String> filePathMapping;

    private String filePath;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataSetContainer", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<DataSetAttribute> attributeSet;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataSetContainer", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DataSetRow> rowList;

    // @Basic
    // @Column(name = "attributeTypes")
    // public String getAttributeTypes()
    // {
    // return attributeTypes;
    // }
    //
    // public void setAttributeTypes(String attributeTypes)
    // {
    // this.attributeTypes = attributeTypes;
    // }

    // @ElementCollection
    // @JoinTable(name = "file_path_rel")
    // @MapKeyColumn(name = "fileName")
    // @JoinColumn(name = "containerId", referencedColumnName = "containerId")
    // @Column(name = "filePath")
    // public Map<String, String> getFilePathMapping() {
    // return filePathMapping;
    // }
    //
    // public void setFilePathMapping(Map<String, String> filePathMapping) {
    // this.filePathMapping = filePathMapping;
    // }

}

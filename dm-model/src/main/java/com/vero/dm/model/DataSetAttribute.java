package com.vero.dm.model;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Data
@Entity
@Table(name = "data_set_attribute", catalog = "")
public class DataSetAttribute implements EntityIdentifier
{
    @Id
    @GenericGenerator(name = "identityGenerator", strategy = "identity")
    @GeneratedValue(generator = "identityGenerator")
    private Long attributeId;

    @Basic
    @Column(name = "fieldName", nullable = false, length = 30)
    private String fieldName;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "description", nullable = false)
    private byte[] description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "attr_feature_rela", joinColumns = @JoinColumn(name = "containerId"), inverseJoinColumns = @JoinColumn(name = "featureId"))
    private Set<AttributeFeature> features;

    @ManyToOne
    @JoinColumn(name = "containerId", foreignKey = @ForeignKey(name = "CONTAINER_ID_FK"))
    private DataSetContainer dataSetContainer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataSetAttribute", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<DataSetCell> cellList;

}

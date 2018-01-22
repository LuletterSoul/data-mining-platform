package com.vero.dm.model;


import java.sql.Date;
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
@Table(name = "data_set_collection", catalog = "")
public class DataSetCollection implements EntityIdentifier

{
    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "collectionId", nullable = false, length = 32)
    private String collectionId;

    private String collectionName;

    @Basic
    @Column(name = "enableMissing")
    private String enableMissing;

    @Column(name = "description")
    private String description;

    @Column(name = "dataDonated")
    private Date dateDonated;

    @Column(name = "relevantPapers")
    private String relevantPapers;

    @Basic
    @Column(name = "abstractInfo")
    private String abstractInfo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "set_task_rel", joinColumns = @JoinColumn(name = "collectionId"), inverseJoinColumns = @JoinColumn(name = "typeId"))
    private Set<MiningTaskType> associatedTasks;

    @Basic
    @Column(name = "topics")
    private String topics;

    private int hits;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "areaId", foreignKey = @ForeignKey(name = "AREA_TYPE_FK"))
    private AreaType area;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attr_type_rel", joinColumns = @JoinColumn(name = "collectionId"), inverseJoinColumns = @JoinColumn(name = "typeId"))
    private Set<AttributeType> attributeTypes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collec_char_rela", joinColumns = @JoinColumn(name = "collectionId"), inverseJoinColumns = @JoinColumn(name = "charId"))
    private Set<DataSetCharacteristic> characteristics;

    @OneToMany(mappedBy = "dataSetCollection", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DataSetContainer> dataSets;

    public DataSetCollection()
    {

    }

    public void addDataSetContainer(DataSetContainer container)
    {
        dataSets.add(container);
        container.setDataSetCollection(this);
    }

    public void removeDataSetContainer(DataSetContainer container)
    {
        dataSets.remove(container);
        container.setDataSetCollection(null);
    }

}

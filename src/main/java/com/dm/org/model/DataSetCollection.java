package com.dm.org.model;


import com.dm.org.identifier.EntityIdentifier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;


/**
 * @author 刘祥德 qq313700046@icloud.com .
 * @date created in 11:26 2017/7/4.
 * @description
 * @modified by:
 */
@Entity
@Table(name = "data_set_collection", catalog = "")
public class DataSetCollection implements EntityIdentifier

{
    private String collectionId;

    private String collectionName;

    private Byte enableMissing;

    private String description;

    private Date dateDonated;

    private String relevantPapers;

    private String abstractInfo;

    private Set<MiningTaskType> associatedTasks;

    private String topics;

    private int hits;

    private AreaType area;

    private Set<AttributeType> attributeTypes;

    private Set<DataSetCharacteristic> characteristics;

    @JsonIgnore
    private Set<DataSetContainer> dataSets;

    public DataSetCollection()
    {

    }

    @Id
    @GenericGenerator(name = "uuidGenerator", strategy = "uuid")
    @GeneratedValue(generator = "uuidGenerator")
    @Column(name = "collectionId", nullable = false,length = 32)
    public String getCollectionId()
    {
        return collectionId;
    }

    public void setCollectionId(String collectionId)
    {
        this.collectionId = collectionId;
    }

    @Basic
    @Column(name = "enableMissing")
    public Byte getEnableMissing()
    {
        return enableMissing;
    }

    public void setEnableMissing(Byte enableMissing)
    {
        this.enableMissing = enableMissing;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Basic
    @Column(name = "dataDonated")
    public Date getDateDonated()
    {
        return dateDonated;
    }

    public void setDateDonated(Date dataDonated)
    {
        this.dateDonated = dataDonated;
    }

    @Lob
    @Column(name = "relevantPapers")
    public String getRelevantPapers() {
        return relevantPapers;
    }

    public void setRelevantPapers(String relevantPapers) {
        this.relevantPapers = relevantPapers;
    }

    @Basic
    @Column(name = "abstractInfo")
    public String getAbstractInfo()
    {
        return abstractInfo;
    }

    public void setAbstractInfo(String abstractInfo)
    {
        this.abstractInfo = abstractInfo;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "set_task_rel",
            joinColumns = @JoinColumn(name = "collectionId"),
            inverseJoinColumns = @JoinColumn(name = "typeId"))
    public Set<MiningTaskType> getAssociatedTasks()
    {
        return associatedTasks;
    }

    public void setAssociatedTasks(Set<MiningTaskType> associatedTasks)
    {
        this.associatedTasks = associatedTasks;
    }

    @Basic
    @Column(name = "topics")
    public String getTopics()
    {
        return topics;
    }

    public void setTopics(String topics)
    {
        this.topics = topics;
    }

    @ManyToOne
    @JoinColumn(name = "areaId",foreignKey = @ForeignKey(name="AREA_TYPE_FK"))
    public AreaType getArea() {
        return area;
    }

    public void setArea(AreaType area)
    {
        this.area = area;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dataSetCollection",
            orphanRemoval = true, fetch = FetchType.EAGER)
    public Set<DataSetContainer> getDataSets()
    {
        return dataSets;
    }

    public void setDataSets(Set<DataSetContainer> dataSets)
    {
        this.dataSets = dataSets;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collec_char_rela",
            joinColumns = @JoinColumn(name = "collectionId"),
            inverseJoinColumns = @JoinColumn(name = "charId"))
    public Set<DataSetCharacteristic>  getCharacteristics()
    {
        return characteristics;
    }

    public void setCharacteristics(Set<DataSetCharacteristic> characteristics) {
        this.characteristics = characteristics;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetCollection that = (DataSetCollection) o;

        return Objects.equal(this.collectionId, that.collectionId);
    }




    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this)
                .add("collectionId", collectionId)
                .add("characteristics", characteristics)
                .add("enableMissing", enableMissing)
                .add("description", description)
                .add("dataDonated", dateDonated)
                .add("relevantPapers", relevantPapers)
                .add("abstractInfo", abstractInfo)
                .add("associatedTasks", associatedTasks)
                .add("topics", topics)
                .add("area", area)
                .toString();
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(collectionId, collectionName, enableMissing, description, dateDonated, relevantPapers,
                abstractInfo,topics, hits, area);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attr_type_rel",
            joinColumns = @JoinColumn(name = "collectionId"),
            inverseJoinColumns = @JoinColumn(name = "typeId"))
    public Set<AttributeType> getAttributeTypes() {
        return attributeTypes;
    }

    public void setAttributeTypes(Set<AttributeType> attributeTypes) {
        this.attributeTypes = attributeTypes;
    }
}

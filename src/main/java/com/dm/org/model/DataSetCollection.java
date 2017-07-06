package com.dm.org.model;


import com.dm.org.identifier.EntityIdentifier;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;


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

    private Byte enableMissing;

    private String description;

    private Timestamp dateDonated;

    private String relevantPapers;

    private String abstractInfo;

    private String associatedTasks;

    private String topics;

    private String area;

    private Set<CollectionCharMap> characteristics;

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
    @Column(name = "enableMissing", nullable = true)
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
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Basic
    @Column(name = "dataDonated", nullable = true)
    public Timestamp getDateDonated()
    {
        return dateDonated;
    }

    public void setDateDonated(Timestamp dataDonated)
    {
        this.dateDonated = dataDonated;
    }

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "relevantPapers", nullable = true)
    public String getRelevantPapers() {
        return relevantPapers;
    }

    public void setRelevantPapers(String relevantPapers) {
        this.relevantPapers = relevantPapers;
    }

    @Basic
    @Column(name = "abstractInfo", nullable = false, length = 600)
    public String getAbstractInfo()
    {
        return abstractInfo;
    }

    public void setAbstractInfo(String abstractInfo)
    {
        this.abstractInfo = abstractInfo;
    }

    @Basic
    @Column(name = "associatedTasks", nullable = false, length = 50)
    public String getAssociatedTasks()
    {
        return associatedTasks;
    }

    public void setAssociatedTasks(String associatedTasks)
    {
        this.associatedTasks = associatedTasks;
    }

    @Basic
    @Column(name = "topics", nullable = false, length = 30)
    public String getTopics()
    {
        return topics;
    }

    public void setTopics(String topics)
    {
        this.topics = topics;
    }

    @Basic
    @Column(name = "area", nullable = true, length = 30)
    public String getArea()
    {
        return area;
    }

    public void setArea(String area)
    {
        this.area = area;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "dataSetCollection",
            orphanRemoval = true, fetch = FetchType.LAZY)
    public Set<DataSetContainer> getDataSets()
    {
        return dataSets;
    }

    public void setDataSets(Set<DataSetContainer> dataSets)
    {
        this.dataSets = dataSets;
    }


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "collec_char_rela",
            joinColumns = @JoinColumn(name = "collectionId"),
            inverseJoinColumns = @JoinColumn(name = "charId"))
    public Set<CollectionCharMap>  getCharacteristics()
    {
        return characteristics;
    }

    public void setCharacteristics(Set<CollectionCharMap> characteristics) {
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
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetCollection that = (DataSetCollection)o;

        return Objects.equal(this.collectionId, that.collectionId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(collectionId);
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
                .add("dataSets", dataSets)
                .toString();
    }
}

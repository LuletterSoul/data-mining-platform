package com.dm.org.dto;

import com.dm.org.model.AreaType;

import java.sql.Date;
import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public class CollectionDTO {

    private String collectionId;

    private String collectionName;

    private String enableMissing;

    private String description;

    private Date dateDonated;

    private String relevantPapers;

    private String abstractInfo;

    private List<Integer> associatedTaskIds;

    private List<Integer> attributeTypeIds;

    private List<Integer> characteristicIds;

    private String topics;

    private int hits;

    private Integer areaId;



    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getEnableMissing() {
        return enableMissing;
    }

    public void setEnableMissing(String enableMissing) {
        this.enableMissing = enableMissing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDonated() {
        return dateDonated;
    }

    public void setDateDonated(Date dateDonated) {
        this.dateDonated = dateDonated;
    }

    public String getRelevantPapers() {
        return relevantPapers;
    }

    public void setRelevantPapers(String relevantPapers) {
        this.relevantPapers = relevantPapers;
    }

    public String getAbstractInfo() {
        return abstractInfo;
    }

    public void setAbstractInfo(String abstractInfo) {
        this.abstractInfo = abstractInfo;
    }

    public List<Integer> getAssociatedTaskIds() {
        return associatedTaskIds;
    }

    public void setAssociatedTaskIds(List<Integer> associatedTaskIds) {
        this.associatedTaskIds = associatedTaskIds;
    }

    public List<Integer> getAttributeTypeIds() {
        return attributeTypeIds;
    }

    public void setAttributeTypeIds(List<Integer> attributeTypeIds) {
        this.attributeTypeIds = attributeTypeIds;
    }

    public List<Integer> getCharacteristicIds() {
        return characteristicIds;
    }

    public void setCharacteristicIds(List<Integer> characteristicIds) {
        this.characteristicIds = characteristicIds;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }
}

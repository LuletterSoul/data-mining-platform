package com.vero.dm.repository.dto;


import java.sql.Date;
import java.util.List;

import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in
 */

@Data
public class CollectionDTO
{

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

    private List<String> containerIds;

    private String topics;

    private int hits;

    private Integer areaId;

}

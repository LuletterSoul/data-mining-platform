package com.vero.dm.repository.dto;

import lombok.*;

import java.security.Timestamp;
import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Data
public class MiningTaskDTO {
    private String taskId;

    private String taskName;

    private String type;

    private String taskDescription;

    private int duration;

    private String startTime;

    private String finishTime;

    private List<String> groupIds;

    private List<String> collectionIds;

    private List<String> algorithmIds;

}

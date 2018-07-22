package com.vero.dm.model.enums;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:31 2018/1/24.
 * @since data-minning-platform
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MiningTaskStatus implements BaseEnum {

    toBeAssigned(1, "TO_BE_ASSIGNED", "待分配"),
    attentionRequired(2, "ATTENTION_REQUIRED", "任务需推进"),
    completed(3, "COMPLETED", "任务完成"),
    inProgress(4, "IN_PROGRESS", "任务进行中"),
    incomplete(5, "IN_COMPLETE", "任务未完成"),
    newTask(6, "NEW_TASK", "新任务"),
    newCommit(7,"NEW_COMMIT","新的提交");

    public static Map<Integer, MiningTaskStatus> map = new HashMap<>();
    static
    {
        MiningTaskStatus[] values = MiningTaskStatus.values();
        for (MiningTaskStatus value1 : values)
        {
            map.put(value1.getValue(), value1);
        }
    }

    private int value;

    private String status;

    private String description;

    MiningTaskStatus(Integer value, String status, String description)
    {
        this.value = value;
        this.status = status;
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public String getStatus()
    {
        return status;
    }

    @JsonCreator
    public static MiningTaskStatus getItem(int value){
        for(MiningTaskStatus item : values()){
            if(item.getValue() == value){
                return item;
            }
        }
        return null;
    }

    @JsonValue
    public int getValue()
    {
        return value;
    }

    public static void setMap(Map<Integer, MiningTaskStatus> map) {
        MiningTaskStatus.map = map;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<StatusObject> enum2Objects()
    {
        List<StatusObject> statusObjects = new ArrayList<>();
        for (int i = 0; i < values().length; i++ )
        {
            statusObjects.add(StatusObject.enum2Object(values()[i]));
        }
        return statusObjects;
    }
}

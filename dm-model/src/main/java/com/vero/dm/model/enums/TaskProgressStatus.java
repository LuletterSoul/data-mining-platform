package com.vero.dm.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  11:41 2018/3/8.
 * @since data-mining-platform
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TaskProgressStatus implements BaseEnum {
    toBeAssigned(1, "TO_BE_ASSIGNED", "无队伍执行"),
    opening(2, "OPENING", "任务开放,可提交任务发掘结果"),
    closed(3, "CLOSED", "任务关闭"),
    closing(4, "CLOSING", "任务即将关闭"),
    canceled(5,"CANCELED","任务被取消"),
    deleted(6,"DELETED","任务被强制删除"),
    newCreate(7, "NEW_CREATE", "新创建"),
    assigned(8,"ASSIGNED","任务已被分发"),;

    public static Map<Integer, TaskProgressStatus> map = new HashMap<>();

    static
    {
        TaskProgressStatus[] values = TaskProgressStatus.values();
        for (TaskProgressStatus value1 : values)
        {
            map.put(value1.getValue(), value1);
        }
    }

    private int value;

    private String status;

    private String description;

    TaskProgressStatus(Integer value, String status, String description)
    {
        this.value = value;
        this.status = status;
        this.description = description;
    }


    @JsonCreator
    public static TaskProgressStatus getItem(int value){
        for(TaskProgressStatus item : values()){
            if(item.getValue() == value){
                return item;
            }
        }
        return null;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public static Map<Integer, TaskProgressStatus> getMap() {
        return map;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}

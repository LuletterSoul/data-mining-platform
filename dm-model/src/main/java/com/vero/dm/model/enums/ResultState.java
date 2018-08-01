package com.vero.dm.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:21 2018/7/24.
 * @since data-mining-platform
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  ResultState implements BaseEnum
{
    noResult(1,"NO_RESULT","未提交"),
    submitted(2,"SUBMITTED","已提交"),
    downloaded(3, "DOWNLOADED", "已下载");

    private int value;

    private String status;

    private String description;


    ResultState(int value, String status, String description) {
        this.value = value;
        this.status = status;
        this.description = description;
    }

    @JsonCreator
    public static ResultState getItem(int value){
        for(ResultState item : values()){
            if(item.getValue() == value){
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public int getValue()
    {
        return value;
    }

    @Override
    public String getStatus()
    {
        return status;
    }

    @Override
    public String getDescription()
    {
        return description;
    }
}

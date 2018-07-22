package com.vero.dm.model.enums;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:55 2018/7/22.
 * @since data-mining-platform
 */

public class StatusObject implements BaseEnum
{

    private Integer value;

    private String status;

    private String description;

    public StatusObject(Integer value, String status, String description)
    {
        this.value = value;
        this.status = status;
        this.description = description;
    }

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

    public static StatusObject enum2Object(BaseEnum baseEnum)
    {
        return new StatusObject(baseEnum.getValue(), baseEnum.getStatus(),
            baseEnum.getDescription());
    }

}

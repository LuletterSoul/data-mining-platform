package com.vero.dm.model;


import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 14:53 2018/1/24.
 * @since data-mining-platform
 */

@Data
public class MiningTaskDuration
{
    private Double durationDay;

    private Double durationHour;

    private Double durationMinute;

    @Override
    public String toString()
    {
        return durationDay + "天" + durationHour + "小时" + durationMinute + "分钟";
    }
}

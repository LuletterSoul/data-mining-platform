package com.vero.dm.repository.dto;


import lombok.Data;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:11 2017/10/13.
 * @since data-minning-platform
 */

@Data
public class TaskConfigParams
{
    private String taskId;

    private String beginDate;

    private String endDate;

    private Integer gradient = 0;

}

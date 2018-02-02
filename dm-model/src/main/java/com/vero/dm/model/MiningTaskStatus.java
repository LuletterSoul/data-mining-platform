package com.vero.dm.model;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:31 2018/1/24.
 * @since data-minning-platform
 */

public enum MiningTaskStatus {
    attentionRequried("任务需推进"),
    completed("任务完成"),
    inProgress("任务进行中"),
    incomplete("任务未完成"),
    newTask("新任务");


    private String description;

    MiningTaskStatus(String description)
    {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

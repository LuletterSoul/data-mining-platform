package com.dm.org.dto;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in  11:11 2017/10/13.
 * @since data-minning-platform
 */

public class TaskConfigParam {
    private String taskId;
    private String beginDate;
    private String endDate;
    private Integer gradient = 0;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getGradient() {
        return gradient;
    }

    public void setGradient(Integer gradient) {
        this.gradient = gradient;
    }
}

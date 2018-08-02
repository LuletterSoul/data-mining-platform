package com.vero.dm.repository.dto;


import com.vero.dm.model.enums.ResultState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 23:45 2018/8/1.
 * @since data-mining-platform
 */

@AllArgsConstructor
@Data
public class TaskStatistics {
    /**
     * 每个阶段提交的人数
     */
    private Map<Integer, Integer> submittedStatistics = new LinkedHashMap<>();

    /**
     * 每个阶段未提交的人数
     */
    private Map<Integer, Integer> noSubmittedStatistics = new LinkedHashMap<>();

    /**
     * 每个阶段管理员已下载其结果的人数
     */
    private Map<Integer, Integer> downloadedStatistics = new LinkedHashMap<>();


    /**
     * 欠交同学
     */
    private List<StudentDto> absentStudents = new ArrayList<>();

    /**
     * 按阶段分类
     */
    private Map<Integer, Integer[]> stageToGroupSubmitted = new LinkedHashMap<>();

    /**
     * 按状态分类
     */
    private Map<ResultState, List<Integer>> stateToGroup = new LinkedHashMap<>();

    public TaskStatistics() {
    }
}

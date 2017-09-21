package com.dm.org.service;

import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

public interface MiningTaskService extends BaseService<DataMiningTask,String>{

    DataMiningTask deleteByTaskId(String taskId);


    List<DataMiningGroup> fetchInvolvedGroups(String taskId);

}

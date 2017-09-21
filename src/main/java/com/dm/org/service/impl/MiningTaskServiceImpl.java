package com.dm.org.service.impl;

import com.dm.org.model.DataMiningGroup;
import com.dm.org.model.DataMiningTask;
import com.dm.org.service.MiningTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 *          created in
 */

@Service
public class MiningTaskServiceImpl extends AbstractBaseServiceImpl<DataMiningTask, String> implements MiningTaskService {

    @Override
    public DataMiningTask deleteByTaskId(String taskId) {
        DataMiningTask task = this.findById(taskId);
        this.deleteById(taskId);
        return task;
    }


    @Override
    public List<DataMiningGroup> fetchInvolvedGroups(String taskId) {
        return miningTaskDao.fetchInvolvedGroups(taskId);
    }
}

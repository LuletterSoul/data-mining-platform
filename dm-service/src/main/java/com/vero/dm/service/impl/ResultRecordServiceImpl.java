package com.vero.dm.service.impl;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.enums.ResultState;
import com.vero.dm.repository.specifications.TaskSpecifications;
import com.vero.dm.service.ResultRecordService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 9:03 2018/7/27.
 * @since data-mining-platform
 */

@Service
@Slf4j
public class ResultRecordServiceImpl extends AbstractBaseServiceImpl<ResultRecord, Integer> implements ResultRecordService
{
    @Override
    public Page<ResultRecord> findResultRecords(Pageable pageable, String taskId,
                                                List<Integer> submitterIds, ResultState state,
                                                boolean all, boolean newest, Integer stageId)
    {
        return resultRecordRepository.findAll(
            TaskSpecifications.recordsSpec(taskId, submitterIds, state, all, stageId,newest), pageable);
    }
}

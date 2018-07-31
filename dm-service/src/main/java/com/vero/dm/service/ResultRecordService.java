package com.vero.dm.service;


import java.util.List;

import com.vero.dm.model.MiningResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.enums.ResultState;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 9:00 2018/7/27.
 * @since data-mining-platform
 */

public interface ResultRecordService extends BaseService<ResultRecord, Integer>
{
    Page<ResultRecord> findResultRecords(Pageable pageable, String taskId,
                                         List<String> submitterIds, ResultState state,
                                         boolean all, boolean newest, Integer stageId);
}

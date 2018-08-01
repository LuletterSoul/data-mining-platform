package com.vero.dm.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vero.dm.model.MiningResult;
import com.vero.dm.model.ResultRecord;
import com.vero.dm.model.enums.ResultState;
import com.vero.dm.repository.specifications.ResultSpecifications;
import com.vero.dm.repository.specifications.TaskSpecifications;
import com.vero.dm.service.ResultRecordService;

import lombok.extern.slf4j.Slf4j;

import static com.vero.dm.repository.specifications.ResultSpecifications.*;


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
                                                List<String> submitterIds, ResultState state,
                                                boolean all, boolean newest, Integer stageId)
    {

        List<MiningResult> results;
        if (all)
        {
            results = miningResultRepository.findAll(
                resultsSpec(taskId, stageId, submitterIds, state));

        }
        else
        {
            Page<MiningResult> page = miningResultRepository.findAll(resultsSpec(taskId, stageId, submitterIds, state),pageable);
            results = page.getContent();
        }
        List<Set<ResultRecord>> recordSets = results.stream().map(
            MiningResult::getRecords).collect(Collectors.toList());
        List<ResultRecord> records = new ArrayList<>();
        recordSets.forEach(records::addAll);
        return new PageImpl<>(records);
    }
}

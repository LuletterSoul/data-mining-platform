package com.vero.dm.service.impl;


import com.vero.dm.model.DataMiningTask;
import org.springframework.stereotype.Service;

import com.vero.dm.model.MiningTaskStage;
import com.vero.dm.service.MiningTaskStageService;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 22:59 2018/7/25.
 * @since data-mining-platform
 */

@Service
@Slf4j
public class MiningTaskStageServiceImpl extends AbstractBaseServiceImpl<MiningTaskStage, Integer> implements MiningTaskStageService
{
    @Override
    public MiningTaskStage saveStage(MiningTaskStage stage)
    {
        return stageRepository.saveAndFlush(stage);
    }
}

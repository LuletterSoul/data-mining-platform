package com.vero.dm.service;

import com.vero.dm.model.MiningTaskStage;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  22:57 2018/7/25.
 * @since data-mining-platform
 */

public interface MiningTaskStageService extends BaseService<MiningTaskStage,Integer>  {

    public MiningTaskStage saveStage(MiningTaskStage stage);
}

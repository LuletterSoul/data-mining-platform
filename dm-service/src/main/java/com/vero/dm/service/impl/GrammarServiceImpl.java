package com.vero.dm.service.impl;


import java.util.List;

import com.vero.dm.model.MiningGrammar;
import com.vero.dm.service.GrammarService;
import org.springframework.stereotype.Service;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:33 2018/7/24.
 * @since data-mining-platform
 */

@Service
public class GrammarServiceImpl extends AbstractBaseServiceImpl<MiningGrammar, Integer> implements GrammarService
{

    @Override
    public List<MiningGrammar> findAll()
    {
        return miningGrammarRepository.findAll();
    }
}

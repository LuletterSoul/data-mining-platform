package com.vero.dm.security.strategy;


import java.util.LinkedList;

import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:11 2018/2/13.
 * @since data-mining-platform
 */

public interface StrategyMatchChain
{

    boolean doMatch(LinkedList<String> candidates, StatelessInfo info, StatelessToken token);

    /**
     * 重置匹配链的状态
     */
    void reuse();
}

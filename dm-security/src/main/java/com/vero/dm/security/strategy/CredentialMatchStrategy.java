package com.vero.dm.security.strategy;


import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;

import java.util.LinkedList;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:15 2018/2/13.
 * @since data-mining-platform
 */

public interface CredentialMatchStrategy
{

     void init(MatchStrategyConfig strategyConfig);

    boolean doMatch(LinkedList<String> candidates, StatelessInfo info, StatelessToken token, StrategyMatchChain matchChain);
}

package com.vero.dm.security.strategy;


import java.util.LinkedList;
import java.util.List;

import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:51 2018/2/13.
 * @since data-mining-platform
 */

@Slf4j
public class ApplicationStrategyMatchChain implements StrategyMatchChain
{

    public static final int INCREMENT = 10;

    /**
     * Strategies
     */
    private DefaultMatchStrategyConfig[] strategies = new DefaultMatchStrategyConfig[0];

    /**
     * The int which is used to maintain the current position in the strategies chain.
     */
    private int pos = 0;

    /**
     * The int which gives the current number of strategies in the chain.
     */
    private int n = 0;

    private boolean matchResult = false;

    public void addStrategy(DefaultMatchStrategyConfig strategyConfig)
    {
        for (MatchStrategyConfig config : strategies)
        {
            if (strategyConfig == config)
            {
                return;
            }
        }
        if (n == strategies.length)
        {
            DefaultMatchStrategyConfig[] newStrategies = new DefaultMatchStrategyConfig[n
                                                                                        + INCREMENT];
            System.arraycopy(strategies, 0, newStrategies, 0, n);
            strategies = newStrategies;
        }
        strategies[n++ ] = strategyConfig;
    }

    /**
     * Prepare for reuse of the filters and wrapper executed by this chain.
     */
    @Override
    public synchronized void reuse()
    {
        matchResult = false;
        pos = 0;
    }

    @Override
    public synchronized boolean doMatch(LinkedList<String> candidates, StatelessInfo info, StatelessToken token)
    {
        return internalDoMatch(candidates, info, token);
    }

    public boolean internalDoMatch(LinkedList<String> candidates, StatelessInfo info,
                                   StatelessToken token)
    {
        // Call the next filter if there is one
        if (pos < n)
        {
            DefaultMatchStrategyConfig filterConfig = strategies[pos++ ];
            CredentialMatchStrategy strategy = filterConfig.getStrategy();
            matchResult = strategy.doMatch(candidates, info, token, this);
            if (breakChainCondition()) return true;
        }
        else
        {
            if (!matchResult)
            {
                log.info("Token info {} committed by client gained an incorrect match result.",
                    token);
            }
        }
        return matchResult;
    }

    //提前终止匹配链:当前仅当匹配链中的匹配规则返回
    private boolean breakChainCondition()
    {
        if (matchResult)
        {
            reuse();
            return true;
        }
        return false;
    }

}

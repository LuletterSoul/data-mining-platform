package com.vero.dm.security.strategy;


import java.util.LinkedList;

import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:22 2018/2/13.
 * @since data-mining-platform
 */

public class AbstractStrategy implements CredentialMatchStrategy
{
    private MatchStrategyConfig strategyConfig;

    private StatelessCredentialsServer credentialsComputer;

    public AbstractStrategy(StatelessCredentialsServer credentialsComputer)
    {
        this.credentialsComputer = credentialsComputer;
    }

    public AbstractStrategy()
    {}

    public AbstractStrategy(MatchStrategyConfig strategyConfig)
    {
        this.strategyConfig = strategyConfig;
    }

    @Override
    public void init(MatchStrategyConfig strategyConfig)
    {
        this.strategyConfig = strategyConfig;
    }

    @Override
    public boolean doMatch(LinkedList<String> candidates, StatelessInfo info, StatelessToken token,
                           StrategyMatchChain matchChain)
    {
        return matchChain.doMatch(candidates, info, token);
    }

    public MatchStrategyConfig getStrategyConfig()
    {
        return strategyConfig;
    }

    public StatelessCredentialsServer getCredentialsComputer() {
        return credentialsComputer;
    }
}

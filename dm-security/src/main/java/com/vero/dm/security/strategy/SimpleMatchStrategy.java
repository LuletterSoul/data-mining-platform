package com.vero.dm.security.strategy;


import java.util.LinkedList;

import org.apache.shiro.crypto.hash.Hash;

import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:22 2018/2/13.
 * @since data-mining-platform
 */

@Slf4j
@Getter
@Setter
public class SimpleMatchStrategy extends AbstractStrategy
{

    private int hashIterations = 1000;

    public SimpleMatchStrategy()
    {}

    public SimpleMatchStrategy(StatelessCredentialsServer credentialsComputer)
    {
        super(credentialsComputer);
    }

    public SimpleMatchStrategy(MatchStrategyConfig strategyConfig)
    {
        super(strategyConfig);
    }

    // 只执行一次令牌匹配
    @Override
    public boolean doMatch(LinkedList<String> candidates, StatelessInfo info, StatelessToken token,
                           StrategyMatchChain matchChain)
    {
        log.debug("Strategy: [{}] do match.", getStrategyConfig().getStrategyName());
        return equals(token.getClientDigest(), buildServerDigest(info))
               || handleCandidates(candidates, token, info)
               || executeChain(candidates, info, token, matchChain);
    }

    protected Hash buildServerDigest(StatelessInfo info)
    {
        return computeServerDigest(info);
    }

    protected boolean executeChain(LinkedList<String> candidates, StatelessInfo info,
                                   StatelessToken token, StrategyMatchChain matchChain)
    {
        return matchChain.doMatch(candidates, info, token);
    }

    protected Hash computeServerDigest(StatelessInfo statelessInfo)
    {
        return getCredentialsComputer().computeHashWithParams(statelessInfo, getHashIterations());
    }

    protected boolean equals(Object tokenCredentials, Object accountCredentials)
    {
        String accountBase64 = ((Hash)accountCredentials).toBase64();
        return tokenCredentials.equals(accountBase64);
    }

    /**
     * 由子类策略定义候选一次性token的验证行为
     * 
     * @param candidates
     * @return
     */
    protected boolean handleCandidates(LinkedList<String> candidates, StatelessToken token,
                                       StatelessInfo info)
    {
        return false;
    };
}

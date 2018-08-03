package com.vero.dm.security.strategy;


import java.util.LinkedList;
import java.util.ListIterator;

import com.vero.dm.exception.auth.HighFrequencyAccessException;
import com.vero.dm.exception.error.ExceptionCode;
import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;
import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 11:17 2018/2/13.
 * @since data-mining-platform
 */
@Slf4j
public class MatchAllCandidateStrategy extends SimpleMatchStrategy
{

    public MatchAllCandidateStrategy()
    {}

    public MatchAllCandidateStrategy(MatchStrategyConfig strategyConfig)
    {
        super(strategyConfig);
    }

    public MatchAllCandidateStrategy(StatelessCredentialsServer credentialsComputer)
    {
        super(credentialsComputer);
    }

    // 匹配所有候选令牌
    @Override
    protected boolean handleCandidates(LinkedList<String> candidates, StatelessToken token, StatelessInfo info) {
        boolean isMatched = false;
        ListIterator<String> listIterator = candidates.listIterator(candidates.size());
        log.debug("Candidates:[{}]", candidates);
        while (listIterator.hasPrevious())
        {
            info.setCredentials(listIterator.previous());
            log.debug("Client digest: [{}].Server digest:[{}]", token.getClientDigest(), buildServerDigest(info));
            if (equals(token.getClientDigest(), buildServerDigest(info)))
            {
                isMatched = true;
                break;
            }
        }
        if (!isMatched)
        {
            String message = "Server has matched all cached toke list,but could not pass the posted client digest. "
                    + "Maybe the client exists concurrency client digest computing error."
                    + "Please link client maintainer,and fix the problem.";
            log.error(message);
            throw new HighFrequencyAccessException("Overflow access frequencies.",
                    ExceptionCode.HighFrequencyAccessError);
        }
        return true;
    }
}

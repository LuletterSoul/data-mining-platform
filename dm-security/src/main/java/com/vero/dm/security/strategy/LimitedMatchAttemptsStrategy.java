package com.vero.dm.security.strategy;


import java.util.LinkedList;
import java.util.ListIterator;

import com.vero.dm.security.credentials.StatelessCredentialsServer;
import com.vero.dm.security.realm.StatelessInfo;
import com.vero.dm.security.realm.StatelessToken;

import lombok.extern.slf4j.Slf4j;


/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 12:00 2018/2/13.
 * @since data-mining-platform
 */

@Slf4j
public class LimitedMatchAttemptsStrategy extends SimpleMatchStrategy
{
    private int attemptTimes = 5;

    public LimitedMatchAttemptsStrategy()
    {}

    public LimitedMatchAttemptsStrategy(StatelessCredentialsServer credentialsComputer)
    {
        super(credentialsComputer);
    }

    public LimitedMatchAttemptsStrategy(MatchStrategyConfig strategyConfig)
    {
        super(strategyConfig);
    }

    public void setAttemptTimes(int attemptTimes)
    {
        this.attemptTimes = attemptTimes;
    }

    // 匹配部分候选令牌
    @Override
    protected boolean handleCandidates(LinkedList<String> candidates, StatelessToken token,
                                       StatelessInfo info)
    {
        if (candidates.size() < attemptTimes)
        {
            String message = "Candidates size is not capable to be executed matching logic.";
            // throw new CandidateTokenSizeException(message,
            // ExceptionCode.CandidateTokenSizeError);
            log.warn(message);
        }
        ListIterator<String> listIterator = candidates.listIterator(candidates.size() - 1);
        boolean isMatched = false;
        int index = 0;
        while (listIterator.hasPrevious() && index < attemptTimes)
        {
            ++index;
            String candidate = listIterator.previous();
            info.setCredentials(candidate);
            if (equals(token.getClientDigest(), buildServerDigest(info)))
            {
                isMatched = true;
                break;
            }
        }
        if (!isMatched)
        {
            log.debug(
                "After [{}] times token match process.Current token {} don't match server info.",
                attemptTimes, token);
        }
        return isMatched;
    }
}

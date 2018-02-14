package com.vero.dm.security.strategy;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 17:28 2018/2/13.
 * @since data-mining-platform
 */

public class DefaultMatchStrategyConfig implements MatchStrategyConfig
{

    private CredentialMatchStrategy strategy;

    // 匹配策略名
    private String strategyName = null;

    public DefaultMatchStrategyConfig(CredentialMatchStrategy strategy, String strategyName)
    {
        this.strategy = strategy;
        strategy.init(this);
        this.strategyName = strategyName;
    }

    public DefaultMatchStrategyConfig()
    {
        initStrategy();
    }

    @Override
    public String getStrategyName()
    {
        return this.strategyName;
    }

    @Override
    public CredentialMatchStrategy getStrategy()
    {
        if (this.strategy != null)
        {
            return (this.strategy);
        }
        initStrategy();
        return this.strategy;
    }

    protected void initStrategy()
    {
        // 如果
        strategy = new AbstractStrategy(this);
    }
}

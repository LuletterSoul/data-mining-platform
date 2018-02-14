package com.vero.dm.security.strategy;

/**
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5
 * created in  17:24 2018/2/13.
 * @since data-mining-platform
 */

public interface MatchStrategyConfig {

    /**
     * Get the name of the strategy.
     *
     * @return The filter-name of this strategy as defined in the deployment
     *         descriptor.
     */
    String getStrategyName();


    CredentialMatchStrategy getStrategy();
}

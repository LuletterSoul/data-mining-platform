package com.vero.dm.security.credentials;

import org.apache.http.auth.InvalidCredentialsException;

/**
 * 判断证书是否过期
 * 
 * @author XiangDe Liu qq313700046@icloud.com .
 * @version 1.5 created in 15:45 2018/2/10.
 * @since data-mining-platform
 */

public interface TokenExpiredChecker
{
    /**
     * 判断证书是否过期
     *
     * @param accessToken
     *            证书
     * @return 是否过期
     */
    boolean isTokenExpired(String accessToken);
}
